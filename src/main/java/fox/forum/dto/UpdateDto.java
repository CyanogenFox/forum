package fox.forum.dto;

import java.util.HashSet;

import lombok.Getter;

@Getter
public class UpdateDto {
	String title;
	HashSet<String> tags;
}
