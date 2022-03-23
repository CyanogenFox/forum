package fox.forum.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Comment {
	String user;
	String message;
	LocalDateTime dateCreated;
	long likes;
}
