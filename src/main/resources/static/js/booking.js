const token = localStorage.getItem("jwtToken")



  function getBooking (){
    
    $.ajax({
    url: "http://localhost:8080/bookings",
    type: "GET",
    dataType: 'text',
    beforeSend: function (xhr) {
      xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    },
    success: function (data) {
      // Do something with the booking data, e.g. create calendar events
      localStorage.setItem("Bookings", data);
    },
    error: function (jqXHR, textStatus, errorThrown) {
      console.error('Error:', textStatus, errorThrown);
    }
  });
}

function makeBooking(name, timeStart, timeEnd, email, calendar) {
  $.ajax({
    url: "http://localhost:8080/makeBooking",
    type: "POST",
    beforeSend: function(xhr) {
      xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    },
    headers: {
      'Content-Type': 'application/json'
    },
    
    data: JSON.stringify({
      "room": {
        "name": name
      },
      "timeStart": timeStart,
      "timeEnd": timeEnd,
      "user": {
        "email": email
      }
    }),
    success:function (data) {
      getBooking();
    }, 
    
    error: function (xhr, status, error) {
      console.log("Error: " + error);
  }
  });
}
function deleteBooking(bookingToken, eventStart, eventEnd, calendar) {
  console.log(bookingToken)
$.ajax({
  url: "http://localhost:8080/cancelBooking",
  type: "POST",
  beforeSend: function(xhr) {
    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
  },
   
  data: {
    token : bookingToken
  },
    
      
  success:function (data) {
        
    getBooking();
  
},
  
  error: function (xhr, status, error) {
    console.log("Error: " + error);
  }
  
});
}



  
  