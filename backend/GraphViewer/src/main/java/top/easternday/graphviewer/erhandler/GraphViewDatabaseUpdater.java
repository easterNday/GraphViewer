package top.easternday.graphviewer.erhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class GraphViewDatabaseUpdater {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateGraphViewDatabase() {
        jdbcTemplate.execute("UPDATE entity SET has_entity1_relation = FALSE");
        jdbcTemplate.execute("UPDATE entity SET has_entity1_relation = TRUE WHERE id IN (SELECT DISTINCT entity1_id FROM entity_relation)");
    }
}
