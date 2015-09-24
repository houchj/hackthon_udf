$(document).ready(function() {
	$("#loginButton").on("click", function() {
		window.alert("hhaaa");
		var username = $("#username").val();
		var password = $("#password").val();
		var loginData = JSON.stringify({"username":username, "password":password, "rememberMe":"false"});
		login(loginData);

	});

	function login(loginData) {
		jQuery.ajax({
			type : "POST",
			url : "rest/login.do",
			data : loginData,
			dataType: 'json',
			contentType : "application/json",
			success : function(data) {
				if (data) {
					if (data.code == "Success") {
						window.alert("Success");
						return;
					} else {
						window.alert("Failed");
					}
				}
			},
			error : function(err) {
				window.alert("call service failed");
			}
		});
	}

	$("#testButton").on("click", function(){
    jQuery.ajax({
      type : "GET",
      url : "rest/test.do",
      dataType: 'json',
      contentType : "application/json",
      success : function(data) {
        if (data) {
          if (data.code == "Success") {
            window.alert("Success");
            return;
          } else {
            window.alert("Failed");
          }
        }
      },
      error : function(err) {
        window.alert("call service failed");
      }
    });
	  
	  
	  
	  
	  
	});
	
	
	
});
