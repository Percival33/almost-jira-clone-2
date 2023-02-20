package pl.edu.pw.elka.pap.z16.almostjira.controllers.tasks;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import pl.edu.pw.elka.pap.z16.almostjira.controllers.ApplicationController;
import pl.edu.pw.elka.pap.z16.almostjira.exceptions.ResourceNotFoundException;
import pl.edu.pw.elka.pap.z16.almostjira.services.ProjectService;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TasksController extends ApplicationController {
    private final ProjectService projectService;
    private static final String MSG = "success";

    public TasksController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @PostMapping("/{id}/tasks")
    public ResponseEntity<Object> addTaskToProject(@PathVariable("id") String projectId, String newTask){
        try {
            var modifiedList = projectService.getTasks(projectId);

            // FIXME: Adding empty task results in OK response but we won't add this task to the project
            if (isTaskEmpty(newTask)) {
                return generateResponse("empty task", HttpStatus.OK, projectService.updateProjectTasks(modifiedList, projectId));
            }
            modifiedList.add(newTask);
            return generateResponse(MSG, HttpStatus.OK, projectService.updateProjectTasks(modifiedList, projectId));
        } catch (ResourceNotFoundException e) {
            return generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @PutMapping("/{id}/tasks")
    public ResponseEntity<Object> modifyTaskInProject(@PathVariable("id") String projectId, int taskIndex, String modifiedTask){
        try {
            var modifiedList = projectService.getTasks(projectId);
            modifiedList.set(taskIndex - 1, modifiedTask);
            return generateResponse(MSG, HttpStatus.OK, projectService.updateProjectTasks(modifiedList, projectId));
        } catch (Exception e) {
            return generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @DeleteMapping("/{id}/tasks")
    public ResponseEntity<Object> removeTaskFromProject(@PathVariable("id") String projectId, int taskIndex){
        try {
            var modifiedList = projectService.getTasks(projectId);
            modifiedList.remove(taskIndex-1);
            return generateResponse(MSG, HttpStatus.OK, projectService.updateProjectTasks(modifiedList, projectId));
        } catch (Exception e) {
            return generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    private boolean isTaskEmpty(String task) {
        return task == null || task.equals("");
    }
}
