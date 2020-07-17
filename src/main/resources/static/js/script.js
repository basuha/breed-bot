var username
var botName
var chatId

function scrollDown() {
    document.getElementById("scroll").scrollTo(0,document.getElementById("scroll").scrollHeight)
}

function addMessageToChat(m) {
    if (m != null) {
        if (m.is_bot_message === true) {
            if (m.type === "image") {
                $('#getResultDiv .list-group').append(
                    '<li class="list-group-item bg-warning mx-auto mt-1 mb-1">'
                    + botName
                    + ' : '
                    + m.text
                    + '</br>'
                    + '<img class="card-img-top" style="max-width: 100ex" src="' + JSON.parse(m.data).message + '"/>'
                    + '</li>')
            } else if (m.type === "list") {
                $('#getResultDiv .list-group').append(
                    '<li class="list-group-item bg-warning mx-auto mt-1 mb-1">'
                    + botName
                    + ' : '
                    + m.text
                    + '</li>'
                    + buildBreedList(m.data))
            } else {
                $('#getResultDiv .list-group').append(
                    '<li class="list-group-item bg-warning mx-auto mt-1 mb-1">'
                    + botName
                    + ' : '
                    + m.text)
            }
        } else {
            $('#getResultDiv .list-group').append(
                '<li class="list-group-item bg-light mx-auto mt-1 mb-1">'
                + username
                + " : "
                + m.text
                + '</li>')
        }
    }
    scrollDown()
    function buildBreedList(data) {
        var html = [];
        html.push('<li class="list-group-item bg-warning mx-auto mt-1 mb-1">')
        html.push('<select name="breeds" id="breeds">')
        $.each(JSON.parse(data), function (i, r) {
            html.push(
                '<option value="'
                + r
                + '">'
                + r
                + '</option>'
            )
        })
        html.push('</select>')
        html.push('</li>')
        return html.join("");
    }
}

$(document).ready(function() {
    scrollDown()
    username = $('input[name="username"]').attr('value')
    botName = '<b>Breed Bot</b>'
    chatId = $('input[name="userId"]').attr('value')
    ajaxGet();
    function ajaxGet() {
        $.ajax({
            type: "GET",
            url: window.location + "breed-bot?chatId=" + chatId,
            success: function (result) {
                $('#getResultDiv ul').empty();
                $.each(result, function (i, m) {
                    addMessageToChat(m)
                });
                scrollDown()
                console.log("Success: ", result);
            },
            error: function (e) {
                $("#getResultDiv").html("<strong>Error</strong>");
                console.log("ERROR: ", e);
            }
        });
    }
})


$(document).ready(function() {
    $("#messageForm").submit(function(event) {
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
            url : window.location + "breed-bot/save",
            data : JSON.stringify(formData),
            dataType : 'json',
            headers: {
                'X-CSRF-Token': token
            },
            success : function(result) {
                $('#getResultDiv .list-group').append(
                    '<li class="list-group-item bg-light mx-auto mt-1 mb-1">'
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
            url: window.location + "breed-bot/response?chatId=" + $('input[name="userId"]').attr('value'),
            success: function (result) {
                $.each(result, function (i, r) {
                    addMessageToChat(r)
                    console.log("Success: ", r);
                })
                scrollDown()
            },
            error: function (e) {
                $("#getResultDiv").html("<strong>Error</strong>");
                console.log("ERROR: ", e);
            },
        });
    }
})