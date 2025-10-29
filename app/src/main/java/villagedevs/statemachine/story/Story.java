package villagedevs.statemachine.story;

import villagedevs.statemachine.Job;

public class Story implements Job { //наследник общей Джобы

    public Story(String id, StoryState state) {
        this.id = id;
        this.state = state;
    }

    String id;
    StoryState state;

}
