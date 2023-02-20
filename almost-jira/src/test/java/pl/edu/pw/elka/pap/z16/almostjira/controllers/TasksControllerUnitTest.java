package pl.edu.pw.elka.pap.z16.almostjira.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import pl.edu.pw.elka.pap.z16.almostjira.controllers.tasks.TasksController;
import pl.edu.pw.elka.pap.z16.almostjira.services.ProjectService;
import org.mockito.Mock;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TasksControllerUnitTest {
    @Mock
    ProjectService projectServiceMock;

    @Test
    void shouldAddTaskToEmptyProject() {

        // given
        when(projectServiceMock.getTasks("number")).thenReturn(null);
        when(projectServiceMock.updateProjectTasks(any(), any())).thenReturn(null);

        TasksController controller = new TasksController(projectServiceMock);

        // when / then
        assertThat(controller.addTaskToProject("number", "").getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat((Map<String, Object>) controller.addTaskToProject("number", "").getBody())
                .containsEntry("data", null)
                .containsEntry("message", "empty task");
    }

    // TODO: test empty task list
}
