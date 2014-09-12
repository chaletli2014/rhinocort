Number.prototype.toPercent = function(){
	return (Math.round(this * 10000)/100).toFixed(0) + '%';
}

function checkIsNotNull(obj){
	var argumentLength = arguments.length;
	var invalidCount = 0;
	
	for( var i = 0; i < argumentLength; i++ ){
		if( arguments[i] && arguments[i].val() == "" ){
			arguments[i].parent().addClass("ls-error");
			invalidCount++;
		}
	}
	if( invalidCount > 0 ){
		return false;
	}else{
		return true;
	}
}

function obj1ltobj2(id1, id2){
	if( $("#"+id1) && $("#"+id2) ){
		if( Number($("#"+id1).val()) < Number($("#"+id2).val()) ){
			showCustomrizedMessage($("#"+id1+"_label").text()+"必须大于"+$("#"+id2+"_label").text());
			$("#"+id1).parent().addClass("ls-error");
			$("#"+id2).parent().addClass("ls-error");
			return false;
		}
	}
	return true;
}

function percentValidate(){
	var argumentLength = arguments.length;
	var invalidCount = 0;
	var percentSum = 0;
	for( var i = 0; i < argumentLength; i++ ){
		if( arguments[i] ){
			if( Number(arguments[i].val()) > 100 ){
				arguments[i].parent().addClass("ls-error");
				invalidCount++;
			}else{
				percentSum = percentSum + Number(arguments[i].val());
			}
		}
	}
	var lsnum = 0;
	if($("#lsnum")){
		lsnum = $("#lsnum").val();
	}
	
	if( invalidCount > 0 ){
		showCustomrizedMessage("百分比录入数据不能大于100");
		return false;
	}else if( percentSum != 100 && lsnum != 0 ){
		showCustomrizedMessage("百分比录入数据之和必须为100");
		return false;
	}else{
		return true;
	}
}

function isDouble(obj){
	var r = /^[1-9]\d*|0$/;
	var argumentLength = arguments.length;
	var invalidCount = 0;
	
	for( var i = 0; i < argumentLength; i++ ){
		if( arguments[i] && !r.test(arguments[i].val())){
			arguments[i].parent().addClass("ls-error");
			invalidCount++;
		}
	}
	if( invalidCount > 0 ){
		showCustomrizedMessage("数值只能为正数或0");
		return false;
	}else{
		return true;
	}
}

function isInteger(obj){
	var r = /^[1-9]\d*$/;
	var argumentLength = arguments.length;
	var invalidCount = 0;
	
	for( var i = 0; i < argumentLength; i++ ){
		if( arguments[i] && !r.test(arguments[i].val()) && arguments[i].val() != 0 ){
			arguments[i].parent().addClass("ls-error");
			invalidCount++;
		}
	}
	if( invalidCount > 0 ){
		showCustomrizedMessage("数值只能为正整数或0");
		return false;
	}else{
		return true;
	}
}

function isNumber(obj){
	var reg = new RegExp("^[0-9]*$");
	var argumentLength = arguments.length;
	var invalidCount = 0;
	
	for( var i = 0; i < argumentLength; i++ ){
		if( arguments[i] && ( arguments[i].val() == '' || !reg.test(arguments[i].val()) ) ){
			arguments[i].parent().addClass("ls-error");
			invalidCount++;
		}
	}
	if( invalidCount > 0 ){
		return false;
	}else{
		return true;
	}
}

function showCustomrizedMessage(message){
	$('html').simpledialog2({
        mode: 'blank',
        headerText: '提示',
        headerClose: true,
        blankContent : 
          "<label style='text-align:center;display:block'>" + message + "</label>" + "<a rel='close' data-role='button' href='#'>关闭</a>"
      });
}

function checkMessage(message){
	if( message != '' ){
		showCustomrizedMessage(message);
	}
}

function checkUploadMessage(messageareaid){
	if( messageareaid != null && messageareaid != '' ){
		$("#"+messageareaid).css("display","block");
	}
}

function webLoginFormCheck(){
	if( $("#web_login_userName") && $("#web_login_userName").val() == '' ){
		alert('用户名不能为空');
		return;
	}
	if( $("#web_login_password") && $("#web_login_password").val() == '' ){
		alert('密码不能为空');
		return;
	}
	$("#webLoginForm").submit();
}

