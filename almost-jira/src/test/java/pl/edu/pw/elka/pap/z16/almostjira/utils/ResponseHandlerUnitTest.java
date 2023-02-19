package pl.edu.pw.elka.pap.z16.almostjira.utils;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseHandlerUnitTest {

	@Test
	public void shouldGenerateResponse() {
		// given
		String message = "Example message";
		HttpStatus status = HttpStatus.OK;
		String data = "Example data";

		// when
		var response = ResponseHandler.generateResponse(message, status, data);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isExactlyInstanceOf(HashMap.class);
		assertThat((Map<String, Object>) response.getBody()).containsEntry("message", message);
		assertThat((Map<String, Object>) response.getBody()).containsEntry("status", status.value());
		assertThat((Map<String, Object>) response.getBody()).containsEntry("data", data);
	}

}
