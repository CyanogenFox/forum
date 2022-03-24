package fox.forum.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 3052058614258769239L;

	public PostNotFoundException(String id) {
		super("Post with id = " + id + " not found");
	}
}
