package org.npeonelove.routeservice.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "routes")
public class Route {

    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @Field("category")
    private String category;

    @Field("country")
    private String country;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("preview_photo_link")
    private String previewPhotoLink;

    @CreatedDate
    @Field("create_date")
    private Date createDate;

    @Field("points")
    private List<Point> points;

}
