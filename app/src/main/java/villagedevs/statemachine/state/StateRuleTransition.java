package villagedevs.statemachine.state;

import villagedevs.statemachine.job.Job;
import villagedevs.statemachine.task.TaskState;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface StateRuleTransition<JOB extends Job, STATE extends State> {

    Map<TaskState, Set<TaskState>> getTransitions();
}