function showweeklypedreport(basePath,reportDate,userTel,userLevel){
    var reportFile = basePath+"weeklyReport/weeklyPEDReport-"+userLevel+"-"+userTel+"-"+reportDate+".html";
    $('html').simpledialog2({
        mode: 'blank',
        headerText: '提示',
        headerClose: true,
        blankContent : 
          "<label style='text-align:center;display:block'>" + message + "</label>" + "<a rel='close' data-role='button' href='#'>关闭</a>"
      });
}

function compareDate(d1, d2) {
	return Date.parse(d1.replace(/-/g, "/")) > Date.parse(d2.replace(/-/g, "/"));
} 

var eDropLangChange = function(){
	var selectedValue = $(this).val();

	$("#dsmSelect").children("span").each(function(){
		$(this).children().clone().replaceAll($(this));
	});

	if(selectedValue != ''){
		$("#dsmSelect").children("option[parentid!='" + selectedValue + "'][value!='']").each(function(){
			$(this).wrap("<span style='display:none'></span>");
		});
	}
};
var eDropFrameChange = function(){
	$("#rsmSelect").val($(this).children("option:selected").attr("parentid"));
};

var eDropLangBMChange = function(){
	var selectedValue = $(this).val();
	
	$("#rsmSelect").children("span").each(function(){
		$(this).children().clone().replaceAll($(this));
	});
	if(selectedValue != ''){
		$("#rsmSelect").children("option[parentid!='" + selectedValue + "'][value!='']").each(function(){
			$(this).wrap("<span style='display:none'></span>");
		});
	}
	
	$("#rsmSelect").selectmenu('enable');
	$("#rsmSelect").val('');
	$("#rsmSelect").selectmenu('refresh', true);
};
var eDropFrameBMChange = function(){
	//$("#rsdSelect").val($(this).children("option:selected").attr("parentid"));
};

function init(){
	document.addEventListener("deviceready",onDeviceReady,false);
}
function onDeviceReady() {
    //All pages at least 100% of viewport height
    var viewPortHeight = $(window).height();
    var headerHeight = $('div[data-role="header"]').height();
    var footerHeight = $('div[data-role="footer"]').height();
    var contentHeight = viewPortHeight - headerHeight - footerHeight;
    // Set all pages with class="page-content" to be at least contentHeight
    $('div[class="page-content"]').css({'min-height': contentHeight + 'px'});
 }

function showlowerRSMReport(curTR){
	
	if($(".dependRSMTR").is(":visible")){
		$(".dependRSMTR").fadeOut("slow");
	}else{
		$.mobile.showPageLoadingMsg('b','数据检索中',false);
		
		$(".dependRSMTR").remove();
		$(".dependREPTR").remove();
		
		$.ajax({ 
	        type: "get",
	        url: "lowerWeeklyReport", 
	        contentType:'application/json',
	        dataType:'json',
	        data:{
	        	lowername:$(curTR).attr("id")
	        },
	        success: function (data) {
	        	if (data && data.success == "true") {
	        		var lowerHTML = '<tr class="dependRSMTR" height="100px"><td colspan="7"><table class="mobileReport_table">';
	        		
	        		lowerHTML = lowerHTML + '<tr><td colspan="7">'+data.subTitle+'</td></tr>';
	        		
	        		lowerHTML = lowerHTML + '<tr class="mobileReport_table_subheader">' + 
	                   '<td width="40%">姓名</td>' + 
	                   '<td width="20%">上报率</td>' + 
	                   '<td width="20%">过敏性鼻炎占比</td>' + 
	                   '<td width="20%">雷诺考特占比</td>' + 
	                   '</tr>';
	        		
	                $.each(data.data, function(i, item) {
	                	if( i%2 === 0 ){
	                		lowerHTML = lowerHTML + '<tr id="'+item.level+'-'+item.name+'" class="mobileReport_table_body" onclick="showlowerREPReport(this)">';
	                	}else{
	                		lowerHTML = lowerHTML + '<tr id="'+item.level+'-'+item.name+'" class="mobileReport_table_body mobileReport_tr_even" onclick="showlowerREPReport(this)">';
	                	}
	                	lowerHTML = lowerHTML + '<td>' + item.name + '</td>' +
	                	'<td>' + item.inRate.toPercent() +'</td>';
	                	
	                	if( item.num1+item.num2+item.num3+item.num4+item.num5 == 0 ){
	                		lowerHTML = lowerHTML + '<td>0%</td>';
	                	}else{
	                		lowerHTML = lowerHTML + '<td>' + (Math.round(item.num2 / (item.num1+item.num2+item.num3+item.num4+item.num5) * 10000)/100).toFixed(0) + "%" + '</td>';
	                	}
	                	
	                	if( item.num2 == 0 ){
	                		lowerHTML = lowerHTML + '<td>0%</td>';
	                	}else{
	                		lowerHTML = lowerHTML + '<td>' + (Math.round(item.num3 / item.num2 * 10000)/100).toFixed(0) + "%" + '</td>';
	                	}
	                	lowerHTML = lowerHTML + '</tr>';
	                });
	                lowerHTML = lowerHTML + '</table></td></tr>';
	                
	                $(curTR).after(lowerHTML);
	                $(".dependRSMTR").hide();
	                $(".dependRSMTR").fadeIn("slow");
	              }
	        	$.mobile.hidePageLoadingMsg();
	         }, 
	         error: function (XMLHttpRequest, textStatus, errorThrown) { 
	        	 $.mobile.hidePageLoadingMsg();
	        	 $('html').simpledialog2({
	        	        mode: 'blank',
	        	        headerText: '提示',
	        	        headerClose: true,
	        	        blankContent : 
	        	          "<label style='text-align:center;display:block'>获取数据失败</label>" + "<a rel='close' data-role='button' href='#'>关闭</a>"
	        	 });
	         } 
		});
	}
}

