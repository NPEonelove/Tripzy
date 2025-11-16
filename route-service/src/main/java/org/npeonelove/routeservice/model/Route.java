package org.npeonelove.routeservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "routes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Route {

    @Id
    @Column(name = "route_id")
    @EqualsAndHashCode.Include
    private UUID routeId;

    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

    @Column(name = "country")
    private String country;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "preview_photo_link")
    private String previewPhotoLink;

    @OneToMany(mappedBy = "route")
    @ToString.Exclude
    private List<Point> points;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

}
