package villagedevs.statemachine.story;

import villagedevs.statemachine.annotation.StateHandlerState;
import villagedevs.statemachine.interfaces.StateHandler;

@StateHandlerState("OPENED")
public class StoryStateOpenedHandler implements StateHandler<Story, StoryState> {

    @Override
    public void handle(Story task, StoryState state) {
        System.out.println("Task handler was invoked. OPENED");
    }
}
