<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html lang="en-US">
<%@include file="header.jsp" %> 
<script type="text/javascript">
function loadData(hospitalCode){
	$.mobile.showPageLoadingMsg('b','数据加载中',false);
	window.location.href="<%=basePath%>rhinocort?selectedHospital="+hospitalCode;
}
function submitForm(){
    if(checkForm()){
        $.mobile.showPageLoadingMsg('b','数据提交中',false);
        if( $('.submit_btn') ){
			$('.submit_btn').removeAttr("onclick");
		}
        $('#pediatricsForm').submit();
    }
}
function checkForm(){
    if( !checkIsNotNull( $("#hospital"),$("#num1"),$("#num2"),$("#num3"),$("#num4"),$("#num5") ) ){
        showCustomrizedMessage("数据不能为空或者字母");
        return false;
    }
	
	if( !isInteger($("#num1"),$("#num2"),$("#num3"),$("#num4"),$("#num5"))  ){
        return false;
	}
    return true;
}
</script>
<body onload="init();checkMessage('${message}')">
    <div style="position:absolute; left:-9999px;"><a href="#" id="setfoc"></a></div>
    <div data-role="page" id="home">
        <jsp:include page="page_header.jsp" flush="true">
        	<jsp:param name="title" value="提交数据"/>
        	<jsp:param name="basePath" value="<%=basePath%>"/>
        </jsp:include>
        <div data-role="content" data-theme="a" class="rhinocort_data_form">
        	<div class="roundCorner">
            <form id="pediatricsForm" action="doCollectData" method="POST" data-ajax="false">
            	<input type="hidden" name="dataId" value="${existedData.dataId}"/>
	        	<input type="hidden" name="selectedHospital" value="${selectedHospital}"/>
                <div data-role="fieldcontain">
                    <label for="hospital" class="select">医院名称</label>
                    <select name="hospital" id="hospital" onchange="loadData(this.value)">
                        <option value="">--请选择--</option>
	                    <c:forEach var="hospital" items="${hospitals}">
	                        <option value="${hospital.code}" <c:if test="${hospital.code == selectedHospital}">selected</c:if>>${hospital.name}</option>
	                    </c:forEach>
	                </select>
                </div>
               	<div data-role="fieldcontain" class="formCollection">
                    <label for="num1" id="num1_label">五官科门诊病人数</label>
                    <input type="number" name="num1" id="num1" value="${existedData.num1==null?0:existedData.num1}"/>
                </div>
              	<div data-role="fieldcontain" class="formCollection">
                   <label for="num2" id="num2_label">过敏性鼻炎病人数</label>
                   <input type="number" name="num2" id="num2" value="${existedData.num2==null?0:existedData.num2}"/>
                </div>
                <div data-role="fieldcontain"  class="formCollection">
                    <label for="num3" id="num3_label">处方雷诺考特的过敏性鼻炎病人数</label>
                    <input type="number" name="num3" id="num3" value="${existedData.num3==null?0:existedData.num3}"/>
                </div>
                <div data-role="fieldcontain"  class="formCollection">
                    <label for="num6" id="num6_label">过敏鼻炎患者中使用鼻用激素的人数</label>
                    <input type="number" name="num6" id="num6" value="${existedData.num6==null?0:existedData.num6}"/>
                </div>
                <div data-role="fieldcontain"  class="formCollection">
                    <label for="num4" id="num4_label">常年性鼻炎病人数</label>
                    <input type="number" name="num4" id="num4" value="${existedData.num4==null?0:existedData.num4}"/>
                </div>
                <div data-role="fieldcontain"  class="formCollection">
                    <label for="num5" id="num5_label">处方雷诺考特的常年性鼻炎病人数</label>
                    <input type="number" name="num5" id="num5" value="${existedData.num5==null?0:existedData.num5}"/>
                </div>
                <div data-role="fieldcontain"  class="formCollection">
                    <label for="num7" id="num7_label">常年鼻炎患者中使用鼻用激素的人数</label>
                    <input type="number" name="num7" id="num7" value="${existedData.num7==null?0:existedData.num7}"/>
                </div>
                <div style="text-align: center;">
		            <a class="submit_btn" href="javascript:void(0)" onclick="submitForm()">
			            <img alt="" src="<%=basePath%>images/button_submit.png" style="cursor: pointer;" />
	            	</a>
	            </div>
            </form>
            </div>
        </div>
        <jsp:include page="page_footer.jsp">
            <jsp:param value="<%=basePath%>" name="basePath"/>
            <jsp:param value="index" name="backURL"/>
        </jsp:include>
    </div>
</body>
</html>