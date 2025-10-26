package org.npeonelove.routeservice.dto.point;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePointRequestDTO {

    private String title;
    private String description;
    private Double[] coordinates;

}
