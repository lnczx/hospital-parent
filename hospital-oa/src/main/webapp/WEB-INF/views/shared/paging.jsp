<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.github.pagehelper.PageInfo" import="com.meijia.utils.PageUtil" import="com.hos.common.Constants"
	import="org.apache.taglibs.standard.tag.common.core.UrlSupport"%>
<%
	String urlAddress = request.getParameter("urlAddress");
	String pageModelName = request.getParameter("pageModelName");
	Integer pageCount = Constants.PAGE_MAX_NUMBER;
	if (request.getParameter("pageCount") != null && !request.getParameter("pageCount").isEmpty()) {
		pageCount = Integer.valueOf(request.getParameter("pageCount"));
	}
%>
<%
	if (pageModelName == null || pageModelName.isEmpty()) {
%>
未获取到分页标识！
<%
	} else if (urlAddress == null || urlAddress.isEmpty()) {
%>
未获取到url地址！
<%
	} else if (pageCount < 10) {
%>
分页数量不能小于10！
<%
	}

	PageInfo pageInfo = (PageInfo) request.getAttribute(pageModelName);
	if (pageInfo == null) {
%>
未获取到分页内容！
<%
	} else {
%>
<div class="dataTables_info" >
	每页显示<%=pageInfo.getPageSize()%>条 ，共
	<%=pageInfo.getTotal()%>条
</div>
<div class="dataTables_paginate paging_simple_numbers">
	<%
		String queryString = request.getQueryString();
			urlAddress = UrlSupport.resolveUrl(urlAddress, null, pageContext);
			if (pageInfo.isHasPreviousPage()) {
				String preUrl = PageUtil.resolveUrl(urlAddress, queryString, pageInfo.getPageNum() - 1, null);
	%>
	<a href="<%=preUrl%>" class="paginate_button previous" data-dt-idx="0" tabindex="0"
		id="DataTables_Table_0_previous">上一页</a>
	<%
		} else {
	%>
	<a class="paginate_button previous disabled"  data-dt-idx="0" tabindex="0"
		id="DataTables_Table_0_previous">上一页</a>
	<%
		}
			if (pageInfo.getPages() <= pageCount) {
				for (int i = 1; i <= pageInfo.getPages(); i++) {
					if (i == pageInfo.getPageNum()) {
	%>
	<span>
		<a class="paginate_button current"  data-dt-idx="1" tabindex="0"><%=i%></a>
	</span>
	<%
		} else {
						String pageUrl = PageUtil.resolveUrl(urlAddress, queryString, i, null);
	%>
	<span>
		<a href="<%=pageUrl%>" class="paginate_button "  data-dt-idx="1" tabindex="0"><%=i%></a>
	</span>
	<%
		}
				}
			} else {
				for (int i = 1; i <= pageCount; i++) {
					if (pageInfo.getPageNum() <= pageCount / 2) {
						if (i == pageInfo.getPageNum()) {
	%>
	<span>
		<a class="paginate_button current"  data-dt-idx="1" tabindex="0"><%=i%></a>
	</span>
	<%
		} else if (pageCount - i > 2) {
							String pageUrl = PageUtil.resolveUrl(urlAddress, queryString, i, null);
	%>
	<span>
		<a href="<%=pageUrl%>" class="paginate_button "  data-dt-idx="1" tabindex="0"><%=i%></a>
	</span>
	<%
		} else if (i == pageCount - 2) {
	%>
	<span>...</span>
	<%
		} else {
							int pageNo = pageInfo.getPages() - (pageCount - i);
							String pageUrl = PageUtil.resolveUrl(urlAddress, queryString, pageNo, null);
	%>
	<span>
		<a href="<%=pageUrl%>" class="paginate_button "  data-dt-idx="1" tabindex="0"><%=pageNo%></a>
	</span>
	<%
		}
					} else if (pageInfo.getPages() - pageInfo.getPageNum() < pageCount / 2) {
						if (i < 3) {
							String pageUrl = PageUtil.resolveUrl(urlAddress, queryString, i, null);
	%>
	<span>
		<a href="<%=pageUrl%>" class="paginate_button "  data-dt-idx="1" tabindex="0"><%=i%></a>
	</span>
	<%
		} else if (i == 3) {
	%>
	<span>...</span>
	<%
		} else {
							int pageNo = pageInfo.getPages() - (pageCount - i);
							if (pageNo == pageInfo.getPageNum()) {
	%>
	<span>
		<a href="#" class="paginate_button current"  data-dt-idx="1" tabindex="0"><%=pageNo%></a>
	</span>
	<%
		} else {
								String pageUrl = PageUtil.resolveUrl(urlAddress, queryString, pageNo, null);
	%>
	<span>
		<a href="<%=pageUrl%>" class="paginate_button"  data-dt-idx="1" tabindex="0"><%=pageNo%></a>
	</span>
	<%
		}
						}
					} else {
						if (i < 3) {
							String pageUrl = PageUtil.resolveUrl(urlAddress, queryString, i, null);
	%>
	<span>
		<a href="<%=pageUrl%>" class="paginate_button"  data-dt-idx="1" tabindex="0"><%=i%></a>
	</span>
	<%
		} else if (i == 3 || i == pageCount - 2) {
	%>
	<span>...</span>
	<%
		} else if (i > pageCount - 2) {
							int pageNo = pageInfo.getPages() - (pageCount - i);
							String pageUrl = PageUtil.resolveUrl(urlAddress, queryString, pageNo, null);
	%>
	<span>
		<a href="<%=pageUrl%>" class="paginate_button"  data-dt-idx="1" tabindex="0"><%=pageNo%></a>
	</span>
	<%
		} else {
							if (pageCount % 2 == 0) {
								if (i == pageCount / 2) {
	%>
	<span>
		<a href="#" class="paginate_button current"  data-dt-idx="1" tabindex="0"><%=pageInfo.getPageNum()%></a>
	</span>
	<%
		} else if (i < pageCount / 2) {
									int pageNo = pageInfo.getPageNum() - (pageCount / 2 - i);
									String pageUrl = PageUtil.resolveUrl(urlAddress, queryString, pageNo, null);
	%>
	<span>
		<a href="<%=pageUrl%>" class="paginate_button current"  data-dt-idx="1" tabindex="0"><%=pageNo%></a>
	</span>
	<%
		} else {
									int pageNo = pageInfo.getPageNum() + (i - pageCount / 2);
									String pageUrl = PageUtil.resolveUrl(urlAddress, queryString, pageNo, null);
	%>
	<span>
		<a href="<%=pageUrl%>" class="paginate_button current"  data-dt-idx="1" tabindex="0"><%=pageNo%></a>
	</span>
	<%
		}
							} else {
								if (i == (pageCount + 1) / 2) {
	%>
	<span>
		<a href="#" class="paginate_button current"  data-dt-idx="1" tabindex="0"><%=pageInfo.getPageNum()%></a>
	</span>
	<%
		} else if (i < (pageCount + 1) / 2) {
									int pageNo = pageInfo.getPageNum() - ((pageCount + 1) / 2 - i);
									String pageUrl = PageUtil.resolveUrl(urlAddress, queryString, pageNo, null);
	%>
	<span>
		<a href="<%=pageUrl%>" class="paginate_button current"  data-dt-idx="1" tabindex="0"><%=pageNo%></a>
	</span>
	<%
		} else {
									int pageNo = pageInfo.getPageNum() + (i - (pageCount + 1) / 2);
									String pageUrl = PageUtil.resolveUrl(urlAddress, queryString, pageNo, null);
	%>
	<span>
		<a href="<%=pageUrl%>" class="paginate_button current"  data-dt-idx="1" tabindex="0"><%=pageNo%></a>
	</span>
	<%
		}
							}
						}
					}
				}
			}
			if (pageInfo.isHasNextPage()) {
				String nextUrl = PageUtil.resolveUrl(urlAddress, queryString, pageInfo.getPageNum() + 1, null);
	%>
	<a href="<%=nextUrl%>" class="paginate_button next"  data-dt-idx="2" tabindex="0"
		id="DataTables_Table_0_next">下一页</a>
	<%
		} else {
	%>
	<a class="paginate_button next disabled"  data-dt-idx="2" tabindex="0"
		id="DataTables_Table_0_next">下一页</a>
	<%
		}
	%>
</div>
<%
	}
%>
