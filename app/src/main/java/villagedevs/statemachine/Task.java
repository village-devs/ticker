package villagedevs.statemachine;

public class Task implements Job { //наследник общей Джобы


    public Task(String id, TaskState state) {
        this.id = id;
        this.state = state;
    }

    String id;
    TaskState state;

}
