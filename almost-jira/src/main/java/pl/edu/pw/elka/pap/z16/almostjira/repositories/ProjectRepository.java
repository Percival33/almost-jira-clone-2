package pl.edu.pw.elka.pap.z16.almostjira.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pw.elka.pap.z16.almostjira.models.Project;

import java.util.Optional;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    Optional<Project> findById(String project_id);
}