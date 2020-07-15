$( document ).ready(function() {
	
	// SUBMIT FORM
    $("#customerForm").submit(function(event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		ajaxPost();
	});
    
    
    function ajaxPost(){
    	// PREPARE FORM DATA
    	var formData = {
    		text : $("#message").val(),
    	}
    	
    	// DO POST
    	$.ajax({
			type : "POST",
			contentType : "application/json",
			url : window.location + "api/customer/save",
			data : JSON.stringify(formData),
			dataType : 'json',
			success : function(result) {
				$('#getResultDiv .list-group').append(
					'<li class="list-group-item list-group-item-warning">'
					// + result.message.author.username
					+ 'dummy'
					+ ': '
					+ result.message.text
					+ '<br>'
					+ '</li>')
				console.log(result);
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
    	
    	// Reset FormData after Posting
    	resetData();
    	getResponseFromBot()
    }
    
    function resetData(){
    	$("#message").val("");
    }

	function getResponseFromBot() {
		$.ajax({
			type: "GET",
			url: window.location + "api/customer/response",
			success: function (result) {
				if (result.status === "Done") {
					$('#getResultDiv .list-group').append(
						'<li class="list-group-item list-group-item-warning">'
						// + result.message.author.username
						+ ': '
						+ '<img src="'+ result.message.message.toString() +'"/>'
						+ '<br>'
						+ '</li>')
					console.log("Success: ", result);
				} else {
					$("#getResultDiv").html("<strong>Error</strong>");
					console.log("Fail: ", result);
				}
			},
			error: function (e) {
				$("#getResultDiv").html("<strong>Error</strong>");
				console.log("ERROR: ", e);
			}
		});
	}
})