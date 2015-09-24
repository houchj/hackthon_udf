$(document).ready(
		function() {
			
			$(".jsSubmit").bind("click keypress", function(event) {
				ConfirmChangeEmail();						
			});		
			
			function ConfirmChangeEmail() {				
				password = $("#password").val().trim();				
				if (password.length < 1) {					
					return;
				}
				
				encodedParam = window.location.search.substring(9,location.search.length);
				var paramJson = JSON.stringify({"password":password, "encodedParam":encodedParam});
				jQuery.ajax({
		            type : "POST",  
		            url : window.context_root + "/rest/resetEmailConfirm",
		            data : paramJson,
		            dataType : "json",
		            contentType: "application/json",		            
		        }).done(function(data){
	            	if (data.success) {	            		
	            		window.location.href = "successAction.html?page=resetEmail";
	            	} else {
	            		//alert("reset Email bad");        
	            		FieldErrorMessage.Show($("#password"), "incorrectPassword");
	            	}
	            }).fail(function(jqXHR, textStatus) {
	            	  alert( "Request failed: " + textStatus );	            	 
	            });
			};
			
		});
