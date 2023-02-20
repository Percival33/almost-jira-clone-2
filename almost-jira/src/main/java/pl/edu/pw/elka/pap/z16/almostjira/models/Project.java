package pl.edu.pw.elka.pap.z16.almostjira.models;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder(toBuilder=true)
@Document(collection = "projects")
public record Project(
        @Id
        String id,
        @Getter
        String overseerId,
        @CreatedDate
        Date createdAt,
        @LastModifiedDate
        Date lastModified,

        @Getter
        String projectName,
        @Getter
        List<String> tasks
) {
    public Project {
        tasks = tasks == null ? new ArrayList<>() : tasks;
    }
}
