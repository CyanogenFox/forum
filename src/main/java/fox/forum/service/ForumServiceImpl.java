package fox.forum.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import fox.forum.dao.ForumRepository;
import fox.forum.dto.CommentDto;
import fox.forum.dto.PeriodDto;
import fox.forum.dto.PostDto;
import fox.forum.dto.AddPostDto;
import fox.forum.dto.UpdateDto;
import fox.forum.exceptions.PostNotFoundException;
import fox.forum.model.Comment;
import fox.forum.model.Post;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ForumServiceImpl implements ForumService {
	ForumRepository repository;
	ModelMapper mapper;

	@Override
	public PostDto addPost(String author, AddPostDto postDto) {
		Post post = mapper.map(postDto, Post.class);
		post.setAuthor(author);
//		repository.save(post);
		post = repository.save(post);
		return mapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> findPostsByTags(List<String> tags) {
		return repository.findByTagsInIgnoreCase(tags).map(p -> mapper.map(p, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<PostDto> findPostsByPeriod(PeriodDto period) {
		return repository.findByDateCreatedBetween(period.getDateFrom(), period.getDateTo())
				.map(p -> mapper.map(p, PostDto.class)).collect(Collectors.toList());
	}

	@Override
	public PostDto updatePost(String id, UpdateDto update) {
		Post post = mapper.map(repository.findById(id).orElse(null), Post.class);
		if (!update.getTitle().equals(null))
			post.setTitle(update.getTitle());
		if (!update.getTags().equals(null))
			post.setTags(update.getTags());
		repository.save(post);
		return mapper.map(post, PostDto.class);
	}

	@Override
	public void addCommnet(String id, String from, CommentDto commentDto) {
		Post post = repository.findById(id).orElse(null);
		// FIXME check nulls
		post.getComments().add(new Comment(from, commentDto.getMessage(), LocalDateTime.now(), 0));
		repository.save(post);
	}

	@Override
	public PostDto findPostById(String id) {
		Post post = repository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		return mapper.map(post, PostDto.class);
	}

	@Override
	public void addLike(String id) {
		Post post = repository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		post.setLikes(post.getLikes() + 1);
		repository.save(post);
	}

	@Override
	public List<PostDto> findPostsByAuthor(String author) {
		return repository.findByAuthorIgnoreCase(author).map(p -> mapper.map(p, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public PostDto deletePost(String id) {
		Post post = mapper.map(repository.findById(id).orElseThrow(() -> new PostNotFoundException(id)), Post.class);
		repository.deleteById(id);
		return mapper.map(post, PostDto.class);
	}

}
