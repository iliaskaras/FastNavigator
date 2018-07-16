package Controllers.AsyncTaskControllers;


/**
 * Created by ILIAS on 16/7/2018.
 */

public class TaskStrategyContext {

    private ITaskStrategy taskStrategy;

    public void setTaskStrategy(ITaskStrategy taskStrategy) {
        this.taskStrategy = taskStrategy;
    }

    public ITaskStrategy getTaskStrategy() {
        return taskStrategy;
    }

    public void executeStrategy(String... params){
        this.taskStrategy.executeOnClick(params);
    }

    
}
