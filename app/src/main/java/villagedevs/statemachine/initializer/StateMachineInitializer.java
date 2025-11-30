package villagedevs.statemachine.initializer;

import villagedevs.statemachine.StateMachineManager;

/**
 * Initializer for a state machine initialization.
 */
public interface StateMachineInitializer {

    /**
     * Method to create a new state machine manager.
     *
     * @return The new state machine manager.
     */
    StateMachineManager createNewManager();
}
