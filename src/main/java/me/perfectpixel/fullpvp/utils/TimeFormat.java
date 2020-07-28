package me.perfectpixel.fullpvp.utils;

import me.perfectpixel.fullpvp.Delegates;
import me.perfectpixel.fullpvp.message.Message;

import me.yushust.inject.Inject;
import me.yushust.inject.process.annotation.Singleton;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

@Singleton
public class TimeFormat {

    @Inject
    @Delegates
    private Message message;

    public String format(long time) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        long seconds = time / 1000;

        int unitValue = Math.toIntExact(seconds / TimeUnit.DAYS.toSeconds(7));

        if (unitValue > 0) {
            seconds %= TimeUnit.DAYS.toSeconds(7);

            String unit;

            if (unitValue == 1) {
                unit = message.getMessage(null, "format.week");
            } else {
                unit = message.getMessage(null, "format.weeks");
            }

            stringJoiner.add(unitValue + " " + unit);
        }

        unitValue = Math.toIntExact(seconds / TimeUnit.DAYS.toSeconds(1));

        if (unitValue > 0) {
            seconds %= TimeUnit.DAYS.toSeconds(1);

            String unit;

            if (unitValue == 1) {
                unit = message.getMessage(null, "format.day");
            } else {
                unit = message.getMessage(null, "format.days");
            }

            stringJoiner.add(unitValue + " " + unit);
        }

        unitValue = Math.toIntExact(seconds / TimeUnit.HOURS.toSeconds(1));

        if (unitValue > 0) {
            seconds %= TimeUnit.HOURS.toSeconds(1);

            String unit;

            if (unitValue == 1) {
                unit = message.getMessage(null, "format.hour");
            } else {
                unit = message.getMessage(null, "format.hours");
            }

            stringJoiner.add(unitValue + " " + unit);
        }

        unitValue = Math.toIntExact(seconds / TimeUnit.MINUTES.toSeconds(1));

        if (unitValue > 0) {
            seconds %= TimeUnit.MINUTES.toSeconds(1);

            String unit;

            if (unitValue == 1) {
                unit = message.getMessage(null, "format.minute");
            } else {
                unit = message.getMessage(null, "format.minutes");
            }

            stringJoiner.add(unitValue + " " + unit);
        }

        if (seconds > 0 || stringJoiner.length() == 0) {
            String unit;

            if (seconds == 1) {
                unit = message.getMessage(null, "format.second");
            } else {
                unit = message.getMessage(null, "format.seconds");
            }

            stringJoiner.add(seconds + " " + unit);
        }

        return stringJoiner.toString();
    }

}