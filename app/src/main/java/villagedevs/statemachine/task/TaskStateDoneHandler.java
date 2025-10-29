package villagedevs.statemachine.task;

import villagedevs.statemachine.StateHandler;

public class TaskStateDoneHandler implements StateHandler<Task, TaskState> {//??
    //статусы унесли в аннотации
    //по сути статус указывался в аннотации
    // создается мапа Имя статуса - хендлер, но тогда проблема, что при одинаковых статусах разных сущностей будет конфликт и начнутся CANCELED и QG_CANCELED...

    // хендлеры для каждого из статусов.
    // а не джобы...
    // как процессить переходы джобы в разные статусы?
    @Override
    public void handle(Task task, TaskState state) {
        System.out.println("Task handler was invoked. DONE");
    }
}
