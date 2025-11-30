package villagedevs.statemachine.state;

import villagedevs.statemachine.job.Job;

public interface StateHandler<JOB extends Job, STATE extends State> {

    void handle(JOB job, STATE state);
}
