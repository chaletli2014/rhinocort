<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html lang="en-US">
<%@include file="header.jsp" %> 
<script type="text/javascript">
</script>
<body onload="init();checkMessage('${message}')">
    <div style="position:absolute; left:-9999px;"><a href="#" id="setfoc"></a></div>
    <div data-role="page" id="home">
        <jsp:include page="page_header.jsp" flush="true">
            <jsp:param name="title" value="每周报告"/>
            <jsp:param name="basePath" value="<%=basePath%>"/>
        </jsp:include>
        <div data-role="content"  data-theme="a">
            <div class="roundCorner" style="padding:4px;background:#fff;">
                <div class="dailyReport_table_Title">${title}</div>
                <table class="mobileReport_table" id="weeklyReportTable">
                   <tr class="mobileReport_table_header">
                       <td width="40%">姓名</td>
                       <td width="20%">上报率</td>
                       <td width="20%">过敏性鼻炎占比</td>
                       <td width="20%">雷诺考特占比</td>
                   </tr>
                   <c:forEach items="${weeklyData}" var="weeklyData" varStatus="status">
                       <tr id="${weeklyData.level}-${weeklyData.name}" class="mobileReport_table_body <c:if test="${status.count%2==0}">mobileReport_tr_even</c:if>" onclick="showlowerRSMReport(this)">
                           <td>${weeklyData.name}</td>
                           <td class="report_data_number"><fmt:formatNumber type="percent" value="${weeklyData.inRate}" pattern="#0%"/></td>
                           <td class="report_data_number"><fmt:formatNumber type="percent" value="${weeklyData.num2Rate}" pattern="#0%"/></td>
                           <td class="report_data_number"><fmt:formatNumber type="percent" value="${weeklyData.num3Rate}" pattern="#0%"/></td>
                       </tr>
                   </c:forEach>
                </table>
            </div>
        </div>
        <iframe src="${reportFile}" class="weeklyReport_iframe"></iframe>
        <jsp:include page="page_footer.jsp">
            <jsp:param value="<%=basePath%>" name="basePath"/>
            <jsp:param value="index" name="backURL"/>
        </jsp:include>
    </div>
</body>  
</html>  