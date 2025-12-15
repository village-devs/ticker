package villagedevs.statemachine.state;

import villagedevs.statemachine.job.Job;

/**
 * Interface for handling state changes for a specific state logic
 * @param <JOB> Job
 * @param <STATE> State
 */
public interface StateHandler<JOB extends Job, STATE extends State> {

    /**
     * Handle the state change
     * @param job Job
     * @param state State
     */
    void handle(JOB job, STATE state);
}
