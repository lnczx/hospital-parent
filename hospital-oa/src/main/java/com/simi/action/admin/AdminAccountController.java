package com.simi.action.admin;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.simi.oa.auth.AccountAuth;
import com.simi.oa.auth.AuthHelper;
import com.simi.oa.auth.AuthPassport;
import com.simi.oa.common.ConstantOa;
import com.simi.oa.common.ValidatException;
import com.simi.oa.vo.account.AccountRegisterVo;
import com.simi.po.model.admin.AdminAccount;
import com.simi.service.admin.AdminAccountService;
import com.simi.service.admin.AdminAuthorityService;
import com.simi.service.admin.AdminRoleService;
import com.simi.vo.admin.AccountSearchVo;
import com.github.pagehelper.PageInfo;
import com.hos.common.ConstantMsg;
import com.hos.common.Constants;
import com.meijia.utils.BeanUtilsExp;
import com.meijia.utils.StringUtil;
import com.meijia.utils.vo.AppResultData;

@Controller
@RequestMapping(value = "/account")
public class AdminAccountController extends AdminController {

	@Autowired
	private AdminAccountService adminAccountService;

	@Autowired
	private AdminRoleService adminRoleService;

	@Autowired
	private AdminAuthorityService adminAuthorityService;

	@AuthPassport
	@RequestMapping(value = "/adminForm", method = { RequestMethod.GET })
	public String register(HttpServletRequest request, Model model) {
		Long ids = Long.valueOf(request.getParameter("id"));

		if (ids == null) {
			ids = 0L;
		}
		AdminAccount account = adminAccountService.initAccount();

		AccountRegisterVo accountRegisterVo = new AccountRegisterVo();
		if (ids != null && ids > 0L) {
			account = adminAccountService.selectByPrimaryKey(ids);
			BeanUtils.copyProperties(account, accountRegisterVo);

		}
		accountRegisterVo.setId(account.getId());

		model.addAttribute("formData", account);

		return "account/adminForm";
	}

	@AuthPassport
	@RequestMapping(value = "/adminForm", method = { RequestMethod.POST })
	public String register(HttpServletRequest request, Model model, @Valid @ModelAttribute("adminAccount") AccountRegisterVo accountRegisterVo,
			BindingResult result) throws ValidatException, NoSuchAlgorithmException {
		Long id = Long.valueOf(request.getParameter("id")); // 主键id

		AdminAccount account = null;
		
		account = adminAccountService.initAccount();
		String oldPassword = account.getPassword();
		// 新增
		if (id > 0L) {
			account = adminAccountService.selectByPrimaryKey(id);
			oldPassword = account.getPassword();
		}

		BeanUtils.copyProperties(accountRegisterVo, account);

		if (id.equals(0L)) {
			account.setId(0L);
			String password = account.getPassword();
			password = StringUtil.md5(password.trim());
			account.setPassword(password);
			adminAccountService.insertSelective(account);
		} else {
			account.setPassword(oldPassword);
			adminAccountService.updateByPrimaryKeySelective(account);
		}

		model.addAttribute("adminAccount", account);

		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		if (returnUrl == null)
			returnUrl = "/account/list";
		return "redirect:/account/list";
	}
	

	
	@AuthPassport
	@RequestMapping(value = "/adminPasswordForm", method = { RequestMethod.GET })
	public String adminPassWordForm(HttpServletRequest request, Model model) {
		Long ids = Long.valueOf(request.getParameter("id"));

		
		AdminAccount account = adminAccountService.selectByPrimaryKey(ids);

		model.addAttribute("formData", account);

		return "account/adminPasswordForm";
	}
	
	@AuthPassport
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public AppResultData<Object> changePassword(@RequestParam("id") Long id, @RequestParam("password") String password) throws NoSuchAlgorithmException {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		AdminAccount account = adminAccountService.selectByPrimaryKey(id);
		
		password = StringUtil.md5(password);
		account.setPassword(password);
		adminAccountService.updateByPrimaryKey(account);
		return result;
	}

	@AuthPassport
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(HttpServletRequest request, Model model, AccountSearchVo searchVo) {
		model.addAttribute("requestUrl", request.getServletPath());
		model.addAttribute("requestQuery", request.getQueryString());

		model.addAttribute("searchModel", searchVo);
		int pageNo = ServletRequestUtils.getIntParameter(request, ConstantOa.PAGE_NO_NAME, ConstantOa.DEFAULT_PAGE_NO);
		int pageSize = ConstantOa.DEFAULT_PAGE_SIZE;
		
		PageInfo pageInfo = adminAccountService.listPage(searchVo, pageNo, pageSize);
		model.addAttribute("contentModel", pageInfo);

		return "account/adminList";
	}