function showlowerREPReport(curTR){
	if($(".dependREPTR").is(":visible")){
		$(".dependREPTR").fadeOut("slow");  //由上自下缩短
	}else{
		$.mobile.showPageLoadingMsg('b','数据检索中',false);
		
		$(".dependREPTR").remove();
		$.ajax({ 
	        type: "get",
	        url: "lowerWeeklyReport", 
	        contentType:'application/json',
	        dataType:'json',
	        data:{
	        	lowername:$(curTR).attr("id")
	        },
	        success: function (data) {
	        	if (data && data.success == "true") {
	        		var lowerHTML = '<tr class="dependREPTR"><td colspan="7"><table class="mobileReport_table">';
	        		lowerHTML = lowerHTML + '<tr><td colspan="7">'+data.subTitle+'</td></tr>';
	        		
	        		lowerHTML = lowerHTML + '<tr class="mobileReport_table_subheader">' + 
	                   '<td width="40%">姓名</td>' + 
                       '<td width="20%">上报率</td>' + 
                       '<td width="20%">过敏性鼻炎占比</td>' + 
                       '<td width="20%">雷诺考特占比</td>' + 
                       '</tr>';
	        		
	                $.each(data.data, function(i, item) {
	                	if( i%2 === 0 ){
	                		lowerHTML = lowerHTML + '<tr class="mobileReport_table_body">';
	                	}else{
	                		lowerHTML = lowerHTML + '<tr class="mobileReport_table_body mobileReport_tr_even">';
	                	}
	                	lowerHTML = lowerHTML + '<td>' + item.name + '</td>' +
	                	'<td>' + item.inRate.toPercent() +'</td>';
	                	
	                	if( item.num1+item.num2+item.num3+item.num4+item.num5 == 0 ){
	                		lowerHTML = lowerHTML + '<td>0%</td>';
	                	}else{
	                		lowerHTML = lowerHTML + '<td>' + (Math.round(item.num2 / (item.num1+item.num2+item.num3+item.num4+item.num5) * 10000)/100).toFixed(0) + "%" + '</td>';
	                	}
	                	
	                	if( item.num2 == 0 ){
	                		lowerHTML = lowerHTML + '<td>0%</td>';
	                	}else{
	                		lowerHTML = lowerHTML + '<td>' + (Math.round(item.num3 / item.num2 * 10000)/100).toFixed(0) + "%" + '</td>';
	                	}
	                	
	                	lowerHTML = lowerHTML + '</tr>';
	                });
	                lowerHTML = lowerHTML + '</table></td></tr>';
	                $(curTR).after(lowerHTML);
	                $(".dependREPTR").hide();
	                $(".dependREPTR").fadeIn("slow");
	              }
	        	$.mobile.hidePageLoadingMsg();
	         }, 
	         error: function (XMLHttpRequest, textStatus, errorThrown) {
	        	 $.mobile.hidePageLoadingMsg();
	        	 $('html').simpledialog2({
	        	        mode: 'blank',
	        	        headerText: '提示',
	        	        headerClose: true,
	        	        blankContent : 
	        	          "<label style='text-align:center;display:block'>获取数据失败</label>" + "<a rel='close' data-role='button' href='#'>关闭</a>"
	        	 });
	         } 
		});
		$(".dependREPTR").slideDown(1);
	}
}