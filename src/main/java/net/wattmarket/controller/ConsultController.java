package net.wattmarket.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.wattmarket.service.ReportService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ConsultController {

    private final ReportService reportService;

    
    private Map<String,List<?>> prodActual=null;
    private Map<String,List<?>> prediction=null;


    @GetMapping("/consult")
    public String consult(){
        return "consult";
    }

    @GetMapping("/consult/prepare")
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

    @GetMapping("/consult/getAnalysisPQ")
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
    @GetMapping("/consult/register")
    @ResponseBody
    public Boolean register(
        @RequestParam(name="tradeType") String tradeType,
        @RequestParam(name="memberId") String memberId,
        @RequestParam(name="startDate") LocalDate startDate,
        @RequestParam(name="endDate") LocalDate endDate,
        @RequestParam(name="tradeAmount") int tradeAmount,
        @RequestParam(name="minPrice") int minPrice
    ){
        if(tradeType.equals("purchase")){

        }

        return true;
    }
}
