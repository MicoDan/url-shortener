package com.url.shortener.dtos;

import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object for click event analytics
 * Used to transfer click statistics data to the frontend
 */
@Data
public class ClickEventDTO {
    // The date when clicks occurred
    private LocalDate clickDate;
    
    // Total number of clicks for this date
    private Long count;
}