	/**
	 * 根据id跳转到用户绑定权限的界面
	 * 
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/authorize/{id}", method = { RequestMethod.GET })
	public String authorize(HttpServletRequest request, Model model, @PathVariable(value = "id") String id) {
		Long ids = Long.valueOf(id.trim());
		if (!model.containsAttribute("contentModel")) {
			AdminAccount adminAccount = adminAccountService.selectByPrimaryKey(ids);
			model.addAttribute("contentModel", adminAccount);
		}
		model.addAttribute(selectDataSourceName, adminRoleService.getSelectSource());
		return "account/authorize";
	}

	/**
	 * 根据id更新改用户的权限
	 * 
	 * @param request
	 * @param model
	 * @param adminAccount
	 * @param id
	 * @param result
	 * @return
	 */
	@AuthPassport
	@RequestMapping(value = "/authorize/{id}", method = { RequestMethod.POST })
	public String authorize(HttpServletRequest request, Model model, @Valid @ModelAttribute("contentModel") AdminAccount adminAccount,
			@PathVariable(value = "id") String id, BindingResult result) {
		Long ids = Long.valueOf(id.trim());
		if (result.hasErrors())
			return authorize(request, model, id);
		if (adminAccount != null) {
			adminAccountService.updateBind(ids, adminAccount.getRoleId(), adminAccount.getOrgId(), adminAccount.getImUsername());
		}
		String returnUrl = ServletRequestUtils.getStringParameter(request, "returnUrl", null);
		if (returnUrl == null)
			returnUrl = "/account/list";
		return "redirect:" + returnUrl;
	}

	/*
	 * 根据id将该用户置为可用，跳转到用户的list页面
	 */
	@AuthPassport
	@RequestMapping(value = "/enable/{id}", method = { RequestMethod.GET })
	public String enableAdminRole(Model model, @PathVariable(value = "id") String id, HttpServletRequest response) {
		Long ids = Long.valueOf(id.trim());
		AdminAccount adminAccount = adminAccountService.selectByPrimaryKey(ids);
		if (adminAccount != null) {
			adminAccount.setEnable(ConstantOa.ROLE_ENABLE);
			adminAccountService.updateByPrimaryKeySelective(adminAccount);
		}
		return "redirect:/account/list";
	}

	/*
	 * 根据id将该用户置为不可用，跳转到用户的list页面
	 */
	@AuthPassport
	@RequestMapping(value = "/disable", method = { RequestMethod.POST })
	public AppResultData<Object> disabledAccount(HttpServletRequest request, @RequestParam("id") Long id) {
		
		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		AccountAuth accountAuth = AuthHelper.getSessionAccountAuth(request);
		
		Long sessionId = accountAuth.getId();
		if (id.equals(sessionId)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("不能停用自己.");
			return result;
		}
		
		Long orgId = accountAuth.getOrgId();
		if (!orgId.equals(0L)) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("权限不足.");
			return result;
		}

		AdminAccount adminAccount = adminAccountService.selectByPrimaryKey(id);
		if (adminAccount != null) {
			adminAccount.setEnable(ConstantOa.ROLE_DISABLE);
			adminAccountService.updateByPrimaryKeySelective(adminAccount);
		}
		return result;
	}

	/*
	 * 根据id删除选中的用户，跳转到用户的list页面
	 */
	@AuthPassport
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.GET })
	public String deleterAdminRole(Model model, @PathVariable(value = "id") String id, HttpServletRequest response) {
		Long ids = Long.valueOf(id.trim());
		String path = "redirect:/account/list";
		int result = adminAccountService.deleteByPrimaryKey(ids);
		if (result >= 0) {
			return path;
		} else {
			return "error";
		}
	}

	@AuthPassport
	@RequestMapping(value = "/checkUsername", method = RequestMethod.GET)
	public AppResultData<Object> checkUserName(@RequestParam("id") Long id, @RequestParam("username") String username) {

		AppResultData<Object> result = new AppResultData<Object>(Constants.SUCCESS_0, ConstantMsg.SUCCESS_0_MSG, "");
		
		AccountSearchVo searchVo = new AccountSearchVo();
		searchVo.setUsername(username);
		List<AdminAccount> list = adminAccountService.selectBySearchVo(searchVo);
		AdminAccount adminAccount = null;

		if (!list.isEmpty()) {
			adminAccount = list.get(0);
		}

		if (adminAccount != null && adminAccount.getId() > 0 && adminAccount.getId() != id) {
			result.setStatus(Constants.ERROR_999);
			result.setMsg("用户名已经被注册，请重新输入.");
			return result;
		}
		
		return result;
	}

}
