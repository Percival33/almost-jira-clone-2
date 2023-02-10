package pl.edu.pw.elka.pap.z16.almostjira.models;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;

@Builder
public record UserForm(
    @Getter
    String firstName,
    @Getter
    String lastName,
    @Getter
    @Indexed(unique = true)
    String login,
    @Getter
    String password
) { }