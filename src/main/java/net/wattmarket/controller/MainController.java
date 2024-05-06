package net.wattmarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {

    @GetMapping({"","/"})
    public String home(){
        return "home";
    }
    /**
     * 참고용 html 확인하는 함수(개발완료시 지울것)
     */
    @GetMapping("/reference")
    public String reference(){
        return "reference";
    }

    // 시뮬레이션 
    @GetMapping("/simulation")
    @ResponseBody
    public String simulation(
        @RequestParam(name="simulName") String simulName,
        @RequestParam(name="simulNationalIdFirst") String simulNationalIdFirst,
        @RequestParam(name="simulNationalIdSecond") String simulNationalIdSecond
    ){
        // 시뮬레이션 돌리려면 한전 API를 이름과 주민번호로 조회할 수 있어야함. 임시로 그냥 숫자 반환하게 해둠
        return "224,500";
    }

    //서비스소개
    @GetMapping("/serviceIntro")
    public String serviceIntro(){
        return "service";
    }
}
