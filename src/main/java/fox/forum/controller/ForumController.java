package fox.forum.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fox.forum.dto.CommentDto;
import fox.forum.dto.PeriodDto;
import fox.forum.dto.PostDto;
import fox.forum.dto.AddPostDto;
import fox.forum.dto.UpdateDto;
import fox.forum.service.ForumService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/forum")
public class ForumController {
	ForumService service;

	@PostMapping("/post/{author}")
	public PostDto addPost(@PathVariable String author, @RequestBody AddPostDto postDto) {
		return service.addPost(author, postDto);
	}

	@PostMapping("/posts/tags")
	public List<PostDto> findPostsByTags(@RequestBody List<String> tags) {
		return service.findPostsByTags(tags);
	}

	@PostMapping("/posts/period")
	public List<PostDto> findPostsByPeriod(@RequestBody PeriodDto period) {
		return service.findPostsByPeriod(period);
	}

	@PutMapping("/post/{id}")
	public PostDto updatePost(@PathVariable String id, @RequestBody UpdateDto update) {
		return service.updatePost(id, update);
	}

	@PutMapping("/post/{id}/comment/{from}")
	public void addCommnet(@PathVariable String id, @PathVariable String from, @RequestBody CommentDto comment) {
		service.addCommnet(id, from, comment);
	}

	@GetMapping("/post/{id}")
	public PostDto findPostById(@PathVariable String id) {
		return service.findPostById(id);
	}

	@PutMapping("/post/{id}/like")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addLike(@PathVariable String id) {
		service.addLike(id);
	}

	@GetMapping("/posts/author/{author}")
	public List<PostDto> findPostsByAuthor(@PathVariable String author) {
		return service.findPostsByAuthor(author);
	}

	@DeleteMapping("/post/{id}")
	public PostDto deletePost(@PathVariable String id) {
		return service.deletePost(id);
	}

}
