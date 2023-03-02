document.addEventListener('DOMContentLoaded', function () {
  getBooking(); 
  var bookings = JSON.parse(localStorage.getItem("Bookings")); 
  var calendarEl = document.getElementById('calendar');
  var calendar = new FullCalendar.Calendar(calendarEl, {
    themeSystem: 'bootstrap5',
    timeZone: 'local',
    locale: navigator.language,
    initialView: 'timeGridWeek',
    selectable: true,
    slotMinTime: '07:00:00',
    slotMaxTime: '19:00:00',
    events: createCalendarEvents(bookings),
    hiddenDays: [0, 6],
    dateClick: function(info) {
      var localDate = moment(info.date).format('DD-MM-YYYY [Time:] HH:mm');
      var modal = document.createElement('div');
      modal.classList.add('modal', 'fade');
      modal.innerHTML = `
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">Date clicked</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <p>Clicked ${localDate}</p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" id="reserveBtn">Reserve</button>
            </div>
          </div>
        </div>
      `;
      document.body.appendChild(modal);
      var modalInstance = new bootstrap.Modal(modal);
      modalInstance.show();

      // Add click event listener to the "Reserve" button
      var reserveBtn = modal.querySelector('#reserveBtn');
      reserveBtn.addEventListener('click', function() {
        // Do something when the button is clicked
        console.log('Reserve button clicked');
        modalInstance.hide();
        modal.remove();
      });

      modal.addEventListener('hidden.bs.modal', function() {
        modalInstance.dispose();
      });
    },
    select: function(info) {      
      var localStart = moment(info.start).format('DD-MM-YYYY [Time:] HH:mm');
      var localEnd = moment(info.end).format('DD-MM-YYYY  [Time:] HH:mm');
      var modal = document.createElement('div');
      modal.classList.add('modal', 'fade');
      modal.innerHTML = `
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">Date range selected</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <p>Selected ${localStart} to ${localEnd}</p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" id="reserveBtn">Reserve</button>
            </div>
          </div>
        </div>
      `;
      document.body.appendChild(modal);
      var modalInstance = new bootstrap.Modal(modal);
      modalInstance.show();
      const token = localStorage.getItem("jwtToken")

      // Add click event listener to the "Reserve" button
      var reserveBtn = modal.querySelector('#reserveBtn');
      reserveBtn.addEventListener('click', function() {
        // Build the request body
        console.log(info.start);
        console.log(info.end);
      
        $.ajax({
          url: "http://localhost:8080/makeBooking",
          type: "POST",
           beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        },         
          headers: {
            'Content-Type': 'application/json'
          },
          data: JSON.stringify({
            "room": {
              "name": "test"
            },
            "timeStart": info.start,
            "timeEnd": info.end,
            "user": {
              "email": "test@cg.nl"
            }
          })
        })
        
        .then(function(response) {
          if (response.ok) {
            console.log('Reservation request successful');
          } else {
            console.log('Reservation request failed');
          }
        })
        .catch(function(error) {
          console.error('Error making reservation request:', error);
        });
      
        modalInstance.hide();
        modal.remove();
      });

      modal.addEventListener('hidden.bs.modal', function() {
        modalInstance.dispose();
      });
    }
  });
  calendar.render();
});

function createCalendarEvents(bookings) {
  var events = [];
  bookings.forEach(function(booking) {
    var event = {
      id: booking.id,
      title: 'Booked',
      start: booking.timeStart,
      end: booking.timeEnd,
      backgroundColor: '#007bff',
      borderColor: '#007bff',
      textColor: '#fff',
      editable: false,
      allDay: false
    };
    events.push(event);
  });
  return events;
}
