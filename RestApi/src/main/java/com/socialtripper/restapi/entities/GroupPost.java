package com.socialtripper.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "groups_posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupPost {
    @Id
    private Long id;

    public GroupPost(Post post) {
        this.post = post;
    }

    public GroupPost(Post post, Group group) {
        this.post = post;
        this.group = group;
    }

    @MapsId
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
