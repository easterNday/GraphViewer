package top.easternday.graphviewer.neo4jhandler;

import org.neo4j.driver.types.Entity;
import org.neo4j.driver.types.Relationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntityRepository extends Neo4jRepository<Entity, Long> {

    @Query("MATCH (e:Entity {name: $name})-[r1]->(related1) RETURN e, r1, related1")
    List<Relationship> findRelatedEntities(@Param("name") String name);
}

