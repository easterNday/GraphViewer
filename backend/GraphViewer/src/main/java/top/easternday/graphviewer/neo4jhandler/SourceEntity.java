package top.easternday.graphviewer.neo4jhandler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Node("entity")
@Data
public class SourceEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    @JsonProperty("text")
    private String name;

    @JsonIgnoreProperties({"outgoings","nodes"})
    @Relationship(value = "relation")
    private List<RelationShip> outgoings;

    @JsonIgnoreProperties({"outgoings","nodes"})
    @Relationship(value = "relation")
    private List<SourceEntity> nodes;
}
