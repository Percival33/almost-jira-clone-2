package pl.edu.pw.elka.pap.z16.almostjira.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ApplicationController {

	private ApplicationController() {

	}

	public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("status", status.value());
		map.put("message", message);
		map.put("data", responseObj);

		return new ResponseEntity<>(map, status);
	}

}
