$(document).ready(function () {
    $("#email-form").submit(function (e) {
        e.preventDefault();

        var email = $("#email").val();
        var password = $("#password").val();

        $.ajax({
            url: "http://localhost:8080/test/authenticate",
            type: "POST",
            xhrFields: {
                withCredentials: true
              },
            headers: {
                'Content-Type': 'application/json'
              },
            credentials: 'include',
            data: JSON.stringify({
                "email": email,
                "password": password
            }),
            success: function(data, textStatus, xhr) {
               
                    window.location.href = "../static/home.html";
               
            },
            error: function (xhr, status, error) {
                console.log("Error: " + error);
            }
        });
    });
});

