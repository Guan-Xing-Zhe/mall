package com.macro.mall.demo.service.impl;

import com.macro.mall.demo.service.DateCalculatorService;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DateCalculatorServiceImpl implements DateCalculatorService {

    @Override
    public Map<String, Object> calculateWorkDays(LocalDate start, LocalDate end) {
        long total = ChronoUnit.DAYS.between(start, end) + 1;
        long workdays = 0;
        List<String> holidays = new ArrayList<>();
        LocalDate cursor = start;
        while (!cursor.isAfter(end)) {
            if (cursor.getDayOfWeek() != DayOfWeek.SATURDAY
                    && cursor.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workdays++;
            } else {
                holidays.add(cursor.toString());
            }
            cursor = cursor.plusDays(1);
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("start", start.toString());
        result.put("end", end.toString());
        result.put("totalDays", total);
        result.put("workdays", workdays);
        result.put("weekends", total - workdays);
        return result;
    }

    @Override
    public Map<String, Object> calculatePromotionRemaining(String endTime) {
        String finalEnd = (endTime != null) ? endTime : "2026-06-18 23:59:59";
        LocalDateTime end = LocalDateTime.parse(finalEnd,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(end)) {
            return Map.of("expired", true, "message", "促销已结束");
        }
        long hours = ChronoUnit.HOURS.between(now, end);
        long minutes = ChronoUnit.MINUTES.between(now, end) % 60;
        long seconds = ChronoUnit.SECONDS.between(now, end) % 60;
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("expired", false);
        result.put("remainingHours", hours);
        result.put("remainingMinutes", minutes);
        result.put("remainingSeconds", seconds);
        return result;
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    @Override
    public List<LocalDate> getNextPromotionWindows(int count) {
        List<LocalDate> windows = new ArrayList<>();
        LocalDate cursor = LocalDate.now().plusDays(1);
        int found = 0;
        while (found < count) {
            if (cursor.getDayOfWeek() == DayOfWeek.SATURDAY
                    || cursor.getDayOfWeek() == DayOfWeek.SUNDAY) {
                windows.add(cursor);
                found++;
            }
            cursor = cursor.plusDays(1);
        }
        return windows;
    }
}