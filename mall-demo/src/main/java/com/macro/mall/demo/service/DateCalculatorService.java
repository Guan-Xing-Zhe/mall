package com.macro.mall.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DateCalculatorService {
    Map<String, Object> calculateWorkDays(LocalDate start, LocalDate end);
    Map<String, Object> calculatePromotionRemaining(String endTime);
    boolean isHoliday(LocalDate date);
    List<LocalDate> getNextPromotionWindows(int count);
}