$(document).ready(function () {
    $("#submit-form").submit(function (e) {
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
                console.log("Received token: " + token);

                // Store the token in local storage
                localStorage.setItem("jwtToken", token);
                window.location.href = "/home.html";
            },
            error: function (xhr, status, error) {
                console.log("Error: " + error);
            }
        });
    });
});