package fox.forum.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import fox.forum.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	String id;
	String title;
	String content;
	String author;
	LocalDateTime dateCreated;
	HashSet<String> tags;
	long likes;
	ArrayList<Comment> comments;
}
