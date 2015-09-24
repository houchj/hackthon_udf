var Const = {
	passCheck: "yes",
	notPassCheck: "no",
};

window.FieldErrorMessage = {
	ClassAttr: "errorInfo",

	Show: function(element, errId) {
		var errorMsg = errId;
		try {
			if ($("#" + errId).length > 0) {
				errorMsg = $("#" + errId).text();
			}
		} catch (err) {

		}
		var selfDiv = element;
		var nextDiv = element.parent().next();
		var errMsgDiv = nextDiv;
		var errMsgArrow = $("<span>").addClass("errorInfoArrow");
		if (nextDiv && nextDiv.hasClass(this.ClassAttr)) {
			nextDiv.show();
		} else {
			errMsgDiv = $("<div>").addClass(this.ClassAttr);
		}
		errMsgDiv.text(errorMsg).append(errMsgArrow).insertAfter(element.parent());
		selfDiv.addClass("errorStatus");
	},

	HideElement: function(element) {
		element.removeClass("errorStatus");
		var nextDiv = element.parent().next();
		if (nextDiv && nextDiv.hasClass(this.ClassAttr)) {
			nextDiv.hide();
		}
	},
};

function changeLanguage(event) {
	var region = event.target.getAttribute("region");
	if (region && region[0] == '/') {
		region = region.substr(1);
	}

	var pathArray = window.location.href.split('/');
	if (pathArray.length > 4) {
		if (/^(zh|en)$/i.test(pathArray[4])) {
			pathArray[4] = region;
		} else {
			pathArray.splice(4, 0, region);
		}
	} else {
		pathArray.push(region);
	}
	var href = pathArray.join('/');
	window.location.href = href;
	return;
}

function bindLanguageSelectHandler() {}


function GetPasswordRule() {
	if (!window.sap) {
		window.sap = {}
	}

	if (window.sap.passwordRule) {
		return window.passwordRule;
	}

	var dtd = $.Deferred();
	$.ajax({
		type: "GET",
		url: window.context_root + "/rest/passwordPolicy",
		dataType: "json",
		cache: false,
	})
		.done(function(data) {
			window.sap.passwordRule = data;
			dtd.resolve(window.sap.passwordRule);
		})
		.fail(function(jqXHR, textStatus, errorThrown) {
			console.log("error");
			dtd.reject();
		});

	return dtd.promise();
}

function GetPasswordRuleUI() {
	$.when(GetPasswordRule()).done(function(data) {
		var $passwordRule = $("#passwordRule");
		var ruleText;
		var phrase1 = $passwordRule.attr("passwordRule1").replace("{1}", data.minimumCharacters).replace("{2}", data.maximumCharacters);
		var phrase2 = $passwordRule.attr("passwordRule2");
		var phrase2_1 = "";
		var phrase2_2 = "";
		var phrase2_3 = "";
		var phrase2_4 = "";
		var phrase3 = $passwordRule.attr("passwordRule3");
		var condictionNum = 0;
		if (data.minimumUpcaseCharacters > 0) {
			phrase2_1 = data.minimumUpcaseCharacters + " " + $passwordRule.attr("passwordRule2_1");
			condictionNum++;
		}
		if (data.minimumLowcaseCharacters > 0) {
			phrase2_2 = data.minimumLowcaseCharacters + " " + $passwordRule.attr("passwordRule2_2");
			condictionNum++;
		}
		if (data.minimumDigits > 0) {
			phrase2_3 = data.minimumDigits + " " + $passwordRule.attr("passwordRule2_3");
			condictionNum++;
		}
		if (data.minimumNonAlphaCharacters > 0) {
			phrase2_4 = data.minimumNonAlphaCharacters + " " + $passwordRule.attr("passwordRule2_4");
			condictionNum++;
		}

		if (condictionNum > 0) {
			ruleText = phrase1 + "<br>" + phrase3 + "<br>" + phrase2 + phrase2_1 + phrase2_2 + phrase2_3 + phrase2_4;
			ruleText = ruleText.substr(0, ruleText.length - 1) + ";";
		} else {
			ruleText = phrase1 + "<br>" + phrase3;
		}

		$passwordRule.html(ruleText);
	});
}

if (navigator.userAgent.toLowerCase().indexOf('ipad') > 0 && navigator.userAgent.indexOf('b1od') < 0) {
	window.__platform = 'ipadsafari';
	window.__deviceType = 'pad';

} else if (navigator.userAgent.toLowerCase().indexOf('ipad') > 0 && navigator.userAgent.indexOf('b1od') > 0) {
	window.__platform = 'ipadshell';
	window.__deviceType = 'pad';

} else if (navigator.userAgent.toLowerCase().indexOf('iphone') > 0 && navigator.userAgent.indexOf('b1od') < 0) {
	window.__platform = 'iphone';
	window.__deviceType = 'phone';

} else if (navigator.userAgent.toLowerCase().indexOf('iphone') > 0 && navigator.userAgent.indexOf('b1od') > 0) {
	window.__platform = 'iphoneshell';
	window.__deviceType = 'phone';

} else if (navigator.userAgent.toLowerCase().indexOf('android') > 0 && navigator.userAgent.indexOf('b1od') < 0) {
	window.__platform = 'android';
	window.__deviceType = 'phone';

} else if (navigator.userAgent.toLowerCase().indexOf('androidphone') > 0 && navigator.userAgent.indexOf('b1od') > 0) {
	window.__platform = 'androidphoneshell';
	window.__deviceType = 'phone';

} else if (navigator.userAgent.toLowerCase().indexOf('ipad') < 0 && navigator.userAgent.toLowerCase().indexOf('android') < 0) {
	window.__platform = 'desktop';
	window.__deviceType = 'desktop';

};


