package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;


/**
 * Represents a Meeting's duration.
 * Guarantees: immutable; is valid as declared in {@link #isValidDuration(long, long)}
 */
public class Duration {

    public static final long MAX_MINUTES = 59;
    public static final String MESSAGE_CONSTRAINTS = "Number of minutes should not be more than " + MAX_MINUTES;

    public final long hours;
    public final long minutes;

    /**
     * Constructs a {@code Duration}.
     *
     * @param hours   The number of hours.
     * @param minutes The number of minutes.
     */
    public Duration(long hours, long minutes) {
        requireAllNonNull(hours, minutes);
        checkArgument(isValidDuration(hours, minutes), MESSAGE_CONSTRAINTS);
        this.hours = hours;
        this.minutes = minutes;
    }

    /**
     * Constructs a {@code Duration}.
     *
     * @param duration string of hours and minutes in the form of HH mm.
     */
    public Duration(String duration) {
        requireNonNull(duration);
        String[] dur = duration.split(" ");
        long hours = Long.parseLong(dur[0]);
        long minutes = Long.parseLong(dur[1]);
        checkArgument(isValidDuration(hours, minutes), MESSAGE_CONSTRAINTS);
        this.hours = hours;
        this.minutes = minutes;
    }

    /**
     * Returns true if a given number of minutes is valid.
     */
    public static boolean isValidDuration(long hours, long minutes) {
        return minutes <= MAX_MINUTES;
    }

    public long getHours() {
        return hours;
    }

    public long getMinutes() {
        return minutes;
    }

    @Override
    public String toString() {
        String temp = "";
        if (hours != 0) {
            temp = hours + "hrs";
        }
        if (minutes == 0) {
            return temp;
        } else {
            return temp + " " + minutes + "mins";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Duration // instanceof handles nulls
                && hours == ((Duration) other).hours && minutes == ((Duration) other).minutes); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }

    public Duration copy() {
        return new Duration(hours, minutes);
    }
}
