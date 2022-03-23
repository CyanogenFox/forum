package fox.forum.dao;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Stream;
import org.springframework.data.mongodb.repository.MongoRepository;

import fox.forum.dto.PostDto;
import fox.forum.model.Post;

public interface ForumRepository extends MongoRepository<Post, String> {
	Stream<PostDto> findByAuthorIgnoreCase(String author);

	Stream<PostDto> findByDateCreatedBetween(LocalDate from, LocalDate to);

	Stream<PostDto> findByTagsInIgnoreCase(Collection<String> tags);
}
