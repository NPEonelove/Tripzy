package org.npeonelove.routeservice.dto.route;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRouteResponseDTO {

    private String id;
    private String userId;
    private String category;
    private String country;
    private String title;
    private String description;

}
