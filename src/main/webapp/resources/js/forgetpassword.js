$(document).ready(function () {
	var CheckerAction = {
			cbHandleFieldCheck:null,
			
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
			
			HandleFieldCheck: function(element, pass, errMsg, notShowErrMsg) {
				if (pass) {
					CheckerAction.MarkChecked(element, true);
					FieldErrorMessage.HideElement(element);
				} else {
					CheckerAction.MarkChecked(element, false);
					if (!notShowErrMsg) {
						FieldErrorMessage.Show(element, errMsg);	
					}
				}
				
				if (this.cbHandleFieldCheck) {
					this.cbHandleFieldCheck();
				}
				
				ActionIfAllPassCheck();
			} ,
			
			resetField: function(element) {
				//no OK flag, and also no error message
				CheckerAction.MarkChecked(element, false);		
				
				if (this.cbHandleFieldCheck) {
					this.cbHandleFieldCheck();
				}
			},
	};
	
	//reset field
	$(".validateAttr").each(function(index, element){
		CheckerAction.resetField($(element)); 
	});
	
	var captchaBeingCheck = false;
	var captchaCheckPass = false;
	$("#captcha").keyup(function (event) {
		var captcha = event.target.value;		
		captchaCheckPass = false;
		if (captcha && captcha.length == 6) {
			captchaBeingCheck = true;			
			
			var captchaJson = JSON.stringify({model:"SendResetPasswordRequest",field:"captcha", fieldValuePairs: [{"field":"captcha","value":captcha}]});
			jQuery.ajax({
	            type : "POST",  
	            url : window.context_root + "/rest/InputValidation",
	            data : captchaJson,
	            dataType : "json",
	            contentType: "application/json",
	            success : function(data){
	            	captchaBeingCheck = false;
	            	
	            	if (data && data.valid) {            		
	            		CheckerAction.HandleFieldCheck($("#captcha"), true);
	            		captchaCheckPass = true;
	            	} else {
	            		captchaCheckPass = false;
	            		CheckerAction.HandleFieldCheck($("#captcha"), false, "verificationCodeIncorrectError", $("#captcha").is( ":focus" ));
	            	
	            	}  
	            }
	        });
		} else {
    		CheckerAction.HandleFieldCheck($("#captcha"), false, "verificationCodeIncorrectError", true);
		}	
	});
	
	$("#captcha").blur(function (event) {
		if (!captchaBeingCheck && !captchaCheckPass) {
			var captcha = $("#captcha")[0].value.trim();			
			if (captcha.length) {
				CheckerAction.HandleFieldCheck($("#captcha"), false, "verificationCodeIncorrectError");	
			} else {
				ActionIfAllPassCheck() 
			}
		}		
	});
	$("#captchaImage, #refreshcaptcha").click(
			function(event) {				
				$("#captchaImage").attr(
						'src',
						window.context_root + '/rest/captcha-image?'
								+ Math.floor(Math.random() * 100));				
				
				CheckerAction.resetField($("#captcha"));
				
			});
	
	function submit() {
		var emailElem = $("#email"),
			captchaElem = $("#captcha"),
			email = emailElem.val(),
			captcha = captchaElem.val();		
		
		var param = JSON.stringify({"email":email, "captcha": captcha});
		jQuery.ajax({
			type: "POST",
			url: window.context_root + "/rest/sendresetPassword",
			dataType: "json",
			contentType: "application/json",
			data: param,
			success: function (data) {
				if (data && data.success) {									
					window.location.href = "successAction.html?page=forgetpassword";
	        	} else {	        		
	        		CheckerAction.HandleFieldCheck($("#email"), false, "resetPasswordFailedError");	        		
	        	}
			},
			error: function (err) {
				
						
			}
		});
		
	}
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	function ActionIfAllPassCheck() {
		var canSubmit = true;
		
		//check whether all field is ok
		
		if ($("#captcha").attr("passCheck") != Const.passCheck) {
			canSubmit = false;
		}
		
		if ($("#email").attr("passCheck") != Const.passCheck) {
			canSubmit = false;
		}
		
		
		if (canSubmit) {			
			$(".jsSubmit").removeClass("disabled");
		} else {			
			$(".jsSubmit").addClass("disabled");
		}			
	}
	
	CheckerAction.cbHandleFieldCheck = ActionIfAllPassCheck;
	
	//disable submite button first
	ActionIfAllPassCheck();
	
	//Email		
	$('#email').keydown(function(event) {
		//mark as not checked
		CheckerAction.HandleFieldCheck($("#email"), false, "", true);
    });	
	
	var emailRegx = /^([a-zA-Z0-9_-]|[.])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,}){1,2})$/;
	$("#email").blur(function (event) {
		var email = event.target.value;
		if (!email) {			
			//CheckerAction.HandleFieldCheck($("#email"), false, "emailEmptyError");			
			return;
		}		
		
		if (!emailRegx.test(email)) {
			CheckerAction.HandleFieldCheck($("#email"), false, "emailInvalidError");			
			return;
		}
		
		CheckerAction.HandleFieldCheck($("#email"), true);
	});
	
	//SUBMITE LOGIC
	$(".jsSubmit").click(function() {
		if (!$(this).hasClass('disabled')) { 
			submit();
		}
	});
	
});
