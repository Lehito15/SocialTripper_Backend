package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "groups_activities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    @NotNull
    private Group group;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    @NotNull
    private Activity activity;

    public GroupActivity(Group group, Activity activity) {
        this.group = group;
        this.activity = activity;
    }
}