function HandlePlaceholder() {
	//support placeholder in IE
	if ('placeholder' in document.createElement('input')) {
		return;
	}

	var targetObjs = $("input[placeholder]");
	targetObjs.after(function(index) {
		var target = $(this);
		var placeholderText = target.attr("placeholder");
		var style = {
			position: "absolute",
			width: "auto",
			height: "0px",
			top: "1px",
			left: "7px",
			color: "#aaa",
		};

		var retLabel = $("<label>").text(placeholderText).addClass("placeholder").css(style);

		retLabel.click(function() {
			target.focus();
		});

		return retLabel;
	});

	targetObjs.keyup(function(event) {
		var target = $(this);
		if (target.val().trim().length == 0) {
			target.siblings("label.placeholder").show();
		} else {
			target.siblings("label.placeholder").hide();
		}
	});

};

function ShowLoadingUI(show) {
	var divLoading = $("div#_preloader_container");
	if (divLoading.length == 0) {
		divLoading = $('<div id="_preloader_container">  </div>');
		var icon = $('<div class= "loading"></div>');
		divLoading.append(icon);
		$('body').append(divLoading);

		var bodyHeight = $('html').height() - 35;
		icon.css('top', bodyHeight / 2);
	}

	if (show) {
		divLoading.show();
	} else {
		divLoading.hide();
	}
}

$(document).ready(function() {
	if (window.__deviceType == 'pad') {
		$("body").find(":not(.pad)").addClass("pad");
		$("body").addClass("pad");

		$(".cssPage").addClass("pad");
	} else if (window.__deviceType == 'phone') {
		$("body").find(":not(.phone)").addClass("phone");
		$("body").addClass("phone");

	} else if (window.__deviceType == 'desktop') {
		$("body").find(":not(.desktop)").addClass("desktop");
		$("body").addClass("desktop");
	}

	$(".cssContext, .cssPage").show();
	$(".jsBackhome").click(function() {
		location.href = window.context_root + "/";
	});

	HandlePlaceholder();

});

if (isMobilePlatform()) {
	var pathname = window.location.pathname;
	if (pathname.indexOf('downloadapp.html') < 0) {
		var targetUrl = location.href;

		if (location.href.indexOf('/zh') < 0 && location.href.indexOf('/en') < 0) {
			var curlocale = window.navigator.language.substr(0, 2);
			if (!curlocale || (curlocale != "zh" && curlocale != "en")) {
				curlocale = "zh";
			}

			var oldToReplace = location.href.substr(0, location.href.indexOf(window.context_root) + window.context_root.length);
			var targetUrl = targetUrl.replace(oldToReplace, oldToReplace + "/" + curlocale);
		}

		//consider port
		if (location.host.search(":") < 0) {
			//add default port
			var port = 443; //https
			if (location.protocol.search("https") < 0) {
				port = 80;
			}
			targetUrl = targetUrl.replace(location.host, location.host + ":" + port);
		}

		var newPath = pathname.substring(0, pathname.indexOf('/', 1) + 1) + curlocale + "/downloadapp.html?target=" + encodeURI(targetUrl);
		location.href = location.origin + newPath;
	}
} else if (window.__deviceType == 'desktop' && notSupportBrowser()) {
	//forbidden unsupport low version browser to visit			
	var pathname = window.location.pathname;
	var r = pathname.match(/\/(zh|en)/);
	var language = "";
	if (r) {
		language = r[1] + '/';
	}

	if (!window.isPadBrowserCheck) {
		var query = '?newtarget=' + encodeURI(location.href);
		var idx = location.href.indexOf(window.context_root);
		location.href = location.href.substr(0, idx) + window.context_root + '/' + language + 'padBrowserCheck.html' + query;
	}
}

function notSupportBrowser() {
	return !isSafari() && !isChrome() && !isFirefox() && !isIE10() && !isIE11();
}

function isSafari() {
	return navigator.userAgent.indexOf('Safari') > 0 && navigator.userAgent.indexOf('Chrome') < 0;
}

function isChrome() {
	return navigator.userAgent.indexOf('Chrome') > 0 || navigator.userAgent.indexOf('Chromium') > 0;
}

function isFirefox() {
	return navigator.userAgent.indexOf('Firefox') > 0 && navigator.userAgent.indexOf('Seamonkey') < 0;
}

function isMobilePlatform() {
	return window.__platform == 'ipadsafari' || window.__platform == 'iphone' || window.__platform == 'android';
}

function isIE10() {
	return navigator.userAgent.indexOf('Trident/6.') > 0 && navigator.userAgent.indexOf('MSIE') > 0;
}

function isIE11() {
	return navigator.userAgent.indexOf('Trident/7.') > 0 && navigator.userAgent.indexOf('rv:11.') > 0;
}


$(".cssContext, .cssPage").hide();

//for center message related cssContext
function CenterCssContext() {
	function innnerFunc() {
		var newpaddingtop = ($("body").height() - $(".cssContext").height()) / 2;
		$(".cssContext").css('padding-top', newpaddingtop + 'px');
	}
	$(window).resize(innnerFunc);
	innnerFunc();
}