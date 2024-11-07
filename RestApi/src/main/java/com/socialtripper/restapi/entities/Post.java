package com.socialtripper.restapi.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    public Post(UUID uuid, String content, LocalDate dateOfPost, boolean isExpired, boolean isLocked, int commentsNumber, int reactionsNumber, Set<PostMultimedia> postMultimedia) {
        this.uuid = uuid;
        this.content = content;
        this.dateOfPost = dateOfPost;
        this.isExpired = isExpired;
        this.isLocked = isLocked;
        this.commentsNumber = commentsNumber;
        this.reactionsNumber = reactionsNumber;
        this.postMultimedia = postMultimedia;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid;

    @Column(length = 2220)
    private String content;

    @Column(name = "date_of_post", nullable = false)
    private LocalDate dateOfPost;

    @Column(name = "is_expired", nullable = false)
    @NotNull
    private boolean isExpired;

    @Column(name = "is_locked", nullable = false)
    @NotNull
    private boolean isLocked;

    @Column(name = "commments_number", nullable = false)
    @NotNull
    private int commentsNumber;

    @Column(name = "reactions_number", nullable = false)
    @NotNull
    private int reactionsNumber;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @NotNull
    private Account account;

    @OneToMany(mappedBy = "post")
    private Set<PostMultimedia> postMultimedia;
}