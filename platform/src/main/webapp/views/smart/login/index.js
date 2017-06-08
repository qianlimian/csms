
window.onload = function(){
	document.getElementById("account").focus();
	var pathParam = generatePathParam();
	if(pathParam.hasOwnProperty("error")){//遇到登陆错误
		$("#errorMsg").fadeIn("slow");
	}
	if(pathParam.hasOwnProperty("logout")){
		$("#logoutMsg").fadeIn("slow");
	}
	
	
}
//将当前页面的页面参数放到js对象中
function generatePathParam(){
	var url = window.location.search; //获取url中"?"符后的字串
	var param = new Object();
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for(var i = 0; i < strs.length; i ++) {
			param[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
		}
	}
	return param;
}
