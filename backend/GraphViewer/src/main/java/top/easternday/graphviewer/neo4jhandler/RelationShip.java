package top.easternday.graphviewer.neo4jhandler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Data
@RelationshipProperties
public class RelationShip {
    @RelationshipId
    private Long id;

    @Property("name")
    private String name;

    @Property("entity1_label")
    private String entity1_label;

    @Property("entity2_label")
    private String entity2_label;

    @Property("label")
    private String label;

    @TargetNode
    @JsonIgnoreProperties({"outgoings","nodes"})
    private SourceEntity targetEntity;
}
