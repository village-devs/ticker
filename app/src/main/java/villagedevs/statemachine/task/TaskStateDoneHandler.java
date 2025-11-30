package villagedevs.statemachine.task;

import villagedevs.statemachine.annotation.StateHandlerState;
import villagedevs.statemachine.state.StateHandler;

@StateHandlerState("DONE")
public class TaskStateDoneHandler implements StateHandler<Task, TaskState> {

    @Override
    public void handle(Task task, TaskState state) {
        System.out.println("Task handler was invoked. DONE");
    }
}
