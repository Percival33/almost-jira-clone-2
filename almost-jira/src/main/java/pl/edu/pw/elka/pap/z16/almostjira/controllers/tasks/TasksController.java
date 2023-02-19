package pl.edu.pw.elka.pap.z16.almostjira.controllers.tasks;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.pap.z16.almostjira.services.ProjectService;
import pl.edu.pw.elka.pap.z16.almostjira.utils.ResponseHandler;

import java.util.ArrayList;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TasksController {
    private final ProjectService projectService;
    private static final String MSG = "success";

    public TasksController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @PostMapping("/{id}/tasks")
    public ResponseEntity<Object> addTaskToProject(@PathVariable("id") String projectId, String newTask){
        try {
            var modifiedList = projectService.getTasks(projectId);
            if (modifiedList == null)
                modifiedList = new ArrayList<>();
            if (newTask == null || newTask.equals(""))
                return ResponseHandler.generateResponse("empty task", HttpStatus.OK, projectService.updateProjectUpdateTasks(modifiedList, projectId));
            modifiedList.add(newTask);
            return ResponseHandler.generateResponse(MSG, HttpStatus.OK, projectService.updateProjectUpdateTasks(modifiedList, projectId));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @PutMapping("/{id}/tasks")
    public ResponseEntity<Object> modifyTaskInProject(@PathVariable("id") String projectId, int taskIndex, String modifiedTask){
        try {

            var modifiedList = projectService.getTasks(projectId);
            modifiedList.set(taskIndex-1, modifiedTask);
            return ResponseHandler.generateResponse(MSG, HttpStatus.OK, projectService.updateProjectUpdateTasks(modifiedList, projectId));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @DeleteMapping("/{id}/tasks")
    public ResponseEntity<Object> removeTaskFromProject(@PathVariable("id") String projectId, int taskIndex){
        try {
            var modifiedList = projectService.getTasks(projectId);
            modifiedList.remove(taskIndex-1);
            return ResponseHandler.generateResponse(MSG, HttpStatus.OK, projectService.updateProjectUpdateTasks(modifiedList, projectId));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
}
