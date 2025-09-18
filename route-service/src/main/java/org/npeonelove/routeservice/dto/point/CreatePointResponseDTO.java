package org.npeonelove.routeservice.dto.point;

import lombok.Getter;
import lombok.Setter;
import org.npeonelove.routeservice.model.Point;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CreatePointResponseDTO {

    private String id;
    private String userId;
    private String category;
    private String country;
    private String title;
    private String description;
    private List<Point> points;
    private String previewPhotoLink;
    private Date createDate;

}
