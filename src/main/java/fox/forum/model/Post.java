package fox.forum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = "id")
public class Post {
	String id;
	@Setter
	String title;
	@Setter
	String content;
	@Setter
	String author;
	LocalDateTime dateCreated;
	@Setter // FIXME del
	HashSet<String> tags;
	@Setter // FIXME del
	long likes;
	ArrayList<Comment> comments;

	public Post() {
		this.dateCreated = LocalDateTime.now();
		this.likes = 0;
		this.comments = new ArrayList<Comment>();
	}

	public Post(String title, String content, String author, HashSet<String> tags) {
		this();
		this.title = title;
		this.content = content;
		this.author = author;
		this.tags = tags;
	}

}
