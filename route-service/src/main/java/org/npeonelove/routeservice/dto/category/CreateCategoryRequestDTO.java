package org.npeonelove.routeservice.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.npeonelove.routeservice.model.CategoryColor;

@Getter
@Setter
public class CreateCategoryRequestDTO {

    @NotNull(message = "Category title is required")
    @Size(min = 1, max = 32, message = "Category title must be between 1 and 32 characters")
    private String title;

    @NotNull(message = "Category color is required")
    private CategoryColor color;

}
