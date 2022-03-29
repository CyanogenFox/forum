package fox.forum;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fox.forum.accounting.dao.AccountingRepository;
import fox.forum.accounting.model.User;

@SpringBootApplication
public class ForumServiceApplApplication implements CommandLineRunner {

	@Autowired
	AccountingRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ForumServiceApplApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!repository.existsById("admin")) {
			String password = BCrypt.hashpw("admin", BCrypt.gensalt());
			User userAccount = new User("admin", password, "", "");
			userAccount.addRole("MODERATOR");
			userAccount.addRole("ADMINISTRATOR");
			repository.save(userAccount);
		}

	}
}
