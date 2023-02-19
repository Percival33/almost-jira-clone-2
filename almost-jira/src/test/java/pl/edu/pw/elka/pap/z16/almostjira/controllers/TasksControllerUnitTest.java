package pl.edu.pw.elka.pap.z16.almostjira.controllers;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import pl.edu.pw.elka.pap.z16.almostjira.controllers.tasks.TasksController;
import pl.edu.pw.elka.pap.z16.almostjira.repositories.ProjectRepository;
import pl.edu.pw.elka.pap.z16.almostjira.services.ProjectService;
import pl.edu.pw.elka.pap.z16.almostjira.utils.ResponseHandler;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class TasksControllerUnitTest {
    @Mock
    ProjectService projectServiceMock;
//    = new ProjectService();

//    @InjectMocks
    TasksController controller;
//    = new TasksController(projectServiceMock);
    @BeforeMethod
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void shouldAddTaskToProject() {
        // given
        when(projectServiceMock.getTasks("number")).thenReturn(null);
        when(projectServiceMock.updateProjectUpdateTasks(any(), any())).thenReturn(null);

//        TasksController controller = new TasksController(projectServiceMock);


        // when / then
        assertThat(controller.addTaskToProject("number", "").getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(controller.addTaskToProject("number", "").getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    // TODO: test empty task list
}
