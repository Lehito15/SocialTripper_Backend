package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "users_activities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 2, scale = 1)
    @NotNull
    private BigDecimal experience;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    @NotNull
    private Activity activity;

    public UserActivity(BigDecimal experience, User user, Activity activity) {
        this.experience = experience;
        this.user = user;
        this.activity = activity;
    }

    public UserActivity(BigDecimal experience, Activity activity) {
        this.experience = experience;
        this.activity = activity;
    }
}
