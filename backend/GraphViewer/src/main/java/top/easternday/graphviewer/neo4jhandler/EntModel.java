package top.easternday.graphviewer.neo4jhandler;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
public class EntModel {
    @Id
    private Long id;

    private String name;
}