package top.easternday.graphviewer.neo4jhandler;

import lombok.Data;

@RelationshipProperties
@Data
public class RelationshipModel {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "label")
    private String label;

    // 可选，用于存储实体标签
    @Property(name = "entity1_label")
    private String entity1Label;

    @Property(name = "entity2_label")
    private String entity2Label;


    @StartNode
    private EntModel entity1;

    @TargetNode
    private EntModel entity2;
}
