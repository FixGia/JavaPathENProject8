package com.project.gpsmicroservice.Dto;

import com.project.gpsmicroservice.model.Location;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionRequest {

    private String attractionName;
    private String city;
    private String state;
    private UUID attractionId;
    private Location location;
}
