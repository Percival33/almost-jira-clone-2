package pl.edu.pw.elka.pap.z16.almostjira.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.pw.elka.pap.z16.almostjira.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}