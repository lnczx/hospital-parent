package com.customtags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hos.po.model.dict.Dicts;
import com.hos.service.dict.DictService;
import com.simi.po.model.admin.AdminRole;
import com.simi.service.admin.AdminRoleService;

public class SelectRoleTag extends SimpleTagSupport {

	private String selectedId = "0";

	// 是否包含全部， 0 = 不包含 1= 包含
	private String hasAll = "1";

	private AdminRoleService roleService;

	public SelectRoleTag() {
	}

	@Override
	public void doTag() throws JspException, IOException {
		try {
			
			WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(((PageContext) getJspContext()).getServletContext());
			roleService = springContext.getBean(AdminRoleService.class);
			
			List<AdminRole> list = roleService.selectAll();
			
			StringBuffer roleSelect = new StringBuffer();
			
			roleSelect.append("<select id = \"roleId\" name=\"roleId\" class=\"select\">");

			roleSelect.append("<option value='' >用户类型</option>");
			
			AdminRole role = new AdminRole();
			
			String selected = "";
			
			for (int i = 1; i < list.size(); i++) {
				role = list.get(i);
				selected = "";
				if (selectedId != null && role.getId().equals(Long.valueOf(selectedId))) {
					selected = "selected=\"selected\"";
				}
				roleSelect.append("<option value='" + role.getId() + "' "
						+ selected + ">" + role.getName() + "</option>");
			}

			roleSelect.append("</select>");

			getJspContext().getOut().write(roleSelect.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getHasAll() {
		return hasAll;
	}

	public void setHasAll(String hasAll) {
		this.hasAll = hasAll;
	}

	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

}
