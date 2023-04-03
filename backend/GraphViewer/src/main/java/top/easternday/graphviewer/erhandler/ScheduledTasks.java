package top.easternday.graphviewer.erhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ScheduledTasks {

    @Autowired
    private GraphViewDatabaseUpdater updater;

    // 每天凌晨执行该任务
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledUpdate() {
        updater.updateGraphViewDatabase();
    }
}

