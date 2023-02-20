package pl.edu.pw.elka.pap.z16.almostjira.utils;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseHandlerUnitTest {

	@Test
	public void shouldGenerateResponse() {
		// given
		String message = "Example message";
		HttpStatus status = HttpStatus.OK;
		String data = "Example data";

		HashMap<String, Object> expectedBody = new HashMap<>();
		expectedBody.put("message", message);
		expectedBody.put("status", status.value());
		expectedBody.put("data", data);

		// when
		var response = ResponseHandler.generateResponse(message, status, data);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isExactlyInstanceOf(HashMap.class);
		assertThat(response.getBody()).isEqualTo(expectedBody);
	}

}
