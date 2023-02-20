package pl.edu.pw.elka.pap.z16.almostjira.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pw.elka.pap.z16.almostjira.models.Project;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
}