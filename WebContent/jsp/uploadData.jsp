<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html lang="en-US">
<%@include file="header_web.jsp"%>
<script type="text/javascript">
	function uploadAllDataForm() {
		if( $("#allData") && $("#allData").val() == '' ){
			alert('请选择一个文件进行上传');
			return false;
		}
		loading();
		$("#uploadAllDataForm").submit();
	}
	function uploadBMUserData() {
        if( $("#bMData") && $("#bMData").val() == '' ){
            alert('请选择一个文件进行上传');
            return false;
        }
        loading();
        $("#uploadBMUserData").submit();
    }
	function downloadDailyData(){
		if( $("#datepicker") && $("#datepicker").val() == '' || 
				$("#datepicker_end") && $("#datepicker_end").val() == '' ){
			alert('请选择起止日期');
			return false;
		}
		if( compareDate($("#datepicker").val(),$("#datepicker_end").val()) ){
			alert('开始日期不能大于截止日期');
			return false;
		}
		
		loading();
		$("#downloadDailyData").submit();
	}
	function downloadWeeklyData(){
		if( $("#weeklyDataPicker") && $("#weeklyDataPicker").val() == '' ){
			alert('请选择日期');
			return false;
		}
		loading();
		$("#downloadWeeklyData").submit();
	}
</script>
<body onload="checkUploadMessage('${messageareaid}');">
    <div id="upload_loading" class="loading_div" style="display: none;"></div>
	<div id="home">
		<div class="logo_header">
			<img src="<%=basePath%>images/logo_200.png" />
		</div>
		<div class="downloaddata_input_file">
			<div class="download_title">数据下载</div>
			<div class="element_block">
				<div class="element_title">原始数据查询</div>
				<div>
					<form action="doDownloadDailyData" id="downloadDailyData" method="post" enctype="multipart/form-data" data-ajax="false" accept-charset="UTF-8">
						选择日期：<input id="datepicker" type="text" name="chooseDate" class="ls_datepicker" readonly="readonly"/> - <input id="datepicker_end" type="text" name="chooseDate_end" class="ls_datepicker" readonly="readonly"/>
						<img alt="" src="<%=basePath%>images/button_submit.png" style="cursor: pointer; vertical-align: middle;" onclick="downloadDailyData()" />
					</form>
					<c:if test="${dataFile != null}">
						<div id="dailyDataFile">
							<a href="<%=basePath%>${dataFile}">${fn:substringAfter(dataFile,'/')}</a>
						</div>
					</c:if>
				</div>
			</div>
			<div class="element_block">
				<div class="element_title">周报数据查询</div>
				<div>
					<form action="doDownloadWeeklyData" id="downloadWeeklyData" method="post" enctype="multipart/form-data" data-ajax="false" accept-charset="UTF-8">
						选择日期：<input id="weeklyDataPicker" type="text" name="chooseDate" class="ls_datepicker" readonly="readonly"/>
						<img alt="" src="<%=basePath%>images/button_submit.png" style="cursor: pointer; vertical-align: middle;" onclick="downloadWeeklyData()" />
					</form>
					<c:if test="${weeklyDataFile != null}">
						<div id="weeklyDataFile">
							<a href="<%=basePath%>${weeklyDataFile}">${fn:substringAfter(weeklyDataFile,'/')}</a>
						</div>
					</c:if>
				</div>
			</div>
		</div>
		<div class="uploaddata_input_file">
			<div class="upload_title">数据上传</div>
			<div class="element_block">
				<div>上传数据--数据总表</div>
				<div>
					<form id="uploadAllDataForm" action="doUploadAllData" method="post" enctype="multipart/form-data" data-ajax="false" accept-charset="UTF-8">
						<input type="file" name="allData" id="allData" /> 
				        <img alt="" src="<%=basePath%>images/button_submit.png" style="cursor: pointer; vertical-align: middle;" onclick="uploadAllDataForm()" />
				        <div id="uploadAllResult_div" class="uploadDataResult_div" style="display: none;">
                            <c:if test="${message != null && message != ''}">
                                <div>
                                    <div>${message}</div>
                                </div>
                            </c:if>
                        </div>
					</form>
				</div>
			</div>
			<div class="element_block">
                <div>上传数据--BU Head</div>
                <div>
                    <form id="uploadBMUserData" action="doUploadBMUserData" method="post" enctype="multipart/form-data" data-ajax="false" accept-charset="UTF-8">
                        <input type="file" name="bMData" id="bMData" /> 
                        <img alt="" src="<%=basePath%>images/button_submit.png" style="cursor: pointer; vertical-align: middle;" onclick="uploadBMUserData()" />
                        <div id="uploadBMUserResult_div" class="uploadDataResult_div" style="display: none;">
                            <c:if test="${message != null && message != ''}">
                                <div>
                                    <div>${message}</div>
                                </div>
                            </c:if>
                        </div>
                    </form>
                </div>
            </div>
		</div>
	</div>
</body>
</html>
