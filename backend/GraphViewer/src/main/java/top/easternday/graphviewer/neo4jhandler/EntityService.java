package top.easternday.graphviewer.neo4jhandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EntityService {

    @Autowired
    private EntityRepository entityRepository;

    public String findEntity(String name) {
        try {
            SourceEntity entity = entityRepository.findEntity(name);
            String id = entity.getId().toString();

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("rootId", id);

            Set<Map<String, Object>> linesList = new HashSet<>();
            for (RelationShip line : entity.getOutgoings()) {
                Map<String, Object> lineMap = new HashMap<>();
                lineMap.put("from", id);
                lineMap.put("to", line.getTargetEntity().getId().toString());
                lineMap.put("text", line.getName());
                linesList.add(lineMap);
            }
            jsonMap.put("lines", linesList);

            Set<Map<String, Object>> nodesList = new HashSet<>();
            for (SourceEntity node : entity.getNodes()) {
                Map<String, Object> nodeMap = new HashMap<>();
                nodeMap.put("id", node.getId().toString());
                nodeMap.put("text", node.getName());
                nodesList.add(nodeMap);
            }
            jsonMap.put("nodes", nodesList);

            return mapper.writeValueAsString(jsonMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
