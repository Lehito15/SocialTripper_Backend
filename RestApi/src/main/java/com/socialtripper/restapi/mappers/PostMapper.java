package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.PostDTO;
import com.socialtripper.restapi.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class PostMapper {
    private PostMultimediaMapper postMultimediaMapper;
    private AccountThumbnailMapper accountThumbnailMapper;

    @Autowired
    public void setPostMultimediaMapper(PostMultimediaMapper postMultimediaMapper) {
        this.postMultimediaMapper = postMultimediaMapper;
    }

    @Autowired
    public void setAccountThumbnailMapper(AccountThumbnailMapper accountThumbnailMapper) {
        this.accountThumbnailMapper = accountThumbnailMapper;
    }

    public Post toEntity(PostDTO postDTO) {
        if (postDTO == null) return null;
        return new Post(
                        postDTO.uuid(),
                        postDTO.content());
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
                accountThumbnailMapper.toDTO(post.getAccount()),
                post.getPostMultimedia().stream().map(postMultimediaMapper::toDTO).collect(Collectors.toSet())
        );
    }

    public Post copyNonNullValues(Post post, PostDTO postDTO) {
        if (postDTO == null) return null;
        if (postDTO.uuid() != null) post.setUuid(postDTO.uuid());
        if (postDTO.content() != null) post.setContent(postDTO.content());
        if (postDTO.isExpired() != null)  post.setExpired(postDTO.isExpired());
        if (postDTO.isLocked() != null)  post.setLocked(postDTO.isLocked());
        if (postDTO.commentsNumber() != null) post.setCommentsNumber(postDTO.commentsNumber());
        if (postDTO.reactionsNumber() != null) post.setReactionsNumber(postDTO.reactionsNumber());
        return post;
    }
}
