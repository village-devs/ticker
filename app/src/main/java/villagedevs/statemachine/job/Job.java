package villagedevs.statemachine.job;

import villagedevs.statemachine.state.State;

public interface Job<T extends State> {

    public T getState();
}
