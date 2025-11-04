package villagedevs.statemachine.interfaces;

public interface StateService<JOB extends Job, STATE extends State> {

    boolean checkTransition(JOB job, STATE toState);

    void process(JOB job, STATE state);

    void addHandler(STATE state, StateHandler<JOB, STATE> handler);
}
