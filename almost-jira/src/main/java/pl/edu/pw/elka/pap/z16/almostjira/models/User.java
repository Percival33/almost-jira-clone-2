package pl.edu.pw.elka.pap.z16.almostjira.models;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Builder(toBuilder=true)
@Document(collection = "users")
public record User(
        @Id
        String id,
        @CreatedDate Date createdAt,
        @LastModifiedDate
        Date lastModified,


        @Setter
        String firstName,
        @Setter
        String lastName,
        @Setter
        String login,
        @Setter
        String password,
        @Setter
        List<Project> projects
) { }