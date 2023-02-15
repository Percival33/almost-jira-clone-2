package pl.edu.pw.elka.pap.z16.almostjira.controllers.Projects;

/* 1 projekt:
 project id
 project name
 tasks list
 overseer - user_id */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.elka.pap.z16.almostjira.exceptions.ClientNotAuthorizedException;
import pl.edu.pw.elka.pap.z16.almostjira.services.ProjectService;
import pl.edu.pw.elka.pap.z16.almostjira.models.ProjectForm;
import pl.edu.pw.elka.pap.z16.almostjira.utils.ResponseHandler;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProjectsController {
    private final ProjectService projectService;
    public ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @PostMapping()
    public ResponseEntity<Object> addProject(@RequestBody ProjectForm newProject){
        try {
            return ResponseHandler.generateResponse("success", HttpStatus.CREATED, projectService.createProject(newProject));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProject(@PathVariable("id") String project_id, String user_id){
        try {
            projectService.deleteProject(project_id, user_id);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, "Project deleted successfully!");
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals(ClientNotAuthorizedException.getName()))
                return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNAUTHORIZED, null);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProjectById(@PathVariable("id") String project_id){
        try {
            return ResponseHandler.generateResponse("success", HttpStatus.OK, projectService.getProjectById(project_id));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getProjects(){
        try {
            return ResponseHandler.generateResponse("success", HttpStatus.OK, projectService.getAllProjects());
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProject(@PathVariable("id") String project_id, String user_id, @RequestBody ProjectForm p){
        try {
            return ResponseHandler.generateResponse("success", HttpStatus.OK, projectService.updateProject(p, project_id, user_id));
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals(ClientNotAuthorizedException.getName()))
                    return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNAUTHORIZED, null);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
// lista słownikow reprezentujacych pojedynczy projekt
// metoda zeby odczytac dane z projektu o danym Id
// metoda zeby dodac projekt do listy (dba zeby id bylo unikalne)
// metoda zeby zmienic projekt o danym id
// metoda zeby usunac projekt  o danym id
}

