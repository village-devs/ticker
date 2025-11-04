package villagedevs.statemachine.story;

import villagedevs.statemachine.annotation.StateHandlerState;
import villagedevs.statemachine.interfaces.StateHandler;

@StateHandlerState("DONE")
public class StoryStateDoneHandler implements StateHandler<Story, StoryState> {

    @Override
    public void handle(Story task, StoryState state) {
        System.out.println("Task handler was invoked. DONE");
    }
}
