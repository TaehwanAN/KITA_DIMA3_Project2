package net.wattmarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import net.wattmarket.dto.MembersDTO;
import net.wattmarket.service.MembersService;

@Controller
@RequiredArgsConstructor
public class MembersController {

    private final MembersService membersService;

    @GetMapping("/join")
    public String join(){
        return "join";
    }
    @PostMapping("/memberJoin")
    @ResponseBody
    public Boolean memberJoin(
        @ModelAttribute MembersDTO membersDTO
    ){
        Boolean joinResult= membersService.joinMember(membersDTO);
        return joinResult;
    }

    @GetMapping("/loginForm")
    public String login(
        @RequestParam(value="error", required=false) String error,
        @RequestParam(value="errMessage", required=false) String exception,
        Model model
        ){
        model.addAttribute("error",error);
        model.addAttribute("errMessage",exception);
        return "loginForm";
    };
}
