package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "relations_multimedia")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RelationMultimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "relation_id", nullable = false)
    @NotNull
    private Relation relation;

    @ManyToOne
    @JoinColumn(name = "multimedia_id", nullable = false)
    @NotNull
    private Multimedia multimedia;

    @Column(name = "location_longitude", precision = 15, scale = 8)
    private BigDecimal locationLongitude;

    @Column(name = "location_latitude", precision = 15, scale = 8)
    private BigDecimal locationLatitude;
}
