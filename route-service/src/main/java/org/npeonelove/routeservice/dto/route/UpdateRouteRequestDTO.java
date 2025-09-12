package org.npeonelove.routeservice.dto.route;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRouteRequestDTO {

    private String category;
    private String country;
    private String title;
    private String description;

}
