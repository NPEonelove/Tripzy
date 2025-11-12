package org.npeonelove.routeservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "points")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Point {

    @Id
    @Column(name = "point_id")
    private UUID pointId;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @ToString.Exclude
    private Route route;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Transient
    private Double xCoordinate;

    @Transient
    private Double yCoordinate;

    @Column(name = "coordinates")
    private String coordinates;

    @Column(name = "photo_link")
    private String photoLink;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "sort_order")
    private Integer sortOrder;

    public void setXCoordinate(Double xCoordinate) {
        this.xCoordinate = xCoordinate;
        if (this.yCoordinate != null) {
            updateCoordinates();
        }
    }

    public void setYCoordinate(Double yCoordinate) {
        this.yCoordinate = yCoordinate;
        if (this.xCoordinate != null) {
            updateCoordinates();
        }
    }

    private void updateCoordinates() {
        if (this.xCoordinate != null && this.yCoordinate != null) {
            this.coordinates = xCoordinate + "," + yCoordinate;
        }
    }

    @PrePersist
    @PreUpdate
    private void prepareCoordinates() {
        updateCoordinates();
    }

    @PostLoad
    private void parseCoordinates() {
        if (coordinates != null &&  !coordinates.isEmpty()) {
            String[] parts = coordinates.split(",");
            this.xCoordinate = Double.parseDouble(parts[0]);
            this.yCoordinate = Double.parseDouble(parts[1]);
        }
    }
}
