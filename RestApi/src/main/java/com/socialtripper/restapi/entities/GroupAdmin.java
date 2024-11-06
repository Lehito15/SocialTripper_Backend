package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "groups_admins")
public class GroupAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    @NotNull
    private Group group;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @NotNull
    private Account admin;
}
