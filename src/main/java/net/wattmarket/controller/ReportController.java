package net.wattmarket.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import net.wattmarket.service.ReportService;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    
    private Map<String,List<?>> prodActual=null;
    private Map<String,List<?>> prediction=null;

    @GetMapping("/report")
    public String report(){
        return "report";
    }

    @GetMapping("/report/prepare")
    @ResponseBody
    public Map<String,Boolean> prepare(
        @RequestParam(name="memberId") String memberId
    ){
        prodActual= reportService.readActualProd(memberId);
        prediction= reportService.readPrediction(memberId, 0);
        Map<String,Boolean> resp = new HashMap<>();
        resp.put("result",true);
        return resp;
    }

    /**
     * 실제 소비량 가져오는 함수
     * @param memberId
     * @return "ActualConsumptionMonths", "ActualConsumption"
     */
    @GetMapping("/report/getActualConsumptionData")
    @ResponseBody
    public Map<String, List<?>> getActualConsumptionData(
        @RequestParam(name="memberId") String memberId
    ){
        Map<String,List<?>> respData = new HashMap<>();
        Map<String, List<?>> consActual = reportService.readActualCons(memberId);
        respData.put("ActualConsumptionMonths", consActual.get("ActualConsumptionMonths"));
        respData.put("ActualConsumption", consActual.get("ActualConsumption"));

        return respData;
    }
    
    @GetMapping("/report/getPredictProductionData")
    @ResponseBody
    public Map<String,List<?>> getPredictProductionData(
        @RequestParam(name="memberId") String memberId
    ){
        Map<String,List<?>> respData = new HashMap<>();

        respData.put("ActualProduction", prodActual.get("ActualProduction"));
        respData.put("PredictProduction",prediction.get("PredictProduction"));
        respData.put("PredictProductionMonths",prediction.get("PredictProductionMonths"));

        return respData;
    }
    
    @GetMapping("/report/getFutureData")
    @ResponseBody
    public Map<String,List<?>> getPredictionData(
        @RequestParam(name="memberId") String memberId
    ){
        Map<String,List<?>> respData = new HashMap<>();
        Map<String,List<?>> pricePrediction=reportService.getPricePrediction(memberId);
        respData.put("PredictConsumptionMonths", prediction.get("PredictConsumptionMonths"));
        respData.put("PredictConsumption", prediction.get("PredictConsumption"));
        respData.put("PredictPriceMonths", pricePrediction.get("PredictPriceMonths"));
        respData.put("PredictPriceProg1", pricePrediction.get("PredictPriceProg1"));
        respData.put("PredictPriceProg2", pricePrediction.get("PredictPriceProg2"));
        respData.put("PredictPriceProg3", pricePrediction.get("PredictPriceProg3"));
        return respData;
    }

    @GetMapping("/report/getBreakevenData")
    @ResponseBody
    public Map<String,List> getBreakevenData(
        @RequestParam(name="cost") String costStr,
        @RequestParam(name="capacity") String capacityStr,
        @RequestParam(name="memberId") String memberId
    ){
        Map<String,List> respData = new HashMap<>();
        int capacity; capacity = Integer.parseInt(capacityStr);
        int cost; cost = Integer.parseInt(costStr);

        Map<String,List<?>> consumerPrediction= reportService.breakevenAnalysis(memberId, capacity, cost);
        respData.put("RequiredMonths", consumerPrediction.get("RequiredMonths"));
        respData.put("NetRevenues", consumerPrediction.get("NetRevenues"));

        return respData;
    }

    //consulting에서도 써야하므로 새롭게 메소드 만듬
    @GetMapping("/report/getTradePQ")
    @ResponseBody
    public Map<String,List<?>> getTradePQ(
        @RequestParam(name="memberId") String memberId
    ){
        Map<String,List<?>> respData = new HashMap<>();

        Map<String,List<?>> prices= reportService.getPricePrediction(memberId);
        

        respData.put("PredictPriceMonths",prices.get("PredictPriceMonths"));
        respData.put("PredictPriceProg1", prices.get("PredictPriceProg1"));
        respData.put("PredictPriceProg2", prices.get("PredictPriceProg2"));
        respData.put("PredictPriceProg3", prices.get("PredictPriceProg3"));
        respData.put("ActualProductionMonths",prodActual.get("ActualProductionMonths"));
        respData.put("ActualProduction",prodActual.get("ActualProduction"));
        respData.put("PredictConsumptionMonths",prediction.get("PredictConsumptionMonths"));
        respData.put("PredictConsumption",prediction.get("PredictConsumption"));        

        return respData;
    }
}
