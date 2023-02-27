package nl.litpho.mybatis.service;

import nl.litpho.mybatis.model.Actor;
import nl.litpho.mybatis.openapi.model.ActorDto;
import nl.litpho.mybatis.repository.ActorMapperRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
class Feature2ServiceImpl implements Feature2Service {

    @Autowired
    private ActorMapperRepository2 actorRepository;

    @Override
    public List<ActorDto> getAll() {
        return actorRepository.findAll().stream()
                .map(this::mapperToDto)
                //.peek(System.out::println)
                .toList();
    }

    private ActorDto mapperToDto(final Actor a) {
        final var actor = new ActorDto();
        actor.setActorId(a.getActor_id());
        actor.setFirstName(a.getFirst_name());
        actor.setLastName(a.getLast_name());
        actor.setLastUpdate(a.getLast_update().toString());
        return actor;
    }

    @Override
    public ActorDto add(final ActorDto newActor) {
        final var actor = new Actor();
        actor.setFirst_name(newActor.getFirstName());
        actor.setLast_name(newActor.getLastName());
        actor.setLast_update(LocalDateTime.now());

        actorRepository.save(actor);
        return mapperToDto(actor);
    }

    @Override
    public Optional<ActorDto> get(final Long actorId) {
        final Optional<Actor> actor = actorRepository.findById(actorId.intValue());

        if (actor.isPresent()) {
            final var actorResponse = new ActorDto();
            actorResponse.setActorId(actor.get().getActor_id());
            actorResponse.setFirstName(actor.get().getFirst_name());
            actorResponse.setLastName(actor.get().getLast_name());
            actorResponse.setLastUpdate(actor.get().getLast_update().toString());
            return Optional.of(actorResponse);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void delete(final Long actorId) {
        System.out.println(actorId);
        actorRepository.deleteById(actorId.intValue());
    }

    @Override
    public ActorDto update(final Long actorId, final ActorDto newActorData) {
        final var actorToUpgrade = new Actor();
        actorToUpgrade.setActor_id(actorId);
        actorToUpgrade.setFirst_name(newActorData.getFirstName());
        actorToUpgrade.setLast_name(newActorData.getLastName());
        actorToUpgrade.setLast_update(LocalDateTime.now());

        actorRepository.update(actorToUpgrade);
        return mapperToDto(actorToUpgrade);
    }
}
