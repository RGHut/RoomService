function logout() {
    localStorage.removeItem("jwtToken");
    window.location.href = "/src/main/resources/static/index.html";
}

if (localStorage.getItem("jwtToken")) {
    const token = localStorage.getItem("jwtToken")

   
    $('#logoutButton').click(logout);
} else {
    window.location.href = "/src/main/resources/static/index.html";
}