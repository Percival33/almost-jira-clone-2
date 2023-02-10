package pl.edu.pw.elka.pap.z16.almostjira.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.elka.pap.z16.almostjira.exceptions.ClientNotAuthorizedException;
import pl.edu.pw.elka.pap.z16.almostjira.exceptions.ResourceNotFoundException;
import pl.edu.pw.elka.pap.z16.almostjira.models.Project;
import pl.edu.pw.elka.pap.z16.almostjira.models.ProjectForm;
import pl.edu.pw.elka.pap.z16.almostjira.repositories.ProjectRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    public Project getProjectById(String id) {
        return projectRepository.findById(id).orElseThrow((() ->
                new ResourceNotFoundException("Project", "id", id)));
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProject(String id, String userId) {
        projectRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Project", "id", id));
        if (projectRepository.findById(id).get().overseerId().equals(userId))
            projectRepository.deleteById(id);
        else throw(new ClientNotAuthorizedException());
    }

    public Project updateProject(ProjectForm p, String id, String userId) {
        Project existingProject = projectRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Project", "Id", id));
        if (!projectRepository.findById(id).get().overseerId().equals(userId))
            throw(new ClientNotAuthorizedException());

        Date now = new Date();
        Project updatedProject = existingProject.toBuilder()
                .overseerId((p.overseerId() == null) ? existingProject.overseerId() : p.overseerId())
                .projectName((p.projectName() == null) ? existingProject.projectName() : p.projectName())
                .tasks((p.tasks() == null) ? existingProject.tasks() : p.tasks())
                .lastModified(now)
                .build();

        return projectRepository.save(updatedProject);
    }

    public Project createProject(ProjectForm p) {
        Date now = new Date();
        return projectRepository.save(
                Project.builder()
                        .id(String.valueOf(UUID.randomUUID()))
                        .overseerId(p.overseerId())
                        .projectName(p.projectName())
                        .createdAt(now)
                        .lastModified(now)
                        .tasks(p.tasks())
                        .build());
    }

    public Project updateProjectUpdateTasks(List<String> modifiedList, String id) {
        Project existingProject = projectRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Project", "Id", id));

        Date now = new Date();
        Project updatedProject = existingProject.toBuilder()
                .tasks(modifiedList)
                .lastModified(now)
                .build();

        return projectRepository.save(updatedProject);
    }

    public List<String> getTasks(String id){
        Project existingProject = projectRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Project", "Id", id));
        return existingProject.tasks();
    }
}
