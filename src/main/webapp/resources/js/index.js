$(document).ready(function() {
	bindLanguageSelectHandler();
	
	$('#backPortal2sp').click(function() {
		var xhr = new XMLHttpRequest();
		xhr.open("POST","call??solutionportal=", false);
		xhr.send();		
	});		
});