package fox.forum.accounting.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import fox.forum.accounting.model.User;

public interface AccountigRepository
		extends MongoRepository<User, String> {

}
