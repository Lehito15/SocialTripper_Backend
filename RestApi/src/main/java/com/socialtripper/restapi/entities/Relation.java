package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "relations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Relation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID uuid;

    public Relation(UUID uuid, int reactionsNumber, int commentsNumber, Set<RelationMultimedia> relationMultimedia) {
        this.uuid = uuid;
        this.reactionsNumber = reactionsNumber;
        this.commentsNumber = commentsNumber;
        this.relationMultimedia = relationMultimedia;
    }

    @Column(name = "reactions_number", nullable = false)
    @NotNull
    private int reactionsNumber;

    @Column(name = "comments_number", nullable = false)
    @NotNull
    private int commentsNumber;

    @OneToMany(mappedBy = "relation")
    private Set<RelationMultimedia> relationMultimedia;

}
