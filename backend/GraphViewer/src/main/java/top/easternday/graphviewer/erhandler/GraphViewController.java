package top.easternday.graphviewer.erhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraphViewController {

    @Autowired
    private GraphViewDatabaseUpdater updater;

    @GetMapping("/refresh")
    public void refreshGraphViewDatabase() {
        updater.updateGraphViewDatabase();
    }
}
