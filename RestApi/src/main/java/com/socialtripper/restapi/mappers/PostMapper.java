package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.PostDTO;
import com.socialtripper.restapi.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class PostMapper {
    private PostMultimediaMapper postMultimediaMapper;

    @Autowired
    public void setPostMultimediaMapper(PostMultimediaMapper postMultimediaMapper) {
        this.postMultimediaMapper = postMultimediaMapper;
    }

    public Post toEntity(PostDTO postDTO) {
        if (postDTO == null) return null;
        return new Post(
                        postDTO.uuid(),
                        postDTO.content(),
                        postDTO.dateOfPost(),
                        postDTO.isExpired(),
                        postDTO.isLocked(),
                        postDTO.commentsNumber(),
                        postDTO.reactionsNumber(),
                        postDTO.postMultimediaDTO().stream().map(postMultimediaMapper::toEntity).collect(Collectors.toSet()));
    }

    public PostDTO toDTO(Post post) {
        if (post == null) return null;
        return new PostDTO(
                            post.getUuid(),
                            post.getContent(),
                            post.getDateOfPost(),
                            post.isExpired(),
                            post.isLocked(),
                            post.getCommentsNumber(),
                            post.getReactionsNumber(),
                            post.getAccount().getUuid(),
                            post.getPostMultimedia().stream().map(postMultimediaMapper::toDTO).collect(Collectors.toSet()));
    }
}
