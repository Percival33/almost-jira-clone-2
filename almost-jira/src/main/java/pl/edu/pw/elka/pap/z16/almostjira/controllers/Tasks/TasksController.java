package pl.edu.pw.elka.pap.z16.almostjira.controllers.Tasks;


// lista słownikow reprezentujacych pojedynczy projeky
// metoda zeby odczytac dane z projektu o danym Id
// metoda zeby dodac projekt do listy (dba zeby id bylo unikalne)
// metoda zeby zmienic projekt o danym id
// metoda zeby usunac projekt  o danym id

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

    public TasksController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @PostMapping("/{id}/tasks")
    public ResponseEntity<Object> addTaskToProject(@PathVariable("id") String projectId, String newTask){
        try {
            var modifiedList = projectService.getTasks(projectId);
            if (modifiedList == null)
                modifiedList = new ArrayList<String>();
            if (newTask == null || newTask == "")
                return ResponseHandler.generateResponse("empty task", HttpStatus.OK, projectService.updateProjectUpdateTasks(modifiedList, projectId));
            modifiedList.add(newTask);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, projectService.updateProjectUpdateTasks(modifiedList, projectId));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @PutMapping("/{id}/tasks")
    public ResponseEntity<Object> modifyTaskInProject(@PathVariable("id") String project_id, int taskIndex, String modifiedTask){
        try {

            var modifiedList = projectService.getTasks(project_id);
            modifiedList.set(taskIndex-1, modifiedTask);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, projectService.updateProjectUpdateTasks(modifiedList, project_id));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @DeleteMapping("/{id}/tasks")
    public ResponseEntity<Object> removeTaskFromProject(@PathVariable("id") String project_id, int taskIndex){
        try {
            var modifiedList = projectService.getTasks(project_id);
            modifiedList.remove(taskIndex-1);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, projectService.updateProjectUpdateTasks(modifiedList, project_id));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
}
