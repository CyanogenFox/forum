package fox.forum.accounting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(HttpStatus.CONFLICT)
public class UserDublicateFoundException extends RuntimeException {
	private static final long serialVersionUID = -2024582790579825759L;

	public UserDublicateFoundException(String login) {
		super("User dublication found with login = " + login);
	}
}
