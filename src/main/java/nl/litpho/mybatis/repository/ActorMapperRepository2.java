package nl.litpho.mybatis.repository;

import nl.litpho.mybatis.generated.client.ActorTableMapper;
import nl.litpho.mybatis.generated.model.ActorTable;
import nl.litpho.mybatis.model.Actor;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static nl.litpho.mybatis.generated.client.ActorTableDynamicSqlSupport.actorTable;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Repository
public class ActorMapperRepository2 {

    private final ActorTableMapper mapper;

    public ActorMapperRepository2(final ActorTableMapper mapper) {
        this.mapper = mapper;
    }

    public List<Actor> findAll() {
        return mapper.select(SelectDSLCompleter.allRows())
                .stream()
                .map(this::actorTableToActor)
                .toList();
    }

    public Optional<Actor> findById(final int id) {
        return mapper.selectOne(s -> s.where(actorTable.actorId, isEqualTo(id)))
                .map(this::actorTableToActor);
    }

    public void deleteById(final int id) {
        mapper.delete(d -> d.where(actorTable.actorId, isEqualTo(id)));
    }

    public Actor save(final Actor employee) {
        final ActorTable a = new ActorTable();
        a.setFirstName(employee.getFirst_name());
        a.setLastName(employee.getLast_name());
        a.setLastUpdate(employee.getLast_update());
        mapper.insert(a);
        return actorTableToActor(a);
    }

    public void update(final Actor employee) {
        mapper.update(u ->
                u.set(actorTable.firstName).equalTo(employee.getFirst_name())
                        .set(actorTable.lastName).equalTo(employee.getLast_name())
                        .set(actorTable.lastUpdate).equalTo(employee.getLast_update())
                        .where(actorTable.actorId, isEqualTo(employee.getActor_id().intValue())));
    }

    private Actor actorTableToActor(final ActorTable a) {
        final Actor actor = new Actor();
        actor.setActor_id(a.getActorId().longValue());
        actor.setFirst_name(a.getFirstName());
        actor.setLast_name(a.getLastName());
        actor.setLast_update(a.getLastUpdate());
        return actor;
    }
}
