package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "events_activities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "required_experience", nullable = false, precision = 2, scale = 1)
    private BigDecimal requiredExperience;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @NotNull
    private Event event;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    @NotNull
    private Activity activity;

    public EventActivity(Event event, Activity activity, BigDecimal requiredExperience) {
        this.requiredExperience = requiredExperience;
        this.event = event;
        this.activity = activity;
    }

    public EventActivity(Activity activity, BigDecimal requiredExperience) {
        this.requiredExperience = requiredExperience;
        this.activity = activity;
    }
}