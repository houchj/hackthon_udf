$(document).ready(function() {
	
	
	
	function changeLanguage(event){
		var index=window.location.href.indexOf("?page=");
		var targetUrl=window.location.href.substr(index+6);
		
		var region = event.target.getAttribute("region") || "";
		var lastIndex = targetUrl.lastIndexOf("/"),
		headStr = targetUrl.substring(0, lastIndex),
		endStr = targetUrl.substring(lastIndex, targetUrl.length);
		
		var urlregx = "^(.)*/([A-Za-z]){2}/(.)*$";
		if (targetUrl.match(urlregx)) {
			var beforeIndex = targetUrl.search("/([A-Za-z]){2}/");	
			headStr = targetUrl.substring(0, beforeIndex);
		}
		window.location.href = headStr + region + endStr;
	}
	
	
	$("#webPageRegion a").click(function (event) {
		changeLanguage(event);
	});	
	
	$("#phonePageRegion li").click(function (event){
		changeLanguage(event);
	});
});