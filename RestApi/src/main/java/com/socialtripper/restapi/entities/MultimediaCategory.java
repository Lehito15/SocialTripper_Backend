package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "multimedia_categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MultimediaCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    @NotNull
    private String name;
}
