package net.wattmarket.service;

import java.util.Collections;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import net.wattmarket.entity.PricePredictionEntity;
import net.wattmarket.entity.PricesEntity;
import net.wattmarket.repository.ConsumptionsRepository;
import net.wattmarket.repository.MembersRepository;
import net.wattmarket.repository.PricePredictionRepository;
import net.wattmarket.repository.PricesRepository;
import net.wattmarket.repository.ProductionsRepository;


@Service
@RequiredArgsConstructor
public class ReportService {

    private final MembersRepository membersRepository;
    private final ConsumptionsRepository consumptionsRepository;
    private final ProductionsRepository productionsRepository;
    private final RestTemplate restTemplate;
    private final PricesRepository pricesRepository;
    private final PricePredictionRepository pricePredictionRepository;

    @Value("${watty.predict.server}")
    String url;

    // 과거 전체전력량을 조회하는 메소드
    public Map<String, List<?>> readActualCons(String memberId){
        //전력소비량 조회
        List<Object[]> consumptions = consumptionsRepository.findAllConsumptionsByMemberId(memberId);
    
        List<Integer> consDateList = new ArrayList<>();
        List<Double> consElectricityList = new ArrayList<>();
    
        for (Object[] c: consumptions){
            consDateList.add((Integer) c[0]);
            consElectricityList.add((Double) c[1]);
        }
    
        //결과 담을 변수
        Map<String, List<?>> consResult = new HashMap<>();
        consResult.put("ActualConsumption", consElectricityList);
        consResult.put("ActualConsumptionMonths", consDateList);
        return consResult;
    }

    // 실제 태양광 발전량 조회
    public Map<String,List<?>> readActualProd(String memberId){
        List<Object[]> prodList = productionsRepository.findProdElectricityAndMonthsByMemberId(memberId);
        Map<String,List<?>> prodResult = new HashMap<>();
    
        List<Object> actualProduction = new ArrayList<>();
        List<Object> actualProductionMonths = new ArrayList<>();
    
        for (Object[] prod : prodList) {
            actualProduction.add(prod[0]);
            actualProductionMonths.add(prod[1]);
        }
    
        prodResult.put("ActualProduction", actualProduction);
        prodResult.put("ActualProductionMonths", actualProductionMonths);
    
        return prodResult;
    }

    // 예측 태양광 발전량 조회
        public Map<String, List<?>> readPrediction(String memberId, int capacity){
        // 경도,위도,설비용량 조회
        List<Object[]> predictInfoList = membersRepository.findPredictInfoByMemberId(memberId);
        Object[] predictInfo = predictInfoList.get(0);

        Double LocationX = (Double) predictInfo[0];
        Double LocationY = (Double) predictInfo[1];
        int installedCapacity = (int) predictInfo[2];
        
        Map<String, Object> producerInfo = new HashMap<>();
        producerInfo.put("locationX", LocationX);
        producerInfo.put("locationY", LocationY);
        producerInfo.put("installedCapacity", installedCapacity);
        producerInfo.put("memberId",memberId);

        Map<String,List<?>> error = new HashMap<>(); //에러용
        Map<String,List<?>> respData = null;//정상용
        
        try{
            // 헤더설정 //임포트는 스프링걸로
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            ResponseEntity<Map> response = restTemplate.postForEntity(url,producerInfo, Map.class);
            respData = response.getBody();
        } catch(Exception e){
            List<String> errorCodeList =new ArrayList<>();
            errorCodeList.add("오류번호:444"); errorCodeList.add("오류내용: 파이썬 작동안해서..");
            error.put("errorStatus", errorCodeList);
            return error;
        }

        return respData;
    }

