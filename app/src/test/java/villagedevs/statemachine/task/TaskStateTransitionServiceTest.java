package villagedevs.statemachine.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import villagedevs.statemachine.state.StateHandler;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskStateTransitionServiceTest {

    @Mock
    private StateHandler<Task, TaskState> handlerMock;

    private TaskStateTransitionService service;

    @BeforeEach
    void setUp() {
        Map<TaskState, StateHandler<Task, TaskState>> handlerMap = Map.of(
                TaskState.OPENED, handlerMock,
                TaskState.DONE, handlerMock
        );

        service = new TaskStateTransitionService(handlerMap);
    }

    @Test
    void shouldCallHandlerOnValidTransition() {
        Task task = mock(Task.class);
        when(task.getState()).thenReturn(TaskState.IN_PROGRESS);

        service.process(task, TaskState.DONE);

        verify(handlerMock).handle(task, TaskState.DONE);
    }

    @Test
    void shouldThrowExceptionOnInvalidTransition() {
        Task task = mock(Task.class);
        when(task.getState()).thenReturn(TaskState.OPENED);

        service.process(task, TaskState.DONE); // not allowed

        verify(handlerMock, never()).handle(any(), any());
    }

    @Test
    void checkTransitionShouldReturnTrueForValidTransition() {
        Task task = mock(Task.class);
        when(task.getState()).thenReturn(TaskState.OPENED);

        boolean result = service.checkTransition(task, TaskState.IN_PROGRESS);
        verify(handlerMock, never()).handle(any(), any());
        assertTrue(result);
    }

    @Test
    void checkTransitionShouldReturnFalseForInvalidTransition() {
        Task task = mock(Task.class);
        when(task.getState()).thenReturn(TaskState.DONE);
        assertFalse(service.checkTransition(task, TaskState.OPENED));
    }
}