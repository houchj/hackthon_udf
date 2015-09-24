$(document).ready(		
		function() {	
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
			

			var checkPassword = function (password) {
				var element = $("#password");
				var token = $("#token").text();				
				var dataJson = JSON.stringify({model:"InitialPasswordRequest",field:"password",
					fieldValuePairs:[{field: "token",value: token }, {field: "password",value: password }]});
				
				jQuery.ajax({
		            type : "POST",
		            url : window.context_root + "/rest/InputValidation",
		            
		            data : dataJson,
		            dataType : "json",
		            contentType: "application/json",		            
		        }).done(function(data){
	            	if (data.valid) {
	            		CheckerAction.MarkChecked(element, true);
	            		FieldErrorMessage.HideElement(element);
	            	} else {
	            		FieldErrorMessage.Show(element, data.errorMessage);
	            	}  
	            	
	            }).fail(function(jqXHR, textStatus) {	            	
	            	if (jqXHR.status == 403) {
	            		alert(jqXHR.responseText);
	            	}
	            });
			};
			var checkReenterpassword = function (password, reenterPassword) {
				if ($("#password").attr("passCheck") == Const.notPassCheck) {
					//first need to check password
					return;
				}				
				
				var element = $("#reenterpassword");
				if (reenterPassword != password) {
					FieldErrorMessage.Show(element, "passwordNotMatchError");					
					return false;
				}else{					
					CheckerAction.MarkChecked(element, true);
					FieldErrorMessage.HideElement(element);					
					return true;
				}
			};
			$("#password").blur(function (event) {
				var password = event.target.value;
				if (!password.length) {
					return;
				}
				checkPassword(password);
			});
			$("#reenterpassword").blur(function (event) {				
				var password = $("#password").val(),
					reenterPassword = event.target.value;
				checkReenterpassword(password, reenterPassword);
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
			            		/*if (!$("#captcha").is( ":focus" )) {
			            			
			            		}*/
			            	}  
			            }
			        });
				} else {
		    		CheckerAction.HandleFieldCheck($("#captcha"), false, "verificationCodeIncorrectError", true);
				}	
			});
			
			$("#captcha").blur(function (event) {
				if (!captchaBeingCheck && !captchaCheckPass && $("#captcha").val() != "") {
					CheckerAction.HandleFieldCheck($("#captcha"), false, "verificationCodeIncorrectError");
				}		
			});						
			
			$("#captchaImage, #refreshcaptcha").click(function(event) {				
				$("#captchaImage").attr(
						'src',
						window.context_root + '/rest/captcha-image?'
								+ Math.floor(Math.random() * 100));				
				
				CheckerAction.resetField($("#captcha"));
			 });
			
			function submit() {
				var password = $("#password").val(),
				reenterPassword = $("#reenterpassword").val(),
                    token = $("#token").text();
				var captchar = $("#captcha").val();
				
				if (checkReenterpassword(password, reenterPassword)) {					
					var paramJson = JSON.stringify({"token":token, "password":password, "captcha":captchar});
					jQuery.ajax({
			            type : "POST",
			            url : window.context_root + "/rest/initialpassword",
			            data : paramJson,
			            dataType : "json",
			            contentType: "application/json",
			            success : function(data){
			            	if (data && data.success) {			            					            		
			            		window.location.href = data.autologinUrl;
			            	} else {
			            		window.location.href =  "badInitialPasswordRequest.html";
			            	}			            	
			            },
			            error: function( jqXHR, textStatus, errorThrown){	
			            	window.location.href =  "badInitialPasswordRequest.html";
			            }
					
			        });	
					
				}
			}

            


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        	function ActionIfAllPassCheck() {
        		var canSubmit = true;
        		
        		//check whether all field is ok
        		
        		if ($("#password").attr("passCheck") != Const.passCheck) {
        			canSubmit = false;
        		}
        		
        		if ($("#reenterpassword").attr("passCheck") != Const.passCheck) {
        			canSubmit = false;
        		}        		
        		
        		if ($("#captcha").attr("passCheck") != Const.passCheck) {
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
        	
        	//SUBMITE LOGIC
        	$(".jsSubmit").click(function() {
        		if (!$(this).hasClass('disabled')) { 
        			submit();
        		}
        	});
        	
        	$("#passwordRule").hide();

        	$('#password').focus(function(event){
        		$("#passwordRule").show();
        	});

        	$('#password').blur(function(event){
        		$("#passwordRule").hide();
        	});

        	//GetPasswordRule		
        	GetPasswordRuleUI();
});

