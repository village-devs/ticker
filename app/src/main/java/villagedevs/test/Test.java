package villagedevs.test;

import villagedevs.statemachine.StateMachineManager;
import villagedevs.statemachine.Task;
import villagedevs.statemachine.TaskState;
import villagedevs.statemachine.TaskStateService;

public class Test {

    public static void main(String[] args) {
        //по сути это код менеджера
        // принимаем джобу и статус
        // ищем нужный процессор и вызываем его
        System.out.println("Start.");
        StateMachineManager stateMachineManager = new StateMachineManager();
        Task testTask = new Task("test_id", TaskState.OPENED);
        stateMachineManager.publishSate(testTask, TaskState.DONE);
        //процессор проверяет возможность перехода
        //вызываем менеджер хендлеров статусов - который ищет нужны хендлер по типу джобы/статусу.
        // подумать как искать нужный хендлер.
        System.out.println("End.");
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
