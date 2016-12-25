package com.customtags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hos.po.model.dict.DictOrgs;
import com.hos.po.model.dict.Dicts;
import com.hos.service.dict.DictOrgService;
import com.hos.service.dict.DictService;
import com.hos.vo.dict.DictOrgSearchVo;
import com.simi.oa.auth.AuthHelper;

public class SelectOrgTag extends SimpleTagSupport {

	private String selectedId = "0";
	
	private Long sessionOrgId = 0L;;

	// 是否包含全部， 0 = 不包含 1= 包含
	private String hasAll = "1";

	private DictOrgService dictOrgService;

	public SelectOrgTag() {
	}

	@Override
	public void doTag() throws JspException, IOException {
		try {

			WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(((PageContext) getJspContext()).getServletContext());
			dictOrgService = springContext.getBean(DictOrgService.class);

			DictOrgSearchVo searchVo = new DictOrgSearchVo();
			
			if (sessionOrgId.equals(0L)) {
				searchVo.setIsOrg((short) 1);
			} else {
				searchVo.setOrgId(sessionOrgId);
			}

			List<DictOrgs> optionList = dictOrgService.selectBySearchVo(searchVo);

			StringBuffer selectHtml = new StringBuffer();
			selectHtml.append("<select id = \"orgId\" name=\"orgId\" class=\"select\">");

			if (hasAll.equals("1")) {
				selectHtml.append("<option value='0' >选择二级单位</option>");
			}

			DictOrgs item = null;
			String selected = "";
			for (int i = 0; i < optionList.size(); i++) {
				item = optionList.get(i);
				selected = "";
				if (selectedId != null && item.getOrgId().toString().equals(selectedId)) {
					selected = "selected=\"selected\"";
				}
				selectHtml.append("<option value='" + item.getOrgId() + "' " + selected + ">" + item.getName() + "</option>");
			}

			selectHtml.append("</select>");

			getJspContext().getOut().write(selectHtml.toString());
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

	public Long getSessionOrgId() {
		return sessionOrgId;
	}

	public void setSessionOrgId(Long sessionOrgId) {
		this.sessionOrgId = sessionOrgId;
	}

}
