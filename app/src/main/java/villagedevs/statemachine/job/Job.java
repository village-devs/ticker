package villagedevs.statemachine.job;

import villagedevs.statemachine.state.State;

/**
 * Interface for a job.
 * @param <T> the type of state
 */
public interface Job<T extends State> {

    /**
     * Returns the state for this job.
     * @return Job state.
     */
    T getState();
}
