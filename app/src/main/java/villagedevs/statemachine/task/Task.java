package villagedevs.statemachine.task;

import villagedevs.statemachine.interfaces.Job;

public class Task implements Job {

    public Task(String id, TaskState state) {
        this.id = id;
        this.state = state;
    }

    String id;
    TaskState state;

}
