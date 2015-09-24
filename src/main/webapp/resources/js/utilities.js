var RESOURCEPATH = "../../resources/";

/*extension for String*/
String.prototype.format = function(i, safe, arg) {
  function format() {
    var str = this, len = arguments.length+1;
 
    // For each {0} {1} {n...} replace with the argument in that position.  If 
    // the argument is an object or an array it will be stringified to JSON.
    for (i=0; i < len; arg = arguments[i++]) {
      safe = typeof arg === 'object' ? JSON.stringify(arg) : arg;
      str = str.replace(RegExp('\\{'+(i-1)+'\\}', 'g'), safe);
    }
    return str;
  }
 
  // Save a reference of what may already exist under the property native.  
  // Allows for doing something like: if("".format.native) { /* use native */ }
  format.native = String.prototype.format;
 
  // Replace the prototype property
  return format;
 
}();

/*
 * getLanguage, setLanguage, BindTextData are working together
 */
function getLanguage() {
	var locale = window.localStorage.getItem('locale');
	if (!locale) {
		locale = navigator.language || navigator.userLanguage;
		window.localStorage.setItem('locale', locale);
	}
	
	return locale.substr(0, 2);
}

function setLanguage(locale) {
	if (locale == undefined) {
		locale = getLanguage() 
	} else {
		window.localStorage.setItem('locale', locale);	
	}
	
	if (window.sap.languages[locale]) {
		BindTextData();
	} else {
		//Get Language json file		
		var localeURL = "{0}locale/lang-{1}.json".format(RESOURCEPATH, locale);
		//resourcePath+"locale/lang-" + locale + ".json";	
		$.getJSON(localeURL, function(data) {
			console.log('localeURL Data()');
			window.sap.languages[locale] = data;
			
			BindTextData();
		});
	}
	
	//bad code here: mix css to logic	
	$("#language-panel div.language-option").removeClass("language-selected");
	if (locale == "zh") {
		$("#language-zh").addClass("language-selected");
	} else {
		$("#language-en").addClass("language-selected");
	}
}

function GetLocaleText(key) {
	var locale = getLanguage();
	var localeObj = sap.languages[locale];
	var strArray = key.split('.');
	var searchObj = localeObj;
	try {
		for (var j = 0; j < strArray.length; j++) {
			var subkey = strArray[j];
			searchObj = searchObj[subkey];
		}
		
		return searchObj;
	} catch (err) {			
		alert("Error: locale: [{0}], key[{1}]".format(locale, key));
		alert(err);
	}	
	
	return undefined;
}

function BindTextData() {
	console.log('BindTextData()');
	function processText(key, attr) {
		key = key.trim();
		attr = attr.trim();
		var value = GetLocaleText(key);	
		 
		if (value instanceof Object) {
			alert("locale: [{0}], key[{1}]".format(locale, key));
		} else if (!value) {
			alert("Error: locale: [{0}], key[{1}]".format(locale, key));
			alert(err);
		} else {			
			if (attr == 'text') {
				contentNode.textContent = value;	
			} else {
				contentNode.setAttribute(attr, value);
			}
			
		}
	}
	
	var contentNodes = document.querySelectorAll("[localeContent]");  	
	for (var i = 0; i < contentNodes.length; i++) {
		var contentNode = contentNodes[i];
		var keys = contentNode.getAttribute("localeContent").split(';');
		for (var j = 0; j < keys.length; j++) {
			var keyAttr = keys[j].split('@');
			var key = keyAttr[0];
			var attr = 'text';
			if (keyAttr.length > 1) {
				attr = keyAttr[1];
			}
			processText(key, attr);			 
		}
	}
}


/*
 * getLanguage, setLanguage, BindTextData are working together
 */
function GetAjaxURL(resourceName) {
	return '../../rest/' + resourceName;
}


/*
 * on page load function
 */
$(document).ready(function() {
	$.ajaxSetup({"error":function(XMLHttpRequest,textStatus, errorThrown) {   
	      alert(textStatus);
	      alert(errorThrown);
	      alert(XMLHttpRequest.responseText);
	}});
	
	//Add Head Title
	var head = document.getElementsByTagName('head').item(0);
	var title = document.createElement("title");
	title.appendChild(document.createTextNode("B1Anywhere"));
	head.appendChild(title);
	
	window.sap = {
			languages: {}
	};
	
	//Choose Language
	//rules: 1. use localStorage 'locale' first 
	//       2. if no 'locale' defined, use language settings in browser
	setLanguage();
	$("#language-panel a").click(function(event){
		var locale = event.target.getAttribute("region");
		if (locale && locale[0]=='/') {
			locale = locale.substr(1);
		}
		setLanguage(locale);		
	});
	 
})