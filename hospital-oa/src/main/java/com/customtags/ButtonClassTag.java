package com.customtags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.meijia.utils.DateUtil;
import com.meijia.utils.StringUtil;

public class ButtonClassTag extends SimpleTagSupport {
	
	private Boolean hasData = false;
	
	private Short status = 0;
	
	public ButtonClassTag() {
	}

	@Override
	public void doTag() throws JspException, IOException {
		try {

			String btnClass = "";
			
			//说明：下方按钮为绿色时表示已提交，红色表示退回，蓝色表示有数据但未提交，灰色表示提交后不能操作。
			if (!hasData) {
				btnClass = "class = \" btn size-S radius btn-primary-outline\"";
			} else {
				if (status.equals((short)0)) btnClass = "class = \" btn size-S radius btn-secondary\"";
				if (status.equals((short)1)) btnClass = "class = \" btn size-S radius btn-success\"";
				if (status.equals((short)2)) btnClass = "class = \" btn size-S radius btn-danger\"";
			}

			getJspContext().getOut().write(btnClass.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Boolean getHasData() {
		return hasData;
	}

	public void setHasData(Boolean hasData) {
		this.hasData = hasData;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

}
