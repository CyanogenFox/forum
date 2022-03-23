package fox.forum.service;

import java.util.List;

import fox.forum.dto.CommentDto;
import fox.forum.dto.PeriodDto;
import fox.forum.dto.PostDto;
import fox.forum.dto.AddPostDto;
import fox.forum.dto.TagsDto;
import fox.forum.dto.UpdateDto;

public interface ForumService {
	// FIXME list of post could be changed
	PostDto addPost(String author, AddPostDto postDto);

	List<PostDto> findPostsByTags(List<String> tags);

	List<PostDto> findPostsByPeriod(PeriodDto period);

	PostDto updatePost(String id, UpdateDto update);

	void addCommnet(String id, String from, CommentDto comment);

	PostDto findPostById(String id);

	void addLike(String id);

	List<PostDto> findPostsByAuthor(String author);

	PostDto deletePost(String id);
}
