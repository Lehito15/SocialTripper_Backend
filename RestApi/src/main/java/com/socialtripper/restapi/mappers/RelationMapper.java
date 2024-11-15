package com.socialtripper.restapi.mappers;

import com.socialtripper.restapi.dto.entities.RelationDTO;
import com.socialtripper.restapi.entities.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class RelationMapper {
    private RelationMultimediaMapper relationMultimediaMapper;

    @Autowired
    public void setRelationMultimediaMapper(RelationMultimediaMapper relationMultimediaMapper) {
        this.relationMultimediaMapper = relationMultimediaMapper;
    }

    public Relation toEntity(RelationDTO relationDTO) {
        if (relationDTO == null) return null;
        return new Relation(
                relationDTO.uuid(),
                relationDTO.reactionsNumber(),
                relationDTO.commentsNumber(),
                relationDTO.multimediaDTOs().stream()
                                            .map(relationMultimediaMapper::toEntity)
                                            .collect(Collectors.toSet())
        );
    }

    public RelationDTO toDTO(Relation relation) {
        if (relation == null) return null;
        return new RelationDTO(
                relation.getUuid(),
                relation.getReactionsNumber(),
                relation.getCommentsNumber(),
                relation.getRelationMultimedia().stream()
                                                .map(relationMultimediaMapper::toDTO)
                                                .collect(Collectors.toSet())
        );
    }

}
