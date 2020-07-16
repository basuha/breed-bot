var username = $('input[name="username"]').attr('value')
var botName = '<b>Breed Bot</b>';

function addMessageToChat(m) {
    if (m.is_bot_message === true) {
        if (m.type === "image") {
            $('#getResultDiv .list-group').append(
                '<li class="list-group-item bg-warning">'
                + '<b>Breed Bot</b>'
                + ' : '
                + m.text
                + '<img class="card-img-top" style="width: 50%; height: 50%" src="' + JSON.parse(m.data).message + '"/>'
                + '</li>')
        } else if (m.type === "list") {
            $('#getResultDiv .list-group').append(
                '<li class="list-group-item bg-warning">'
                + '<b>Breed Bot</b>'
                + ' : '
                + breedList()
                + '</li>')
        } else {
            $('#getResultDiv .list-group').append(
                '<li class="list-group-item bg-warning">'
                + '<b>Breed Bot</b>'
                + ' : '
                + m.text)
        }
    } else {
        $('#getResultDiv .list-group').append(
            '<li class="list-group-item">'
            + username
            + " : "
            + m.text
            + '</li>')
    }
}

$( document ).ready(function() {
    ajaxGet();
    function ajaxGet() {
        $.ajax({
            type: "GET",
            url: window.location + "api/customer?chatId=" + $('input[name="userId"]').attr('value'),
            success: function (result) {
                $('#getResultDiv ul').empty();
                $.each(result.message, function (i, m) {
                    addMessageToChat(m)
                });
                $('#getResultDiv .list-group').append(
                    '<li class="list-group-item bg-warning"> '
                    + botName + ' : '
                    + 'Добрый день, ' + username + '! Меня зовут Breed bot. Чем могу помочь?'
                    + '</li>')
                document.getElementById("scroll").scrollTo(0,document.getElementById("scroll").scrollHeight)
                console.log("Success: ", result);
            },
            error: function (e) {
                $("#getResultDiv").html("<strong>Error</strong>");
                console.log("ERROR: ", e);
            }
        });
    }
})


$( document ).ready(function() {
    $("#customerForm").submit(function(event) {
        event.preventDefault();
        ajaxPost();
    });

    function ajaxPost(){
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
                    + username
                    + ': '
                    + formData.text
                    + '</li>')
                console.log(result);
            },
            error : function(e) {
                alert("Error!")
                console.log("ERROR: ", e);
            }
        });
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
                console.log(result)
                $.each(result, function (i, r) {
                    addMessageToChat(r)
                    console.log("Success: ", r);
                    document.getElementById("scroll").scrollTo(0,document.getElementById("scroll").scrollHeight)
                })
            },
            error: function (e) {
                $("#getResultDiv").html("<strong>Error</strong>");
                console.log("ERROR: ", e);
            },
        });
    }
})

function breedList(){
    var output;
    $.getJSON("https://dog.ceo/api/breeds/list/all", function(result) {
        var breeds = result.message;
        firstDog = Object.keys(breeds)[0];
        $.each(breeds,function(dog,breed){
                if(breeds[dog].length>=1) {
                    for(let i = 0; i < breeds[dog].length; i++) {
                        $('#getResultDiv .list-group').append(
                            '<button type="button" class="btn btn-secondary" value="'
                            + dog
                            + '-' + breeds[dog][i]
                            + '">'
                            + ''
                            + breeds[dog][i]
                            + ' '
                            + dog
                            + '</button>');
                    }
                }
                else if(breeds[dog].length<1){
                    $('#getResultDiv .list-group').append(
                        '<l value="'
                        + dog
                        + '">'
                        + dog
                        + '</l>');
                }
            }
        );
        // $.getJSON("https://dog.ceo/api/breed/"
        // 	+ firstDog
        // 	+ "/images/random", function(result){
        // 	$(".demo-image").html("<img src='"+result.message+"'>");
        // });
    });
    return output;
}

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