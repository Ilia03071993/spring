package com.example.school.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CourseDto(
        Integer id,

        LocalDateTime startTime,
        LocalDateTime finishTime,

        BigDecimal price,

        Integer countSeat,

        Integer countAvailableSeat,

        Integer categoryId
) {
}
