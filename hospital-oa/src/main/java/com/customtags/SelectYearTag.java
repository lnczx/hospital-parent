package com.customtags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hos.po.model.dict.Dicts;
import com.hos.service.dict.DictService;
import com.meijia.utils.DateUtil;

public class SelectYearTag extends SimpleTagSupport {

	public SelectYearTag() {
	}

	@Override
	public void doTag() throws JspException, IOException {
		try {

			StringBuffer selectHtml = new StringBuffer();
			selectHtml.append("<select id = \"selectYear\" name=\"selectYear\" class=\"select\">");

			int curYear = DateUtil.getYear();
			

			Dicts item = null;
			String selected = "";
			for (int i = curYear - 5; i < curYear + 5; i++) {
				
				selected = "";
				if (curYear == i) {
					selected = "selected=\"selected\"";
				}
				selectHtml.append("<option value='" + i + "' " + selected + ">" + i + "</option>");
			}

			selectHtml.append("</select>");

			getJspContext().getOut().write(selectHtml.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
