package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "personal_posts")
@NoArgsConstructor
@Getter
@Setter
public class PersonalPost {
    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public PersonalPost(Post post) {
        this.post = post;
    }
}
