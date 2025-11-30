package villagedevs.statemachine.common.result;

import villagedevs.statemachine.state.StateTransitionResult;

public class CommonStateTransitionResult implements StateTransitionResult {

    private final boolean isSuccessful;

    public CommonStateTransitionResult(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    @Override
    public boolean isSuccessful() {
        return isSuccessful;
    }
}