    // 소비량 시계열 예측 수행
    public Map<String, List<?>> breakevenAnalysis(String memberId, int capacity, int cost){
        // 결과 담을 객체
        Map<String,List<?>> respData = new HashMap<>();

        // 생산량 및 소비량 예측 수행
        Map<String,List<?>> prediction =readPrediction(memberId, capacity);
        // 소비량은 바로 담아주기
        respData.put("PredictConsumption",prediction.get("PredictConsumption"));
        respData.put("PredictConsumptionMonths", prediction.get("PredictConsumptionMonths"));

        // 과거 12개월치의 추정생산량 기반 월평균 생산량 도출
        List<?> predictProduction = prediction.get("PredictProduction");
        Map<String,List<?>> calc = calcBreakeven(memberId, capacity, cost, predictProduction);
        respData.put("RequiredMonths",calc.get("RequiredMonths"));
        respData.put("NetRevenues",calc.get("NetRevenues"));

        return respData;
    }
    public Map<String,List<?>> calcBreakeven(String memberId, int capacity, int cost, List<?> prodPrediction ){
        double sum = 0.0;
        for (Object value : prodPrediction) {
            sum += (Double) value;
        }
        double avgProd = sum / prodPrediction.size();

        // 현재 전기가격 리스트 가져오기 : 원래 코드 but 월별로 갱신이 안되서 그냥 4월로 고정
        /*
        LocalDate date = LocalDate.now(); // 현재날짜를 Long 형태의 202404 처럼 바꿔주기
        if (date.getDayOfMonth() < 5) {
            date = date.minusMonths(1); // 이전 달로 설정
        }
         */
        LocalDate date = LocalDate.of(2024, 4, 30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        String formattedDate = date.format(formatter);
        Long yearMonth = Long.parseLong(formattedDate);
        PricesEntity pricesEntity= pricesRepository.findHousePricesByType(yearMonth); // 현재의 가격정보 가져오기
        String contractType = membersRepository.findContractTypeByMemberId(memberId); // 계약정보 가져오기
        // 가중평균을 통한 월평균 상계전기가격 계산
        double price=0.0;
        if (contractType.equals("저압")){
            price=(pricesEntity.getHleBetween() * 5 + pricesEntity.getHlsBetween())/6; // 12개월중 10개월은 기타 요금이, 2개월은 하계 요금이 적용되므로
        }
        else if (contractType.equals("고압")){
            price=(pricesEntity.getHhsBetween()*5 + pricesEntity.getHhsBetween())/6; // 누진 2구간을 기준으로 잡음
        }

        // 총비용과 총수입 비교하여 총수입이 더 커지는 기간 계산(월 단위)
        // 걸리는 개월수 [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14...]
        // 순이익 [-100000000,-9999999999,-999999998 ....]
        int month = 0;
        double monthlyRevenue= price * avgProd;
        double netRevenue=0.0 - cost;
        ArrayList<Integer> monthList = new ArrayList<>();
        ArrayList<Double> netRevenueList = new ArrayList<>();
        while (true){
            monthList.add(month); month+=1;
            netRevenueList.add(netRevenue); netRevenue+=monthlyRevenue;
            if(netRevenue>=0) break;
        }
        for (int i = 0; i<12; ++i){
            monthList.add(month); month+=1;
            netRevenueList.add(netRevenue); netRevenue+=monthlyRevenue;
        }

        // Map 객체에 담아서 반환
        Map<String,List<?>> respData= new HashMap<>();
        respData.put("RequiredMonths", monthList);
        respData.put("NetRevenues", netRevenueList);

        return respData;
    }

    // 예측한 요금 가져오기
    public Map<String,List<?>> getPricePrediction(String memberId){
        String contractType = membersRepository.findContractTypeByMemberId(memberId); //"고압" or "저압"
        
        List<PricePredictionEntity> ppEntityList= pricePredictionRepository.findAll();
        List<String> predictMonthList = new ArrayList<>(); // 예측시점 문자열 담을 리스트 ("4월_예측","5월_예측")
        List<Double> prog1PriceList = new ArrayList<>(); // 누진 1구간 요금 담을 리스트
        List<Double> prog2PriceList = new ArrayList<>(); // 누진 2구간 요금 담을 리스트
        List<Double> prog3PriceList = new ArrayList<>(); // 누진 3구간 요금 담을 리스트

        
        for (PricePredictionEntity ppe: ppEntityList){
            predictMonthList.add(ppe.getPredictMonth());
            if(contractType.equals("저압")){
                if (isSummer(ppe.getPredictMonth())){ // 저압 그리고 7월이나 8월인 경우
                    prog1PriceList.add(ppe.getHlsUnder300());
                    prog2PriceList.add(ppe.getHlsBetween());
                    prog3PriceList.add(ppe.getHlsOver450());
                } else{
                    prog1PriceList.add(ppe.getHleUnder200());
                    prog2PriceList.add(ppe.getHleBetween());
                    prog3PriceList.add(ppe.getHleOver400());
                }
            } else if(contractType.equals("고압")){
                if(isSummer(ppe.getPredictMonth())){
                    prog1PriceList.add(ppe.getHhsUnder300());
                    prog2PriceList.add(ppe.getHhsBetween());
                    prog3PriceList.add(ppe.getHhsOver450());
                } else{
                    prog1PriceList.add(ppe.getHheUnder200());
                    prog2PriceList.add(ppe.getHheBetween());
                    prog3PriceList.add(ppe.getHheOver400());
                }
            }
        }//for문
        Map<String,List<?>> respData = new HashMap<>();

        respData.put("PredictPriceMonths",predictMonthList);
        respData.put("PredictPriceProg1", prog1PriceList);
        respData.put("PredictPriceProg2", prog2PriceList);
        respData.put("PredictPriceProg3", prog3PriceList);
        
        return respData;
    }
    public Boolean isSummer(String predictMonth){
        if ((predictMonth.endsWith("7")||(predictMonth.endsWith("8")))){
            return true;
        }
        return false;
    }
}
