const token = localStorage.getItem("jwtToken")

function getRooms (name){
    
  $.ajax({
  url: "http://localhost:8080/getRooms",
  type: "POST",
  credentials: 'include',
  xhrFields: {
    withCredentials: true
  },
  data: {
    name:name
  },  
  success: function (data) {
    // Do something with the booking data, e.g. create calendar events
    const rooms = data
    localStorage.setItem("Rooms",JSON.stringify(rooms));
  },
  error: function (jqXHR, textStatus, errorThrown) {
    console.error('Error:', textStatus, errorThrown);
  }
});
}

  
function getBooking (){
    
    $.ajax({
    url: "http://localhost:8080/bookings",
    type: "GET",
    credentials: 'include',
    xhrFields: {
      withCredentials: true
    },
    dataType: 'text',
    success: function (data) {
      // Do something with the booking data, e.g. create calendar events
      localStorage.setItem("Bookings", data);
    },
    error: function (jqXHR, textStatus, errorThrown) {
      console.error('Error:', textStatus, errorThrown);
    }
  });
}

function getBookingByRoom (roomName){
  
  $.ajax({
  url: "http://localhost:8080/getBookingByRoom",
  type: "POST",
  credentials: 'include',
  xhrFields: {
    withCredentials: true
  },
  data: {
    roomName:roomName
  }, 

  success: function (data) {
    localStorage.setItem(roomName,JSON.stringify(data));
    
    // Do something with the booking data, e.g. create calendar events
   
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
    credentials: 'include',  
    xhrFields: {
      withCredentials: true
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
      getBookingByRoom(name);
    }, 
    
    error: function (xhr, status, error) {
      console.log("Error: " + error);
  }
  });
}
function deleteBooking(bookingToken, name) {
  $.ajax({
  url: "http://localhost:8080/cancelBooking",
  type: "POST",
  credentials: 'include',   
  xhrFields: {
    withCredentials: true
  }, 
  data: {
    token : bookingToken
  },
    
      
  success:function (data) {
        
    getBookingByRoom(name)
  
},
  
  error: function (xhr, status, error) {
    console.log("Error: " + error);
  }
  
});
}



  
  