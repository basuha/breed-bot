$( document ).ready(function() {

    // // GET REQUEST
    // $("#getAllCustomerId").(function (event) {
    //     event.preventDefault();
        ajaxGet();
    // });

    function ajaxGet() {
        $.ajax({
            type: "GET",
            url: window.location + "api/customer/all",
            success: function (result) {
                if (result.status === "Done") {
                    $('#getResultDiv ul').empty();
                    $.each(result.data, function (i, message) {
                        $('#getResultDiv .list-group').append(
                            '<li class="list-group-item list-group-item-warning">'
                            + message.author.username
                            + ': '
                            + message.text
                            + '<br>'
                            + '</li>')
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