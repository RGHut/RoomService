function logout() {
    localStorage.removeItem("jwtToken");
    window.location.href = "../static/index.html";
}

if (localStorage.getItem("jwtToken")) {
    $('#logoutButton').click(logout);
} else {
    window.location.href = "../static/index.html";
}