package org.npeonelove.routeservice.dto.route;

import lombok.Getter;
import lombok.Setter;
import org.npeonelove.routeservice.model.Point;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GetRouteResponseDTO {

    private String id;
    private String userId;
    private String category;
    private String country;
    private String title;
    private String description;
    private String previewPhotoLink;
    private Date createDate;
    private List<Point> points;

}
