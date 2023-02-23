document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'timeGridWeek'
    });
    calendar.getOption("http://localhost:8080/demo-controller/admin");
    calendar.render();
});