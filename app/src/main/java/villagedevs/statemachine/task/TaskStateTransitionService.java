package villagedevs.statemachine.task;

import villagedevs.statemachine.annotation.StateServiceJob;
import villagedevs.statemachine.state.StateHandler;
import villagedevs.statemachine.common.CommonStateTransitionService;

import java.util.Map;
import java.util.Set;

@StateServiceJob
public class TaskStateTransitionService extends CommonStateTransitionService<Task, TaskState> {

    public TaskStateTransitionService(Map<TaskState, StateHandler<Task, TaskState>> handlerList) {
        super(handlerList);
    }

    @Override
    public Map<TaskState, Set<TaskState>> getTransitions() {
        return Map.of(
                TaskState.OPENED, Set.of(TaskState.IN_PROGRESS),
                TaskState.IN_PROGRESS, Set.of(TaskState.OPENED, TaskState.DONE)
        );
    }

    @Override
    public TaskState getInitialState() {
        return TaskState.OPENED;
    }
}
