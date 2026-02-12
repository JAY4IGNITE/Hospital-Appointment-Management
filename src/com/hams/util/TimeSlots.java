package com.hams.util;

import java.util.Arrays;
import java.util.List;

public class TimeSlots {
    private static final List<String> SLOTS = Arrays.asList(
            "09:00 AM",
            "10:00 AM",
            "11:00 AM",
            "12:00 PM",
            "02:00 PM",
            "03:00 PM",
            "04:00 PM",
            "05:00 PM");

    public static List<String> getAll() {
        return SLOTS;
    }

    public static String getSlot(int index) {
        if (index >= 0 && index < SLOTS.size()) {
            return SLOTS.get(index);
        }
        return null;
    }
}
