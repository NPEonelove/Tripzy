package org.npeonelove.routeservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Point {

    @Id
    private String id;

}
