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
       const token = data.split(' ')[2];
       var bookingToken = token;
       // add the new booking to local storage
      var bookings = JSON.parse(localStorage.getItem("Bookings")) || [];
      var newBooking = {
        id: bookingToken,
        timeStart: timeStart,
        timeEnd: timeEnd
      };
      bookings.push(newBooking);
      localStorage.setItem("Bookings", JSON.stringify(bookings));

      // add the new booking to the calendar
      var newEvent = {
        id: bookingToken,
        title: 'BookedTemp',
        start: newBooking.timeStart,
        end: newBooking.timeEnd,
        backgroundColor: '#007bff',
        borderColor: '#007bff',
        textColor: '#fff',
        editable: false,
        allDay: false
      };
      console.log(newEvent)
      calendar.addEvent(newEvent);
      
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
        
    var newEvent2 = {
    title: 'Available',
    start: eventStart,
    end: eventEnd,
    backgroundColor: '#008000',
    borderColor: '#008000',
    textColor: '#fff',
    editable: false,
  };
  calendar.addEvent(newEvent2);
  
},
  
  error: function (xhr, status, error) {
    console.log("Error: " + error);
  }
  
});
}



  
  