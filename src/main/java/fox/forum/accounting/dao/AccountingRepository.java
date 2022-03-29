package fox.forum.accounting.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import fox.forum.accounting.model.User;

public interface AccountingRepository
		extends MongoRepository<User, String> {

}
