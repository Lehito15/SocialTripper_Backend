package com.socialtripper.restapi.entities;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "relations")
public class Relation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "relation_id")
    private UUID id;

    @Column(name = "reactions_number", nullable = false)
    @NotNull
    private int reactionsNumber;

    @Column(name = "comments_number", nullable = false)
    @NotNull
    private int commentsNumber;

    @OneToMany(mappedBy = "relation")
    private Set<RelationMultimedia> relationMultimedia;

}
