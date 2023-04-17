package top.easternday.graphviewer.neo4jhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin  // 允许跨域访问
public class EntityController {
    @Autowired
    private EntityService entityService;

    @GetMapping("/entities/{name}")
    public String getEntitiesByName(@PathVariable String name) {
        return entityService.findEntity(name);
    }
}
