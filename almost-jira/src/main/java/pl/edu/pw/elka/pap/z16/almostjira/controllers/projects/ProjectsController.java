package pl.edu.pw.elka.pap.z16.almostjira.controllers.projects;

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
    private static final String MSG = "success";
    public ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @PostMapping()
    public ResponseEntity<Object> addProject(@RequestBody ProjectForm newProject){
        try {
            return ResponseHandler.generateResponse(MSG, HttpStatus.CREATED, projectService.createProject(newProject));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProject(@PathVariable("id") String projectId, String userId){
        try {
            projectService.deleteProject(projectId, userId);
            return ResponseHandler.generateResponse(MSG, HttpStatus.OK, "Project deleted successfully!");
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals(ClientNotAuthorizedException.getName()))
                return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNAUTHORIZED, null);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProjectById(@PathVariable("id") String projectId){
        try {
            return ResponseHandler.generateResponse(MSG, HttpStatus.OK, projectService.getProjectById(projectId));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getProjects(){
        try {
            return ResponseHandler.generateResponse(MSG, HttpStatus.OK, projectService.getAllProjects());
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProject(@PathVariable("id") String projectId, String userId, @RequestBody ProjectForm p){
        try {
            return ResponseHandler.generateResponse(MSG, HttpStatus.OK, projectService.updateProject(p, projectId, userId));
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals(ClientNotAuthorizedException.getName()))
                    return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNAUTHORIZED, null);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
}

