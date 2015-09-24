$(document).ready(function() {	
			function MarkChecked(element, marked) {
				var checkImg = element.next(".green-check");
				if (marked) {
					//mark as checked first 
					 element.attr("passCheck", Const.passCheck);
					 
					 if (checkImg.length == 0) {
						 checkImg = $("<span>").addClass("green-check");
						 checkImg.insertAfter(element);
					 }
					
					 checkImg.css('display', 'inline-block');
				} else {
					element.attr("passCheck", Const.notPassCheck);
					if (checkImg.length == 0) {
						 checkImg = $("<span>").addClass("green-check");
						 checkImg.insertAfter(element);
					}
					checkImg.hide();
				}
			};
			
			
			var isPincodeChecked = false;
			var checkPassword = function (password) {
				var element = $("#password");
				var login = $("#email").text();
				var dataJson = JSON.stringify({model:"ResetPasswordRequest",field:"password",
					fieldValuePairs:[{field: "login",value: login }, {field: "password",value: password }]});
				
				jQuery.ajax({
		            type : "POST",
		            url : window.context_root + "/rest/InputValidation",
		            
		            data : dataJson,
		            dataType : "json",
		            contentType: "application/json",		            
		        }).done(function(data){
	            	if (data.valid) {
	            		MarkChecked(element, true);
	            		FieldErrorMessage.HideElement(element);
	            	} else {
	            		FieldErrorMessage.Show(element, data.errorMessage);
	            	}  
	            	ActionIfAllPassCheck();
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
				
				var retValue;
				var element = $("#reenterpassword");
				if (reenterPassword != password) {
					FieldErrorMessage.Show(element, "passwordNotMatchError");					
					retValue = false;
				}else{					
					MarkChecked(element, true);
					FieldErrorMessage.HideElement(element);					 
					retValue = true;
				}
				
				ActionIfAllPassCheck();
				return retValue;
			};
			$("#password").keyup(function (event) {
				var $password = $("#password")
				MarkChecked($password, false);
				FieldErrorMessage.HideElement($password);	
				
				//clear reenter password 
				var $reenterpassword =$("#reenterpassword")				
				$reenterpassword.val("");
				MarkChecked($reenterpassword, false);
				FieldErrorMessage.HideElement($reenterpassword);				
				ActionIfAllPassCheck();				
			});
			
			$("#password").blur(function (event) {				
				var password = event.target.value;
				if (password.length == 0) {
					return;
				}
				checkPassword(password);
				
			});
			
			$("#pin").keyup(function (event) {
				   var pin = $("#pin")[0].value.trim(); 	  
				   if (pin.length > 0) {
					   $(".jsNext").removeClass("disabled");
				   } else {
					   $(".jsNext").addClass("disabled");
				   }
			});
			
			$("#reenterpassword").keyup(function (event) {
				   var $reenterpassword	= $("#reenterpassword");
				   var password = $("#password").val().trim(); 	 
				   var reenterPassword = $reenterpassword.val().trim(); 	  
				   if (password.length > 0 && reenterPassword == password) {
					   checkReenterpassword(password, reenterPassword);
				   } else {
					   MarkChecked($reenterpassword, false);
					   FieldErrorMessage.HideElement($reenterpassword);				
					   ActionIfAllPassCheck();		
				   }
			});
			
			$("#reenterpassword").blur(function (event) {				
				var	reenterPassword = event.target.value;
				if (reenterPassword.length == 0) {
					return;
				}
				
				var password = $("#password").val();				
				checkReenterpassword(password, reenterPassword);
			});
			
			$("#pin").keyup(function (event) {
			   var pin = $("#pin")[0].value.trim(); 	  
			   if (pin.length > 0) {
				   $(".jsNext").removeClass("disabled");
			   } else {
				   $(".jsNext").addClass("disabled");
			   }
			});
			//make jsNext disabled first
			$(".jsNext").addClass("disabled");
			
			
			function submit() {
				var loginId = $("#email").text(),
				password = $("#password").val(),
				encodedParam = $("#encodedParam").text(),
				pin = window.location.hash.substr(1),
				reenterPassword = $("#reenterpassword").val();
				if (checkReenterpassword(password, reenterPassword)) {
					
					var paramJson = JSON.stringify({"login":loginId, "password":password, "encodedParam":encodedParam, "pin":pin});
					jQuery.ajax({
			            type : "POST",
			            url : window.context_root + "/rest/resetPasswordConfirm",
			            data : paramJson,
			            dataType : "json",
			            contentType: "application/json",
			            success : function(data){
			            	if (data && data.success) {			            					            		
			            		window.location.href = "successAction.html?page=resetPassWord";
			            	} else {
			            		var errorMsg = data && data.message ? data.message : "reset password failed";			            		
			            		FieldErrorMessage.Show($("#password"), errorMsg);
			            	}
			            	 
			            },
			            error: function( jqXHR, textStatus, errorThrown){	
			            	var errorMsg = jqXHR && jqXHR.responseText ? jqXHR.responseText : "reset password failed";
		            		
			            }
					
			        });	
					 
				}
			}
			
			
			
			function checkPinCode() {
				FieldErrorMessage.HideElement($("#pin"));			
				
				var encodedParam = $("#encodedParam").text();
				var pin = $("#pin").val();
				var dataJson = JSON.stringify({model:"ResetPasswordRequest",field:"pin",
					fieldValuePairs:[{field: "pin",value: pin }, {field: "encodedParam",value: encodedParam }]});
				
				jQuery.ajax({
		            type : "POST",
		            url : window.context_root + "/rest/InputValidation",
		            
		            data : dataJson,
		            dataType : "json",
		            contentType: "application/json",		            
		        }).done(function(data){
	            	if (data.valid) {	           
	            		window.location.hash=pin;
	            		UpdateUI('checkPassword');	
	            		isPincodeChecked = true;
	            	} else {      
	            		FieldErrorMessage.Show($("#pin"), data.errorMessage);
	            	}  
	            	
	            }).fail(function(jqXHR, textStatus) {	            	
	            	if (jqXHR.status == 403) {
	            		alert(jqXHR.responseText);
	            	}
	            });
			};
			
			function ActionIfAllPassCheck()
			{
				var canSubmit = true;
				
				//check whether all field is ok
				
				if ($("#password").attr("passCheck") != Const.passCheck) {
					canSubmit = false;
				}
				
				if ($("#reenterpassword").attr("passCheck") != Const.passCheck) {
					canSubmit = false;
				}
				
				
				if (canSubmit) {			
					$(".jsSubmit").removeClass("disabled");
				} else {			
					$(".jsSubmit").addClass("disabled");
				}
			}
			ActionIfAllPassCheck();
			
			$(".jsNext").click(function(event) {
				checkPinCode();
			});	
			
			$(".jsSubmit").click(function(event) {
				submit();
			});	
			
			function UpdateUI(state) {
				if (state == 'checkPinCode') {					
					// hide 'passwordCheckPart' and show 'pinCheckPart'
					$(".jsCheckPasswordPart").addClass('cssHidden'); 
					$(".jsCheckPinPart").removeClass('cssHidden');					
				} else if (state == 'checkPassword') {
					// show 'passwordCheckPart' and hide 'pinCheckPart'						
					$(".jsCheckPinPart").addClass('cssHidden'); 
					$(".jsCheckPasswordPart").removeClass('cssHidden');
				}
			}
			
			var encodedParam = $("#encodedParam").text();
			if (encodedParam == "") {
				UpdateUI('checkPassword');
			} else {
				if (window.location.hash) {					
					UpdateUI('checkPassword');
				} else {
					UpdateUI('checkPinCode');
				}	
			}
			
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
