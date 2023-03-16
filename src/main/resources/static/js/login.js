$(document).ready(function () {
    $("#login-form").submit(function (e) {
        e.preventDefault();

        var email = $("#email").val();
        var password = $("#password").val();

        $.ajax({
            url: "http://localhost:8080/test/authenticate",
            type: "POST",
            headers: {
                'Content-Type': 'application/json'
              },
            data: JSON.stringify({
                email: email,
                password: password,
                }),
            success: function (data) {
                var token = data.token;
                

                // Store the token in local storage
                localStorage.setItem("jwtToken", token);
                window.location.href = "../static/home.html";
            },
            error: function (xhr, status, error) {
                
                console.log("Error: " + xhr.responseJSON.error );
            }
        });
    });
});