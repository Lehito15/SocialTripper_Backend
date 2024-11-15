package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.RelationMultimediaDTO;
import com.socialtripper.restapi.entities.RelationMultimedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RelationMultimediaMapper {
    private MultimediaMapper multimediaMapper;
    private RelationMapper relationMapper;

    @Autowired
    public void setMultimediaMapper(MultimediaMapper multimediaMapper) {
        this.multimediaMapper = multimediaMapper;
    }

    @Autowired
    public void setRelationMapper(RelationMapper relationMapper) {
        this.relationMapper = relationMapper;
    }

    public RelationMultimedia toEntity(RelationMultimediaDTO relationMultimediaDTO) {
        if (relationMultimediaDTO == null) return null;
        return new RelationMultimedia(
                relationMultimediaDTO.id(),
                relationMapper.toEntity(relationMultimediaDTO.relationDTO()),
                multimediaMapper.toEntity(relationMultimediaDTO.multimediaDTO()),
                relationMultimediaDTO.locationLongitude(),
                relationMultimediaDTO.locationLatitude()
        );
    }

    public RelationMultimediaDTO toDTO(RelationMultimedia relationMultimedia) {
        if (relationMultimedia == null) return null;
        return new RelationMultimediaDTO(
                relationMultimedia.getId(),
                relationMapper.toDTO(relationMultimedia.getRelation()),
                multimediaMapper.toDTO(relationMultimedia.getMultimedia()),
                relationMultimedia.getLocationLongitude(),
                relationMultimedia.getLocationLatitude()
        );
    }
}
