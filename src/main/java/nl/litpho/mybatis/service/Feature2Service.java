package nl.litpho.mybatis.service;

import nl.litpho.mybatis.openapi.model.ActorDto;

import java.util.List;
import java.util.Optional;

public interface Feature2Service {

    List<ActorDto> getAll();
    ActorDto add(ActorDto newActor);
    Optional<ActorDto> get(Long actorId);
    void delete(Long actorId);
    ActorDto update(Long actorId, ActorDto newActorData);
}
