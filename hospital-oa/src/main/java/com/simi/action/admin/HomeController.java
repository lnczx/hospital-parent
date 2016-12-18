package com.simi.action.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.simi.oa.auth.AuthPassport;


@Controller
@RequestMapping(value = "/home")
public class HomeController extends AdminController {
	
    @AuthPassport
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model) {
    	
        return "home/index";
    }

    @RequestMapping(value = "/notfound")
    public ModelAndView notfound() {

    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("404");

    	return mv;
    }
}
