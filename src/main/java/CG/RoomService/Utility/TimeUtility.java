package CG.RoomService.Utility;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class TimeUtility {

    public static OffsetDateTime timeConverter(OffsetDateTime time) {
        ZoneId cetZoneId = ZoneId.of("Europe/Paris"); // you can replace "Europe/Paris" with the appropriate time zone ID
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

        ZonedDateTime utcDateTime = ZonedDateTime.parse(time.toString(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
// convert the date and time to the Central European Time zone

        ZonedDateTime cetDateTime = utcDateTime.withZoneSameInstant(cetZoneId);

// format the date and time in the CET time zone

        String formattedDateTime = cetDateTime.format(formatter);
        return OffsetDateTime.parse(formattedDateTime);
    }
}
