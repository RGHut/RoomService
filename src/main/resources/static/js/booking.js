function getBooking (){
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
            // Do something with the booking data, e.g. create calendar events
            localStorage.setItem("Bookings", data);
          },
          error: function (jqXHR, textStatus, errorThrown) {
            console.error('Error:', textStatus, errorThrown);
          }
        });
}