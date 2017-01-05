package com.simi.action.admin;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hos.common.ConstantMsg;
import com.hos.common.Constants;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.StringUtil;
import com.simi.oa.auth.AccountAuth;
import com.simi.oa.auth.AuthHelper;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ValidatException;
import com.simi.oa.vo.account.AccountRegisterVo;
import com.simi.po.model.admin.AdminAccount;
import com.simi.service.admin.AdminAccountService;
import com.simi.vo.AppResultData;


@Controller
@RequestMapping(value = "/home")
public class HomeController extends AdminController {
	
	@Autowired
	private AdminAccountService adminAccountService;
	
    @AuthPassport
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model) {
    	
        return "home/index";
    }
    
    @AuthPassport
    @RequestMapping(value = "/help")
    public String help(HttpServletRequest request, Model model) {
    	
        return "home/help";
    }

    @RequestMapping(value = "/notfound")
    public ModelAndView notfound() {

    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("404");

    	return mv;
    }
    
	@AuthPassport
	@RequestMapping(value = "/myForm", method = { RequestMethod.GET })
	public String myForm(HttpServletRequest request, Model model) {

		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long id = accountAuth.getId();

		AccountRegisterVo accountRegisterVo = new AccountRegisterVo();
		
		AdminAccount account = adminAccountService.selectByPrimaryKey(id);
		BeanUtils.copyProperties(account, accountRegisterVo);

		accountRegisterVo.setId(account.getId());

		model.addAttribute("formData", account);

		return "account/myForm";
	}

	@AuthPassport
	@RequestMapping(value = "/myForm", method = { RequestMethod.POST })
	public AppResultData<Object> doMyForm(HttpServletRequest request, 
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "password", required = false, defaultValue = "") String password,
			@RequestParam(value = "mobile", required = false, defaultValue = "") String mobile,
			@RequestParam(value = "email", required = false, defaultValue = "") String email
			) throws NoSuchAlgorithmException  {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");

		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		Long id = accountAuth.getId();

		AdminAccount account = adminAccountService.selectByPrimaryKey(id);	
		account = adminAccountService.selectByPrimaryKey(id);
		String oldPassword = account.getPassword();
		
		if (!StringUtil.isEmpty(name)) account.setName(name);
		
		if (!StringUtil.isEmpty(mobile)) account.setMobile(mobile);
		
		if (!StringUtil.isEmpty(email)) account.setEmail(email);
		
		if (!StringUtil.isEmpty(password)) {
			password = StringUtil.md5(password.trim());
			account.setPassword(password);
		} else {
			account.setPassword(oldPassword);
		}

		adminAccountService.updateByPrimaryKeySelective(account);

		return result;
	}	    
    
}
