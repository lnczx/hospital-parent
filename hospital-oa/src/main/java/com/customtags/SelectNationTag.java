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

public class SelectNationTag extends SimpleTagSupport {

	private String selectedId = "0";

	// 是否包含全部， 0 = 不包含 1= 包含
	private String hasAll = "1";

	private DictService dictService;

	public SelectNationTag() {
	}

	@Override
	public void doTag() throws JspException, IOException {
		try {

			WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(((PageContext) getJspContext()).getServletContext());
			dictService = springContext.getBean(DictService.class);

			List<Dicts> optionList = dictService.LoadNationData();

			StringBuffer selectHtml = new StringBuffer();
			selectHtml.append("<select id = \"nationId\" name=\"nationId\" class=\"select\">");

			if (hasAll.equals("1")) {
				selectHtml.append("<option value='0' >民族</option>");
			}

			Dicts item = null;
			String selected = "";
			for (int i = 0; i < optionList.size(); i++) {
				item = optionList.get(i);
				selected = "";
				if (selectedId != null && item.getId().toString().equals(selectedId)) {
					selected = "selected=\"selected\"";
				}
				selectHtml.append("<option value='" + item.getId() + "' " + selected + ">" + item.getName() + "</option>");
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

}
