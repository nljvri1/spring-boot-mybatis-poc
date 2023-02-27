package nl.litpho.mybatis.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * CREATE TABLE actor (
 *     actor_id integer DEFAULT nextval('actor_actor_id_seq'::regclass) NOT NULL,
 *     first_name character varying(45) NOT NULL,
 *     last_name character varying(45) NOT NULL,
 *     last_update timestamp without time zone DEFAULT now() NOT NULL
 * );
 */
public class Actor {
    private Long actor_id;
    private String first_name;
    private String last_name;
    private LocalDateTime last_update;

    public Actor(Long actor_id, String first_name, String last_name, LocalDateTime last_update) {
        this.actor_id = actor_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.last_update = last_update;
    }

    public Actor() {}

    public Long getActor_id() {
        return actor_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public LocalDateTime getLast_update() {
        return last_update;
    }

    public void setActor_id(Long actor_id) {
        this.actor_id = actor_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setLast_update(LocalDateTime last_update) {
        this.last_update = last_update;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(actor_id, actor.actor_id) && Objects.equals(first_name, actor.first_name) && Objects.equals(last_name, actor.last_name) && Objects.equals(last_update, actor.last_update);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actor_id, first_name, last_name, last_update);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "actor_id=" + actor_id +
                ", first_name='" + first_name + '\'' +
                ", last_name=" + last_name +
                ", last_update=" + last_update +
                '}';
    }
}
