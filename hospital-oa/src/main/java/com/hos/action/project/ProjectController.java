package com.hos.action.project;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.simi.action.BaseController;
import com.simi.oa.auth.AuthPassport;


@Controller
@RequestMapping(value = "/project")
public class ProjectController extends BaseController {
	
    @AuthPassport
    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request, Model model) {
    	
        return "project/project-list";
    }

}
