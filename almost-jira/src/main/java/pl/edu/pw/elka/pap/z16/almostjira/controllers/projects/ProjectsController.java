package pl.edu.pw.elka.pap.z16.almostjira.controllers.projects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import pl.edu.pw.elka.pap.z16.almostjira.exceptions.ClientNotAuthorizedException;
import pl.edu.pw.elka.pap.z16.almostjira.exceptions.ResourceNotFoundException;
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
        return ResponseHandler.generateResponse(MSG, HttpStatus.CREATED, projectService.createProject(newProject));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProject(@PathVariable("id") String projectId, String userId){
        try {
            projectService.deleteProject(projectId, userId);
            return ResponseHandler.generateResponse(MSG, HttpStatus.OK, "Project deleted successfully!");
        } catch (ClientNotAuthorizedException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNAUTHORIZED, null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProjectById(@PathVariable("id") String projectId){
        try {
            return ResponseHandler.generateResponse(MSG, HttpStatus.OK, projectService.getProjectById(projectId));
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getProjects(){
        return ResponseHandler.generateResponse(MSG, HttpStatus.OK, projectService.getAllProjects());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProject(@PathVariable("id") String projectId, String userId, @RequestBody ProjectForm p){
        try {
            return ResponseHandler.generateResponse(MSG, HttpStatus.OK, projectService.updateProject(p, projectId, userId));
        } catch (ClientNotAuthorizedException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNAUTHORIZED, null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
}

