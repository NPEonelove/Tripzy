package org.npeonelove.routeservice.dto.category;

import lombok.Getter;
import lombok.Setter;
import org.npeonelove.routeservice.model.CategoryColor;

import java.util.UUID;

@Getter
@Setter
public class CreateCategoryResponseDTO {

    private UUID categoryId;
    private String title;
    private String color;

}
