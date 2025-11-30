package villagedevs.statemachine.task;

import villagedevs.statemachine.annotation.StateHandlerState;
import villagedevs.statemachine.state.StateHandler;

@StateHandlerState("OPENED")
public class TaskStateOpenedHandler implements StateHandler<Task, TaskState> {

    @Override
    public void handle(Task task, TaskState state) {
        System.out.println("Task handler was invoked. OPENED");
    }
}
