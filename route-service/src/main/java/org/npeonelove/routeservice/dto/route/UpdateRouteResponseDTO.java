package org.npeonelove.routeservice.dto.route;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UpdateRouteResponseDTO {

    private String id;
    private String userId;
    private String category;
    private String country;
    private String title;
    private String description;
    private String previewPhotoLink;
    private Date createDate;

}
