package org.npeonelove.routeservice.dto.point;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreatePointRequestDTO {

    private String title;
    private String description;
    private Double[] coordinates;

}
