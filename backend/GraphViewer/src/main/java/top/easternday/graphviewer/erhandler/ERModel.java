package top.easternday.graphviewer.erhandler;  // 定义当前类所在的包名

import jakarta.persistence.*;  // 导入 jakarta.persistence 包中的所有类
import lombok.Data;  // 导入 lombok.Data 注解

@Entity  // 声明当前类为一个实体类
@Table(name = "entity_relation")  // 指定当前实体类对应的数据库表名称为 entity_relation
@Data  // 自动生成 getter 和 setter 方法
public class ERModel {

    @Id  // 指定当前字段为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 指定主键的生成策略为自增长
    private Long id;  // 实体类中的一个字段，类型为 Long ，名称为 id

    @ManyToOne(optional = false)  // 指定当前字段为多对一关系
    @JoinColumn(name = "entity1_id")  // 指定当前字段在数据库表中的列名为 entity1_id
    private EntModel entity1;  // 表示一个实体

    @ManyToOne(optional = false)  // 指定当前字段为多对一关系
    @JoinColumn(name = "relation_id")  // 指定当前字段在数据库表中的列名为 relation_id
    private RelationModel relation;  // 表示一个关系

    @ManyToOne(optional = false)  // 指定当前字段为多对一关系
    @JoinColumn(name = "entity2_id")  // 指定当前字段在数据库表中的列名为 entity2_id
    private EntModel entity2;  // 表示另一个实体
}