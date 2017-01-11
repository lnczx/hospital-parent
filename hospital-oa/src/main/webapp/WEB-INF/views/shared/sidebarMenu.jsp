<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="Hui-aside">
	<input runat="server" id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
		<c:forEach items="${sessionScope.accountAuth.accountRole.authorityMenus}" var="item" varStatus="status">
			<c:if test="${item.id > 2}">
				<dl id="menu-${item.id} }">
					<dt class="selected">
						<i class='${ empty item.itemIcon?"icon-list": item.itemIcon}'></i><strong>${ item.name }</strong>
						<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
					</dt>
					<dd style="display: block;">
						<ul class="sub">
							<c:forEach items="${item.childrens}" var="subItem" varStatus="subStatus">
								<li>
								<li>
									<a data-href="<c:url value='${ subItem.url }'/>" data-title="${ subItem.name }" href="javascript:void(0)">${ subItem.name }</a>
								</li>
								</li>
							</c:forEach>
						</ul>
					</dd>
				</dl>
			</c:if>
		</c:forEach>
	</div>
</aside>