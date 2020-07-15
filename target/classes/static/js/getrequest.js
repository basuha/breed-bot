
$( document ).ready(function() {
    //
    // $("#getAllCustomerId").post(function (event) {
    //     event.preventDefault();
        ajaxGet();
    // });

    function isJsonString(message) {
        try {
            JSON.parse(message);
        } catch (e) {
            return false;
        }
        return true;
    }
    var username = $('input[name="username"]').attr('value')
    function ajaxGet() {

        $.ajax({
            type: "GET",
            url: window.location + "api/customer?chatId=" + $('input[name="userId"]').attr('value'),
                // + JSON.stringify(document.currentUserId),
            success: function (result) {
                if (result.status === "Done") {
                    $('#getResultDiv ul').empty();
                    $.each(result.message, function (i, m) {
                        $('#getResultDiv .list-group').append(
                            '<li class="list-group-item">'
                            + username
                                + " : "
                            + m.text
                            // + ': ' )
                            // .append(
                            //     isJsonString(m.text)
                            //         ? '<img class="card-img-top" src="' + m.text + '</img>'
                            //         : m.text
                            // ).append(
                            // + '<br>'
                            + '</li>')
                        document.getElementById("scroll").scrollTo(0,document.getElementById("scroll").scrollHeight)
                    });
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