<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div data-role="header" data-position="fixed" data-backbtn="false">
	<div class="ui-grid-a">
		<div class="ui-block-a">
			<img alt="" src="<%=request.getParameter("basePath")%>images/logo_butterfly.png" style="vertical-align: middle;"/>
			<img alt="" src="<%=request.getParameter("basePath")%>images/logo_200.png" style="vertical-align: middle;"/>
		</div>
		<div class="ui-block-b" style="text-align: right;height:44px;">
		    <label style="color:#0D713D;font-weight:bold;background:url(<%=request.getParameter("basePath")%>images/header_timer.png) no-repeat;padding:4px;position: relative;top:30%;">
				<strong id="currentDate"></strong>
			</label>
		</div>
	</div>
	<div style="text-align: center;font-weight:normal; background: #008E87;color:#fff;padding:4px;">
		<%=request.getParameter("title") %>
	</div>
</div>