package villagedevs.statemachine;

public interface StateHandler<JOB, STATE> {

    void handle(JOB job, STATE state);
}
