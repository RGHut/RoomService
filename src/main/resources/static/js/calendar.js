document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'timeGridWeek',
        selectable: true,
        dateClick: function(info) {
                alert('clicked ' + info.dateStr);
              },
              select: function(info) {
                alert('selected ' + info.startStr + ' to ' + info.endStr);
              }

    });
    calendar.getOption("http://localhost:8080/demo-controller/admin");
    calendar.render();

});