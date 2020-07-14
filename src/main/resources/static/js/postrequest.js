$( document ).ready(function() {
	
	// SUBMIT FORM
    $("#customerForm").submit(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		ajaxGet();
		ajaxPost();
		ajaxGet();
	});
    
    
    function ajaxPost(){
    	
    	// PREPARE FORM DATA
    	var formData = {
    		message : $("#message").val(),
    	}
    	
    	// DO POST
    	$.ajax({
			type : "POST",
			contentType : "application/json",
			url : window.location + "api/customer/save",
			data : JSON.stringify(formData),
			dataType : 'json',
			success : function(result) {
				if(result.status == "Done"){
					$("#postResultDiv").html("<h1>" + result.data.username + ": " + result.data.message + "</h1>");
				}else{
					$("#postResultDiv").html("<strong>Error</strong>");
				}
				console.log(result);
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
    	
    	// Reset FormData after Posting
    	resetData();
    }

	function ajaxGet(){
		$.ajax({
			type : "GET",
			url : window.location + "api/customer/all",
			success: function(result){
				if(result.status === "Done"){
					$('#getResultDiv ul').empty();
					$.each(result.data, function(i, customer){
						$('#getResultDiv .list-group').append(
							"<b>" + customer.username + "</b>"
							+ ": " + customer.message + "<br>")
					});
					console.log("Success: ", result);
				}else{
					$("#getResultDiv").html("<strong>Error</strong>");
					console.log("Fail: ", result);
				}
			},
			error: function(e) {
				$("#getResultDiv").html("<strong>Error</strong>");
				console.log("ERROR: ", e);
			}
		});
	}
    
    function resetData(){
    	$("#message").val("");
    }
})