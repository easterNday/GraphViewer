package top.easternday.graphviewer.neo4jhandler;


import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 这是一个用于访问和操作Neo4j中{@link SourceEntity}对象的存储库接口。
 * 它扩展了Spring Data Neo4j存储库接口({@link Neo4jRepository})，用于基本的CRUD操作。
 * {@link EntityRepository}接口定义了一个自定义查询方法，用于在图数据库中查找实体，并返回其关系和连接的节点。
 *
 * 该接口使用{@link Repository}注释，表示它是一个由Spring管理的存储库组件。
 */
@Repository
interface EntityRepository extends Neo4jRepository<SourceEntity, Long> {

    /**
     * 自定义查询方法，用于通过名称在Neo4j图数据库中查找{@link SourceEntity}。
     * 它使用Cypher查询来匹配具有给定名称的实体，并检索其关系和连接的节点。
     * @param name 要查找的实体的名称
     * @return 具有给定名称的{@link SourceEntity}对象，以及其外向关系和连接的节点，
     *         如果找不到实体，则返回{@code null}。
     */
    @Query("MATCH (e:entity {name: $name})-[r:relation]->(t) RETURN e, collect(r) as outgoings, collect(t) as nodes")
    SourceEntity findEntity(@Param("name") String name);
}


