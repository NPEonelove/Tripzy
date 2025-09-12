package org.npeonelove.routeservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Point {

    @Id
    private String id;

    @Field("routeId")
    private String routeId;

    @Field("coordinates")
    private double[] coordinates;



}
