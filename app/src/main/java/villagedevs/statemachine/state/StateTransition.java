package villagedevs.statemachine.state;

import villagedevs.statemachine.job.Job;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Interface for state transition sates for job
 */
public interface StateTransition<JOB extends Job, STATE extends State> {

    boolean checkTransition(JOB job, STATE toState);

    StateTransitionResult process(JOB job, STATE state);

    Optional<StateHandler<JOB, STATE>> getStateHandler(STATE taskState);

    Map<STATE, Set<STATE>> getTransitions();

    STATE getInitialState();

    List<STATE> getFinalStatuses();

}
