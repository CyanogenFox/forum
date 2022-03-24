package fox.forum.accounting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -3319755209670702462L;

	public UserNotFoundException(String login) {
		super("User " + login + " not found ");
	}
}
