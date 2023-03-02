document.addEventListener('DOMContentLoaded', function () {
  var calendarEl = document.getElementById('calendar');
  var calendar = new FullCalendar.Calendar(calendarEl, {
    themeSystem: 'bootstrap5',
    timeZone: 'local',
    locale: navigator.language,
    initialView: 'timeGridWeek',
    selectable: true,
    slotMinTime: '07:00:00',
    slotMaxTime: '19:00:00',
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

      // Add click event listener to the "Reserve" button
      var reserveBtn = modal.querySelector('#reserveBtn');
      reserveBtn.addEventListener('click', function() {
        // Do something when the button is clicked
        console.log('Reserve button clicked');
        console.log(info.start);
        console.log(info.end);
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