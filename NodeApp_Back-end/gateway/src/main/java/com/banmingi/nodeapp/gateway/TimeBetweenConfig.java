package com.banmingi.nodeapp.gateway;

import lombok.Data;

import java.time.LocalTime;

/**
 * @auther 半命i 2020/4/21
 * @description
 */
@Data
public class TimeBetweenConfig {
    private LocalTime start;
    private LocalTime end;
}
