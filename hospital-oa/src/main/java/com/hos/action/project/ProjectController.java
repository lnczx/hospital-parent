package com.hos.action.project;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageInfo;
import com.hos.service.project.ProjectService;
import com.hos.vo.project.ProjectSearchVo;
import com.simi.action.BaseController;
import com.simi.oa.auth.AccountAuth;
import com.simi.oa.auth.AuthHelper;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;


@Controller
@RequestMapping(value = "/project")
public class ProjectController extends BaseController {
	@Autowired
	private ProjectService projectService;
	
    @AuthPassport
    @RequestMapping(value = "/list", method = { RequestMethod.GET })
    public String list(HttpServletRequest request, Model model, ProjectSearchVo searchVo) {
    	
    	model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ConstantOa.DEFAULT_PAGE_SIZE;
    	
    	AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
    	
    	Long orgId = accountAuth.getOrgId();
    	if (orgId > 0L) {
    		searchVo.setOrgId(orgId);
    	}
    	
    	@SuppressWarnings("rawtypes")
		PageInfo pageInfo = projectService.selectByListPage(searchVo, pageNo, pageSize);

    	
    	model.addAttribute("contentModel", pageInfo);
    	
        return "project/projectList";
    }
    
    @AuthPassport
    @RequestMapping(value = "/project-import", method = { RequestMethod.GET })
    public String projectImport(HttpServletRequest request, Model model) {
    	
        return "project/projectImport";
    }

}
