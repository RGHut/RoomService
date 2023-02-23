function logout() {
    localStorage.removeItem("jwtToken");
    window.location.href = "/src/main/resources/static/index.html";
}

if (localStorage.getItem("jwtToken")) {
    const token = localStorage.getItem("jwtToken")

    $.ajax({
        url: "http://localhost:8080/bookings",
        type: "GET",
        dataType: 'text',

        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        },
        success: function (data) {
            console.log('Response data:', data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error('Error:', textStatus, errorThrown);
        }
    });

    $('#logoutButton').click(logout);
} else {
    window.location.href = "/src/main/resources/static/index.html";
}