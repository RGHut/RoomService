$(document).ready(function () {
    $("#register-form").submit(function (e) {
        e.preventDefault();

        var firstName = $("firstName").val();
        var lastName = $("lastName").val();
        var email = $("#email").val();
        var password = $("#password").val();
        var company = $("company").val();

        $.ajax({
            url: "http://localhost:8080/test/authenticate",
            type: "POST",
            headers: {
                'Content-Type': 'application/json'
              },
            data: JSON.stringify({
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password,
                company: company
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