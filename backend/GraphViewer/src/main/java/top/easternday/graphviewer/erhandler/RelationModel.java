package top.easternday.graphviewer.erhandler;  // 定义当前类所在的包名

import jakarta.persistence.*;  // 导入 jakarta.persistence 包中的所有类
import lombok.Data;  // 导入 lombok.Data 注解

@Entity  // 声明当前类为一个实体类
@Table(name = "relation")  // 指定当前实体类对应的数据库表名称为 relation
@Data  // 自动生成 getter 和 setter 方法
public class RelationModel {

    @Id  // 指定当前字段为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 指定主键的生成策略为自增长
    private Long id;  // 实体类中的一个字段，类型为 Long ，名称为 id

    @Column(name = "name")  // 指定当前字段在数据库表中的列名为 name
    private String name;  // 实体类中的一个字段，类型为 String ，名称为 name

    @Column(name = "label")  // 指定当前字段在数据库表中的列名为 label
    private String label;  // 实体类中的一个字段，类型为 String ，名称为 label
}