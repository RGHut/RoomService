$(document).ready(function () {
    $("#email-form").submit(function (e) {
        e.preventDefault();

        var email = $("#email").val();
        var password = $("#password").val();

        $.ajax({
            url: "http://localhost:8080/test/authenticate",
            type: "POST",
            data: {
                email: email,
                password: password
            },
            success: function (data) {
                var token = data.token;
                

                // Store the token in local storage
                localStorage.setItem("jwtToken", token);
                window.location.href = "/src/main/resources/static/home.html";
            },
            error: function (xhr, status, error) {
                console.log("Error: " + error);
            }
        });
    });
});