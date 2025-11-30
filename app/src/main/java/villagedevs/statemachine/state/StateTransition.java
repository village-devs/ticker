package villagedevs.statemachine.state;

import villagedevs.statemachine.job.Job;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Interface for state transition sates for job
 */
public interface StateTransition<JOB extends Job, STATE extends State> {

    /**
     * Method to check if transition is valid
     *
     * @param job job
     * @param toState to state
     * @return true if valid
     */
    boolean checkTransition(JOB job, STATE toState);

    /**
     * Method to process transition
     *
     * @param job job
     * @param state state
     */
    void process(JOB job, STATE state);

    /**
     * Method to get state handler for state
     * @param taskState task state
     * @return optional state handler
     */
    Optional<StateHandler<JOB, STATE>> getStateHandler(STATE taskState);

    /**
     * Method to get transitions map for states
     * @return transitions map
     */
    Map<STATE, Set<STATE>> getTransitions();

    /**
     * Method returns initial state for job
     * @return State of job
     */
    STATE getInitialState();

    /**
     * Method to get default state handler for state without state handler
     * @return Optional StateHandler
     */
    default Optional<StateHandler<JOB, STATE>> getDefaultStateHandler() {
        return Optional.empty();
    }

    /**
     * Method to get state handler for error situation
     * @return Optional StateHandler
     */
    default Optional<StateHandler<JOB, STATE>> getErrorStateHandler() {
        return Optional.empty();
    }
}
