package nl.litpho.mybatis.controller;

import nl.litpho.mybatis.openapi.ActorsApi;
import nl.litpho.mybatis.openapi.model.ActorDto;
import nl.litpho.mybatis.service.Feature2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActorController implements ActorsApi {
    @Autowired
    private Feature2Service actorService;

    @Override
    public ResponseEntity<List<ActorDto>> getActors() {
        return ResponseEntity.ok().body(actorService.getAll());
    }

    @Override
    public ResponseEntity<ActorDto> addActor(final ActorDto newActor) {
        return ResponseEntity.ok().body(actorService.add(newActor));
    }


    @Override
    public ResponseEntity<ActorDto> getActor(final Long actorId) {
        final var result = actorService.get(actorId);
        if(result.isPresent()) {
            return ResponseEntity.ok().body(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteActor(final Long actorId) {
        final var result = actorService.get(actorId);
        if(result.isPresent()) {
            actorService.delete(actorId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<ActorDto> updateActor(final Long actorId, final ActorDto actorData) {
        final var result = actorService.get(actorId);
        if(result.isPresent()) {
            final var actorUpdated = actorService.update(actorId, actorData);
            return ResponseEntity.ok().body(actorUpdated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
