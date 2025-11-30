package villagedevs.statemachine.task;

import villagedevs.statemachine.job.Job;

public class Task implements Job<TaskState> {

    public Task(String id, TaskState state) {
        this.id = id;
        this.state = state;
    }

    String id;
    TaskState state;

    @Override
    public TaskState getState() {
        return state;
    }
}
