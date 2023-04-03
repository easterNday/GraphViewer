package top.easternday.graphviewer.erhandler;  // 定义当前接口所在的包名

import java.util.List;  // 导入 java.util 包中的 List 类
import org.springframework.data.jpa.repository.JpaRepository;  // 导入 Spring Data JPA 提供的 JpaRepository 接口
import org.springframework.data.jpa.repository.Query;  // 导入 Spring Data JPA 提供的 Query 注解
import org.springframework.data.repository.query.Param;  // 导入 Spring Data JPA 提供的 Param 注解
import org.springframework.stereotype.Repository;  // 导入 Spring Framework 提供的 Repository 注解

@Repository  // 声明当前接口为一个仓库
public interface ERRepo extends JpaRepository<ERModel, Long> {
    /**
     * 这里是通过实体的name来查询并自然连接的SQL脚本，对应下面所编写的接口。<br/>
     * SELECT e1.name AS entity_1_name, r.name AS relation, e2.name AS entity_2, e2.has_entity1_relation AS has_entity1_relation FROM entity e1
     * JOIN entity_relation er ON e1.id = er.entity1_id
     * JOIN relation r ON er.relation_id = r.id
     * JOIN entity e2 ON er.entity2_id = e2.id
     * WHERE e1.name = :name
     * 此处实例中，我定义的name为`秦始皇`，实际接口使用中name可以为任意字段。
     *
     * @param name 数据库中实体对应的name
     * @return 数据库中查找到的所有数据
     */

    @Query(value = "SELECT e1.name AS entity_1_name, r.name AS relation, e2.name AS entity_2, e2.has_entity1_relation AS has_entity1_relation FROM entity e1 JOIN entity_relation er ON e1.id = er.entity1_id JOIN relation r ON er.relation_id = r.id JOIN entity e2 ON er.entity2_id = e2.id WHERE e1.name = :name", nativeQuery = true)
    List<Object[]> findEntityRelationsByEntityName(@Param("name") String name);
}