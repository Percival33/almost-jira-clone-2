package pl.edu.pw.elka.pap.z16.almostjira.models;

import lombok.Builder;
import lombok.Getter;
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

        @Getter
        String firstName,
        @Getter
        String lastName,
        @Getter
        String login,
        @Getter
        String password,
        @Getter
        List<Project> projects
) { }