package com.macro.mall.demo.controller;

import com.macro.mall.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "DateCalculatorController")
@Tag(name = "DateCalculatorController", description = "日期计算器 - 促销周期计算")
@RestController
@RequestMapping("/dateCalc")
public class DateCalculatorController {

    @ApiOperation("计算两个日期之间的工作日天数")
    @GetMapping("/workdays")
    public CommonResult<Map<String, Object>> countWorkDays(
            @RequestParam String start,
            @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        long total = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        long workdays = 0;
        List<String> holidays = new ArrayList<>();
        LocalDate cursor = startDate;
        while (!cursor.isAfter(endDate)) {
            if (cursor.getDayOfWeek() != DayOfWeek.SATURDAY
                    && cursor.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workdays++;
            } else {
                holidays.add(cursor.toString());
            }
            cursor = cursor.plusDays(1);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("start", start);
        result.put("end", end);
        result.put("totalDays", total);
        result.put("workdays", workdays);
        result.put("weekends", total - workdays);
        result.put("weekendDates", holidays);
        return CommonResult.success(result);
    }

    @ApiOperation("计算促销剩余时间")
    @GetMapping("/promotion-remaining")
    public CommonResult<Map<String, Object>> promotionRemaining(
            @RequestParam(required = false) String endTime) {
        String finalEnd = (endTime != null) ? endTime : "2026-06-18 23:59:59";
        LocalDateTime end = LocalDateTime.parse(finalEnd,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(end)) {
            return CommonResult.success(Map.of("expired", true, "message", "促销已结束"));
        }
        long hours = ChronoUnit.HOURS.between(now, end);
        long minutes = ChronoUnit.MINUTES.between(now, end) % 60;
        long seconds = ChronoUnit.SECONDS.between(now, end) % 60;
        Map<String, Object> result = new HashMap<>();
        result.put("expired", false);
        result.put("remainingHours", hours);
        result.put("remainingMinutes", minutes);
        result.put("remainingSeconds", seconds);
        result.put("endTime", finalEnd);
        return CommonResult.success(result);
    }

    @ApiOperation("获取当前营销日历")
    @GetMapping("/marketing-calendar")
    public CommonResult<Map<String, Object>> marketingCalendar() {
        LocalDate today = LocalDate.now();
        Map<String, Object> calendar = new HashMap<>();
        calendar.put("today", today.toString());
        calendar.put("dayOfWeek", today.getDayOfWeek().name());
        calendar.put("dayOfMonth", today.getDayOfMonth());
        calendar.put("month", today.getMonthValue());
        calendar.put("quarter", (today.getMonthValue() - 1) / 3 + 1);
        calendar.put("isWeekend", today.getDayOfWeek() == DayOfWeek.SATURDAY
                || today.getDayOfWeek() == DayOfWeek.SUNDAY);
        calendar.put("daysUntilMonthEnd",
                today.lengthOfMonth() - today.getDayOfMonth());
        return CommonResult.success(calendar);
    }
}