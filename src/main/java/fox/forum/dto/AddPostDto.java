package fox.forum.dto;

import java.util.HashSet;

import lombok.Getter;

@Getter
public class AddPostDto {
	String title;
	String content;
	HashSet<String> tags;
}
