<%@page import="com.chalet.rhinocort.utils.RhinocortAttributes"%>
<%@page import="com.chalet.rhinocort.utils.DateUtils,com.chalet.rhinocort.model.UserInfo"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    
    String currentUserTel = (String)request.getSession().getAttribute(RhinocortAttributes.CURRENT_OPERATOR);
    UserInfo currentUser = (UserInfo)request.getSession().getAttribute(RhinocortAttributes.CURRENT_OPERATOR_OBJECT);
%>
<head>
	<title>Rhinocort</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/jquery.mobile-1.3.2.min.css" />
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery.mobile-1.3.2.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/jquery.mobile.simpledialog.min.css" />
	<script type="text/javascript" src="<%=basePath%>js/jquery.mobile.simpledialog2.min.js"></script>
	
	<script type="text/javascript" src="<%=basePath%>js/dFormat.js"></script>
	
	<link rel="stylesheet" href="<%=basePath%>css/ls-theme.min.css" type="text/css"/>
	<link rel="stylesheet" href="<%=basePath%>css/rhinocort.css" type="text/css"/>
	<script type="text/javascript" src="<%=basePath%>js/rhinocort.js?version=1"></script>
	<script type="text/javascript">
	$(document).ready(function(){
	    var today = new Date();
	    var y=today.getFullYear()+"-";
        var month=today.getMonth()+1+"-";
        var td=today.getDate();
        if($("#currentDate")){
        	$("#currentDate").text(y+month+td);
        }
        
        if( $("input[type='number']") ){
        	$("input[type='number']").on("click",function(event){
        		$(this).parent().removeClass("ls-error");
        	});
        }
        
        if( $('.submit_btn') ){
			$('.submit_btn').attr("onclick","submitForm()");
		}
	});
	$.mobile.hidePageLoadingMsg();
	</script>
	<style type="text/css">
	div.ui-loader{
	   top:0;
	}
	</style>
</head>