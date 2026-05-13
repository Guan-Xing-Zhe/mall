package com.macro.mall.demo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DateCalculatorServiceImplTest {

    @InjectMocks
    private DateCalculatorServiceImpl service;

    @Test
    void testCalculateWorkDays_WeekdaysOnly() {
        Map<String, Object> result = service.calculateWorkDays(
                LocalDate.of(2026, 5, 11),
                LocalDate.of(2026, 5, 15));
        assertEquals(5L, result.get("totalDays"));
        assertEquals(5L, result.get("workdays"));
        assertEquals(0L, result.get("weekends"));
    }

    @Test
    void testCalculateWorkDays_WithWeekend() {
        Map<String, Object> result = service.calculateWorkDays(
                LocalDate.of(2026, 5, 8),
                LocalDate.of(2026, 5, 11));
        assertEquals(4L, result.get("totalDays"));
        assertEquals(2L, result.get("workdays"));
        assertEquals(2L, result.get("weekends"));
    }

    @Test
    void testIsHoliday_Weekend() {
        assertTrue(service.isHoliday(LocalDate.of(2026, 5, 10))); // Sunday
        assertTrue(service.isHoliday(LocalDate.of(2026, 5, 16))); // Saturday
    }

    @Test
    void testIsHoliday_Weekday() {
        assertFalse(service.isHoliday(LocalDate.of(2026, 5, 11))); // Monday
        assertFalse(service.isHoliday(LocalDate.of(2026, 5, 15))); // Friday
    }

    @Test
    void testGetNextPromotionWindows() {
        var windows = service.getNextPromotionWindows(2);
        assertEquals(2, windows.size());
        windows.forEach(d -> {
            assertTrue(service.isHoliday(d));
        });
    }
}