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
			user_id : $('input[name="userId"]').attr('value'),
    	}

    	console.log(formData)

		var token =  $('input[name="_csrf"]').attr('value')
		var username = $('input[name="username"]').attr('value')
    	
    	// DO POST
    	$.ajax({
			type : "POST",
			contentType : "application/json",
			url : window.location + "api/customer/save",
			data : JSON.stringify(formData),
			dataType : 'json',
			headers: {
				'X-CSRF-Token': token
			},
			success : function(result) {
				$('#getResultDiv .list-group').append(
					'<li class="list-group-item">'
					// + result.message.author.username
					+ username
					+ ': '
					+ formData.text
					// + '<br>'
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
			url: window.location + "api/customer/response?chatId=" + $('input[name="userId"]').attr('value'),
			success: function (result) {
				console.log(result.data)
				if (result.status === "success") {
					$('#getResultDiv .list-group').append(
						'<li class="list-group-item">'
						+ '<b>Breed Bot</b>'
						+ ' : '
						+ '<img class="card-img-top" src="'+ result.message +'"/>'
						+ '<br>'
						+ '</li>')
					console.log("Success: ", result);
					document.getElementById("scroll").scrollTo(0,document.getElementById("scroll").scrollHeight)
				} else {
					$("#getResultDiv").html("<strong>Error</strong>");
					console.log("Fail: ", result);
				}
			},
			error: function (e) {
				$("#getResultDiv").html("<strong>Error</strong>");
				console.log("ERROR: ", e);
			},
		});

	}
})

//
// $.ajax({
// 	url: '<url-адрес>',
// 	type: 'post',
// 	data: '<отправляемые_данные>', // можно строкой, а можно, например, так: $('input[type="text"], input[type="radio"]:checked, input[type="checkbox"]:checked, select, textarea')
// 	dataType: 'json',
// 	beforeSend: function() {
// 		$('#sendajax').button('loading');
// 	},
// 	complete: function() {
// 		$('#sendajax').button('reset');
// 	},
// 	success: function(json) {
// 		// какие-то действия с полученными данными
// 	},
// 	error: function(xhr, ajaxOptions, thrownError) {
// 		alert(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
// 	}
// });