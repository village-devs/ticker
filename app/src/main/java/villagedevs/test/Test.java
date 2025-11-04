package villagedevs.test;

import villagedevs.statemachine.StateMachineInitializer;
import villagedevs.statemachine.StateMachineManager;
import villagedevs.statemachine.story.Story;
import villagedevs.statemachine.story.StoryState;
import villagedevs.statemachine.task.Task;
import villagedevs.statemachine.task.TaskState;

public class Test {

    public static void main(String[] args) {
        System.out.println("Start task.");
        StateMachineManager stateMachineManager = new StateMachineInitializer().createNewManager();
        Task testTask = new Task("test_id", TaskState.OPENED);
        stateMachineManager.publishSate(testTask, TaskState.DONE);
        stateMachineManager.publishSate(testTask, TaskState.OPENED);
        System.out.println("End task.");

        System.out.println("Start story.");

        Story story = new Story("story_id", StoryState.OPENED);
        stateMachineManager.publishSate(story, StoryState.IN_PROGRESS);
        stateMachineManager.publishSate(story, StoryState.DONE);

        System.out.println("End story.");
    }

    /*
    * флоу такой:
    * точка входа публикация(прямой вызов) евента/сервиса который идет в менеджер процессоров
    * 1) меняем статус - идем в менеджер процессоров и отдаем джобу и новый статус
    * 2) ищем процессор для этой джобы
    * 3) проверям возможность перевода в новый статус
    * 4) кидаем евент на новый статус
    * 4,5) или просто в менеджере обработчиков ищем нужный (тип джобы/статус)
    * 5) в менеджере обработчиков ищем для этого статуса обработчик
    * 6) вызываем код в обработчике
    * */
}
