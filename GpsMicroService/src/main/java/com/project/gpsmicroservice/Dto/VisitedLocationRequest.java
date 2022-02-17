package com.project.gpsmicroservice.Dto;

import gpsUtil.location.Location;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitedLocationRequest {

    private UUID userId;
    private Location location;
    private Date timeVisited;

    public VisitedLocationRequest(UUID userId, com.project.gpsmicroservice.model.Location location, Date timeVisited) {
    }
}
