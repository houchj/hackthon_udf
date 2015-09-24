$(document).ready(function(){			 
	var LocalConst = {
		manualTrialRequestApprovalCode:-1,
		emailActivationRequiredCode:0,		
		statusSuccess:"Successful",
		statusProcessing:"Processing",
		statusFail:"Failed",
		
		statusInitSubscription: "initStart",
		statusActivating: "activating",
		statusActivateDone: "activateDone",
		
	};
	$.extend(Const, LocalConst);
	
	var URLs = {
		homeURL: function() {
				return window.context_root + "/";
		},
			
		submitURL: function() {
			return window.context_root + "/rest/subscription";
		},
		taskStatusURL: function() {			
			return window.context_root + "/rest/subscription";
		},
		autoLoginURL: function () {					
			return window.context_root + "/rest/autologin?from=subscribe";
		},
		
		captchaURL: function() {
			return window.context_root + '/rest/captcha-image?'+ Math.floor(Math.random() * 100);	
		},
		
		inputValidateURL: function() {
			return window.context_root + "/rest/InputValidation";
		},
		
		thanksBackLoginURL: function() {
			return window.context_root + "/successAction.html?page=thanksBackLogin";
		},	
		
		thanksOpenEmailURL: function() {
			return window.context_root + "/successAction.html?page=thanksOpenEmail";
		},
		
		alreadyLoginURL: function() {
			return window.context_root + "/successAction.html?page=alreadyLogin";
		},		
		
		badSubscriptionURL: function() {
			return window.context_root + "/badSubscription.html"; 
		},
		
		noAuthoritySubscriptionURL: function() {
			return window.context_root + "/successAction.html?page=noAuthority"; 
		},
		
		noReplicateSubscriptionURL: function() {
			return window.context_root + "/noRepSubscription.html"; 
		},
		
		thanksSubscriptionURL: function() {
			return window.context_root + "/successAction.html?page=thanksBackLogin";
		},	
		
	};
	
	$.ajaxSetup({
		  contentType: "application/json; charset=utf-8"
		});
	
	//extend jquery
	$.fn.bindEx = function (selfEvent, fn) {
		this.bind(selfEvent, fn);
		this.attr(selfEvent, 'on');
	};
	
	var Checker = {
		elementValEmpty: function (element) {
			return element.val().trim().length == 0;
		},
		
		//common ajaxValidate
		ajaxInputValidate: function(dataJson, errMsg) {
			var retObj = {valid:false, errMsg: "impossible in valid!"}	;	
			var dtd = $.Deferred();
			
			$.when($.post(URLs.inputValidateURL(), dataJson, "json"))
			.done(function(data) {
				if (data && data.valid) {
					retObj.valid = true;
					dtd.resolve(retObj);
            	} else {            		
            		retObj.errMsg = errMsg? errMsg: data.errorMessage;            		
            		dtd.resolve(retObj);				            		
            	}  
			})
			.fail(function(jqXHR, textStatus, errorThrown){
				//impossible				
				dtd.reject();
			});
			
			return dtd.promise();
		},		
		
		mobileValid: function(email) {			
			var retObj = {valid:false, errMsg: "mobileInvalidError"};
			var emailRegx = /^[0-9|+|*|-]+\d$/;
			if (emailRegx.test(email)) {
				retObj.valid = true;		
			}         	
			return retObj;	
		},
		
		// return defered or value
		emailValid: function(email) {			
			var retObj = {valid:false, errMsg: "emailInvalidError"}	;
			var emailRegx = /^([a-zA-Z0-9_-]|[.])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,}){1,4})$/;
			if (!emailRegx.test(email)) {
				return retObj;			
			} else {				
				var dataJson = JSON.stringify({model:"UserSubscription",field:"email",
					fieldValuePairs:[{field: "email",value: email }]});
				
				return Checker.ajaxInputValidate(dataJson);
			};	        		
		},
		
		passwordValid: function(password) {
			var firstName = $("#firstName").val();
			var lastName = $("#lastName").val();
			var email = $("#email").val();
			var password = $("#password").val();
			
			/*check whether password contain email name*/
			var emailName = email.split('@')[0];
			if (password.search(emailName) >= 0) {
				return {valid:false, errMsg: "passwordNotContainName"};
			} 
			
			var dataJson = JSON.stringify({
				model:"UserSubscription",
				field:"password",
				fieldValuePairs:[{field: "firstName",value: firstName}, {field: "lastName",value: lastName}
					,{field: "email",value: email}, {field: "password",value: password}],
			});			
			return Checker.ajaxInputValidate(dataJson);
		},
		
		captchaValid: function(captcha) {			
			var dataJson = JSON.stringify({model:"UserSubscription",field:"captcha",
				fieldValuePairs:[{field: "captcha",value: captcha }]});			
			return Checker.ajaxInputValidate(dataJson);
		},
	};
	
	var CheckerAction = {
			_cbFieldCheck: null,
			
			MarkChecked: function(element, marked) {
				var checkImg = element.next(".green-check");
				if (marked) {
					//mark as checked first 
					 element.attr("passCheck", Const.passCheck);
					 
					 if (checkImg.length == 0) {
						 checkImg = $("<span>").addClass("green-check");
						 checkImg.insertAfter(element);
					 }
					 //checkImg.show();
					 checkImg.css('display', 'inline-block');
				} else {
					element.attr("passCheck", Const.notPassCheck);
					if (checkImg.length == 0) {
						 checkImg = $("<span>").addClass("green-check");
						 checkImg.insertAfter(element);
					}
					checkImg.hide();
				}
			},
			
			HandleFieldCheck: function(element, pass, errMsg) {
				if (pass) {
					CheckerAction.MarkChecked(element, true);					
					FieldErrorMessage.HideElement(element);
				} else {
					CheckerAction.MarkChecked(element, false);
					FieldErrorMessage.Show(element, errMsg);
				}
				
				if (this._cbFieldCheck) {
					this._cbFieldCheck();
				}
			} ,
			
			SetFieldCheckCallback: function(cb) {
				this._cbFieldCheck = cb;
			},
			
			DefaultAction: function (element, checkFunc) {
				var fieldVal = element.val();
		    	return $.when(checkFunc(fieldVal))
				.done(function (data) {
					CheckerAction.HandleFieldCheck(element, data.valid, data.errMsg);
				});
			},
			
		    Email: function (event) {
		    	return CheckerAction.DefaultAction($(event.target), Checker.emailValid);
		    },
		    
		    Mobile: function (event) {
		    	return CheckerAction.DefaultAction($(event.target), Checker.mobileValid);
		    },
		    
		    
		    
		    Password: function (event) {
		    	return CheckerAction.DefaultAction($(event.target), Checker.passwordValid);
		    },
		    
		    ReenterPassword: function (event) {
		    	var password = $("#password").val();
		    	var reEnterPassword = $("#reenterPassword").val();
		    	var pass = (password == reEnterPassword);
		    	CheckerAction.HandleFieldCheck($(event.target), pass, "reenterPasswordError");
		    	return 32;
		    },
		    
		    Captcha: function (event) {
		    	return CheckerAction.DefaultAction($(event.target), Checker.captchaValid);
		    },
		    
		    ////////// CheckerAction.CheckElement
		    CheckElement: function (element) {		    	
		    	if (element.attr("passCheck") == Const.passCheck) {
					 return;
				 }
		    	
		    	//exceptional handling. bad style
		    	if ($("#reenterPassword").is( element ) && ($("#password").attr("passCheck") == Const.notPassCheck)) {
		    		//first need to check password
		    		//MarkChecked($("#reenterpassword"), false);
		    		return;
		    	}
		    	
				 //do check
				 if (Checker.elementValEmpty(element)) {	
					 //CheckerAction.HandleFieldCheck(element, false, "emptyError");
					 return;
				 }

				 if (element.attr("CheckValue") == null) {
					 //mark as checked first 
					 this.HandleFieldCheck(element, true);
				 } else {
					element.triggerHandler("CheckValue");	 
				 }
		    },			
	};
	
	function ActionIfAllPassCheck() {
		var canSubmit = true;
		
		//check whether all field is ok
		$(".validateAttr").each(function(i) {
			if ($(this).attr("passCheck") == Const.notPassCheck) {									
				canSubmit = false;
				return false;
			}	
		});
		
		if (canSubmit) {			
			$(".jsSubmit").removeClass("disabled");
		} else {			
			$(".jsSubmit").addClass("disabled");
		}			
	}
	
	(function RegisterEvent() {
		$("#mobile").bindEx("CheckValue", CheckerAction.Mobile);
		$("#email").bindEx("CheckValue", CheckerAction.Email);
		$("#password").bindEx("CheckValue", CheckerAction.Password);		
		$("#reenterPassword").bindEx("CheckValue", CheckerAction.ReenterPassword);
		$("#captcha").bindEx("CheckValue", CheckerAction.Captcha);		
		
		// handle class validateAttr		
		$('input.validateAttr').keydown(function(event) {
			//mark as not checked		
			CheckerAction.MarkChecked($(event.target), false);
			
			ActionIfAllPassCheck();
	    });
		
		$('#password').keydown(function(event) {
			//mark as not checked		
			CheckerAction.MarkChecked($("#reenterPassword"), false);			
			$("#reenterPassword").val("");
	    });
		
		$('#password').focus(function(event){
			$("#passwordRule").show();
		});
		
		$('#password').blur(function(event){
			$("#passwordRule").hide();
		});
		
		$("input.validateAttr").blur(function(event) {
			 CheckerAction.CheckElement($(event.target));
		 });
		
		$("#captchaImage, #refreshcaptcha").click( function(event) {
			$("#captchaImage").attr('src', URLs.captchaURL());
			var $captcha = $("#captcha");
			$captcha.val("");			
			CheckerAction.MarkChecked($captcha, false);					
			FieldErrorMessage.HideElement($captcha);
			ActionIfAllPassCheck();
		 });		
				
		$(".jsSubmit").bind("click keypress", function (event) {
			if (!$(this).hasClass('disabled')) { 
				submit();
			}			
		});	
		
		
	})();
	
	function ShowSubscribeForm() {		 
		$(".cssForm").show();
	       $(".cssProgress").hide();	       	       
	       $(".cssForSubmit").show();
	       $(".cssForGetStarted").hide();
	       
	    $("#passwordRule").hide();
	       
	       
		// default selected "CN" country.
		if($("#country").val() != null && $("#country").val() != ""){
			$("#country").val("CN");
		}
		
		//set default attribute
		$("input").val("");
		$(".validateAttr").attr("passCheck", Const.notPassCheck);
		
		
		//un-check 'Accept terms'
		$("#readAccept").prop("checked", false);
		
		//disable submit button at first
		//$(".jsSubmit").attr("disabled", "true");	
		
		
		//set callback 
		CheckerAction.SetFieldCheckCallback(ActionIfAllPassCheck);
		
		//special process
		$("#readAccept").change( function(event) {
			if ($("#readAccept").prop("checked")) {
				$("#readAccept").attr("passCheck", Const.passCheck);
			} else {
				$("#readAccept").attr("passCheck", Const.notPassCheck);
			}
			ActionIfAllPassCheck();
		 });
		
		$("#claim").click(function() {
			 // show term of use page
			$("#TermUI").show();
			$(".cssContext").hide();
			
			
		});		
		
		$(".jsTermUIAccept").click(function() {			 
			$("#TermUI").hide();	
			$(".cssContext").show();
			if (!$("#readAccept").prop("checked")) {
				$("#readAccept").click();
			} 
		});
		
		$(".jsTermUIDeny").click(function() {
			$("#TermUI").hide();
			$(".cssContext").show();
			if ($("#readAccept").prop("checked")) {
				$("#readAccept").click();
			}
		});
		
		
		
		ActionIfAllPassCheck();
		
		//GetPasswordRule		
		GetPasswordRuleUI();
		
		//for SAPFIRE: check email first		
		ShowSubscribeFormSection(true);
		
		$('.jsNext').click(function(){
			var email = $("#email").val();
			$.when(Checker.emailValid(email))
			.done(function(data) {
				console.log("data:"+data);
				//check whether email is OK
				if (data.valid) {					
					$('.'+FieldErrorMessage.ClassAttr).remove()
					$('#emailLabel').text(email);
					ShowSubscribeFormSection(false);	
				}
			});
			

		});
	};
	
	function ShowSubscribeFormSection (showEmailPart) {
		if (showEmailPart) {
			$('.cssFormPart1 >div').addClass('cssHidden');
			$('.jsSubmit').addClass('cssHidden');		
			$('div.jsEmail').removeClass('cssHidden');
			$('div.jsNext').removeClass('cssHidden');
		} else {
			$('.cssFormPart1 >div').removeClass('cssHidden');
			$('.jsSubmit').removeClass('cssHidden');		
			$('div.jsEmail').addClass('cssHidden');
			$('div.jsNext').addClass('cssHidden');
		}
	}
	
	
	
	function ShowProgressForm(initProgress) {
		//UI style
		$(".cssForm").hide();
	       $(".cssProgress").show();	       	       
	       $(".cssForSubmit").hide();
	       $(".cssForGetStarted").show();	
	       $(".cssProgress-statusDone").hide();
	       
	       
	       $(".jsGetStarted").addClass("disabled");
	       
	       
		function setProgressValue(percentage, time) {
			//$('#progressbar')[0].value = percentage;
			
			$('.cssMetro').css("width", percentage+"%");
			$('.cssPercentage').text(percentage+"%");
			
			if (percentage == 100) {
				//enable getStarted
				$(".jsGetStarted").removeClass("disabled");				
				$(".cssProgress-statusDone").show();
				$(".cssProgress-status").hide();
			}
		} 
		
		
		
		$(".jsGetStarted").click(function(){
		   if (!$(this).hasClass("disabled") ){
			  //show hiding UI		
			  ShowLoadingUI(true);
			  window.location.href = URLs.autoLoginURL();
		   }
		})

		setProgressValue(initProgress, 0);
		if (initProgress == 100) {
			setProgressValue(100);
			return;
		}
		
		//return;
				 
		var lastQueryAjaxDeferd = null;
		var clearIntervalFun = null;
		
		var action = function() {
			if (lastQueryAjaxDeferd && lastQueryAjaxDeferd.state() == 'lastQueryAjaxDeferd') {
				//console.log('wait for ajax return');
				return; 
			}
			
			//need force no cache!
			 
			//console.log('send ajax cnt: ', cnt)
			var done = false;
			lastQueryAjaxDeferd = $.ajax({
				  type: "GET",
				  url: URLs.taskStatusURL(),
				  dataType: "json",
				  cache:false,
				})
			.done(function(data){
				//console.log('done')
				
				if (data) {
					//console.log('data.taskStatus: ' + data.taskStatus)
					if (data.taskStatus == Const.statusSuccess) {
						clearInterval(clearIntervalFun);
						done = true;
						setProgressValue(100, 1000);						 
					} else if (data.taskStatus == Const.statusProcessing){
						setProgressValue(data.percentage, 1000);					
					} else {
						console.log("query error occur data is " + data);
						if (!done) {
							console.log("goto thanksSubscriptionURL");
							window.location.href = URLs.thanksSubscriptionURL();	
						}						
					}
				}else{
					//console.log('goto thanksSubscriptionURL')
					window.location.href = URLs.thanksSubscriptionURL();
				}
			})
			.fail(function(jqXHR, textStatus, errorThrown){
				window.location.href = URLs.thanksSubscriptionURL();
			});	
		};		
		action();
		
		clearIntervalFun = setInterval(action, 2000);		
	};
	
	
	function submit() {
		//get form fields' values
		var country = $("#country").val();
		var baseLanguage = $("html").attr('lang') == "zh" ? "15": "3";
		var companyName = $("#companyName").val();
		var firstName = $("#firstName").val();
		var lastName = $("#lastName").val();
		var mobile = $("#mobile").val();
		var email = $("#email").val();
		var password = $("#password").val();
		var captchar = $("#captcha").val();
		
		var subscriptionJson = JSON.stringify({
			"localization" : country,
			"company" : companyName,
			"firstName" : firstName,
			"lastName" : lastName,
			"email" : email,
			"password" : password,
			"defaultLanguage": baseLanguage,			
			"captcha": captchar,
			"mobile": mobile
			//"includeDemoData":includeDemoData,
		});
		
		//show hiding UI		
		ShowLoadingUI(true);
		
		var posting = $.post(URLs.submitURL(), subscriptionJson, "json");
		posting.done(function(data){
			switch(data.errCode){
				case 'NOERR':
					ShowLoadingUI(false);
					ShowProgressForm(0);
					break;
				case 'NOAUTHORITY':
					location.href = URLs.noAuthoritySubscriptionURL();
					break;
				case 'ALREADYSUBSCRIBE':
					location.href = URLs.noReplicateSubscriptionURL();
					break;
				case 'ALREADYLOGIN':
					location.href = URLs.alreadyLoginURL();
					break;
				case 'MANUAL_APPROVAL':
					location.href = URLs.thanksBackLoginURL(); 
					break;
				case 'EMAIL_CONFIRM':
					location.href = URLs.thanksOpenEmailURL();
					break;
				default:
					location.href = URLs.badSubscriptionURL();	
					break;
			}
		})
		.fail(function(jqXHR, textStatus, errorThrown){
			//    			
			ShowLoadingUI(false);
			location.href = URLs.badSubscriptionURL();
		});
	}
	
	// Initial form in left content
	function goMain() {
		var initStatus = $("#initStatus").text().trim();		
		if (initStatus == Const.statusInitSubscription) {
			ShowSubscribeForm();
				
		} else if (initStatus == Const.statusActivating) {
			var progress = $("#initProgress").text().trim();
			ShowProgressForm(progress);
		} else if (initStatus == Const.statusActivateDone) {
			ShowProgressForm(100);
		}		
	}
	
	goMain();
	
	
	
 	//ShowProgressForm(100);
});
