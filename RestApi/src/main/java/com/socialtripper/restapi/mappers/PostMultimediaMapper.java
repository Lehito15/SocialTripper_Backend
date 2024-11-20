package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.PostMultimediaDTO;
import com.socialtripper.restapi.entities.PostMultimedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMultimediaMapper {
//    private PostMapper postMapper;
//    private MultimediaMapper multimediaMapper;
//
//    @Autowired
//    public void setPostMapper(PostMapper postMapper) {
//        this.postMapper = postMapper;
//    }
//
//    @Autowired
//    public void setMultimediaMapper(MultimediaMapper multimediaMapper) {
//        this.multimediaMapper = multimediaMapper;
//    }
//
//    public PostMultimedia toEntity(PostMultimediaDTO postMultimediaDTO) {
//        if (postMultimediaDTO == null) return null;
//        return new PostMultimedia(
//                postMultimediaDTO.id(),
//                postMapper.toEntity(postMultimediaDTO.postDTO()),
//                multimediaMapper.toEntity(postMultimediaDTO.multimediaDTO())
//        );
//    }
//
//    public PostMultimediaDTO toDTO(PostMultimedia postMultimedia) {
//        if (postMultimedia == null) return null;
//        return new PostMultimediaDTO(
//                postMultimedia.getId(),
//                postMapper.toDTO(postMultimedia.getPost()),
//                multimediaMapper.toDTO(postMultimedia.getMultimedia())
//        );
//    }
}
