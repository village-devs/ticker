package villagedevs.statemachine.story;

import villagedevs.statemachine.annotation.StateHandlerState;
import villagedevs.statemachine.interfaces.StateHandler;

@StateHandlerState("IN_PROGRESS")
public class StoryStateInProgressHandler implements StateHandler<Story, StoryState> {

    @Override
    public void handle(Story task, StoryState state) {
        System.out.println("Task handler was invoked. IN_PROGRESS");
    }
}
