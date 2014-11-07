<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html lang="en-US">
<%@include file="header.jsp" %>
<body onload="init();checkMessage('${message}')">
    <div style="position:absolute; left:-9999px;"><a href="#" id="setfoc"></a></div>
    <div data-role="page" id="home" data-theme="a">
        <jsp:include page="page_header.jsp" flush="true">
        	<jsp:param name="title" value="销售数据采集系统"/>
        	<jsp:param name="basePath" value="<%=basePath%>"/>
        </jsp:include>
        <div data-role="content" data-theme="a">
            <div class="department_img_div">
	            <img alt="" src="<%=basePath%>images/img_bg_dataform.png" onclick="javascript:window.location.href='<%=basePath%>rhinocort'" style="cursor: pointer;">
        	</div>
            <div class="department_img_div">
	            <img alt="" src="<%=basePath%>images/img_bg_weeklyreport.png" onclick="javascript:window.location.href='<%=basePath%>weeklyreport'" style="cursor: pointer;">
        	</div>
            <div class="department_img_div">
	            <img alt="" src="<%=basePath%>images/img_bg_share.png" onclick="showCustomrizedMessage('功能正在建设中')" style="cursor: pointer;">
        	</div>
        </div>
        <jsp:include page="page_footer.jsp" flush="true">
            <jsp:param value="<%=basePath%>" name="basePath"/>
            <jsp:param value="index" name="currentPage"/>
        </jsp:include>
    </div>
</body>  
</html>  