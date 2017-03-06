package com.customtags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;

public class SelectYearTag extends SimpleTagSupport {
	
	private String selectedId = "";
	
	public SelectYearTag() {
	}

	@Override
	public void doTag() throws JspException, IOException {
		try {

			StringBuffer selectHtml = new StringBuffer();
			selectHtml.append("<select id = \"pYear\" name=\"pYear\" class=\"select\">");

			int curYear = DateUtil.getYear();
			
			String selected = "";
			for (int i = curYear - 5; i < curYear + 5; i++) {
				
				selected = "";
				if (!StringUtil.isEmpty(selectedId)) {
					if ( selectedId.equals(String.valueOf(i))) 
						selected = "selected=\"selected\"";
				} else if (curYear == i) {
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

	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

}
