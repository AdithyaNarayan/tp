package seedu.address.model.meeting;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Predicate;

public class MeetingWithinHoursPredicate implements Predicate<Meeting> {
    private final int hour;

    public MeetingWithinHoursPredicate(int hour) {
        this.hour = hour;
    }

    @Override
    public boolean test(Meeting meeting) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime meetingTime = meeting.getDateTime().getValue();

        if (meetingTime.isBefore(currentTime)) {
            return false;
        }

        return Duration.between(currentTime, meetingTime).toHours() <= this.hour;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MeetingWithinHoursPredicate // instanceof handles nulls
                && hour == ((MeetingWithinHoursPredicate) other).hour); // state check
    }
}
