package villagedevs.statemachine.interfaces;

public interface StateHandler<JOB extends Job, STATE extends State> {

    void handle(JOB job, STATE state);
}
