package fox.forum.accounting.service;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import fox.forum.accounting.dao.AccountingRepository;
import fox.forum.accounting.dto.RoleResponseDto;
import fox.forum.accounting.dto.UpdateDto;
import fox.forum.accounting.dto.UserRegDto;
import fox.forum.accounting.dto.UserResponseDto;
import fox.forum.accounting.exceptions.UserDublicateFoundException;
import fox.forum.accounting.exceptions.UserNotFoundException;
import fox.forum.accounting.model.User;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
	AccountingRepository repository;
	ModelMapper mapper;

	@Override
	public UserResponseDto register(UserRegDto regDto) {
		if (repository.existsById(regDto.getLogin()))
			throw new UserDublicateFoundException(regDto.getLogin());
		User user = mapper.map(regDto, User.class);
		String password = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(password);
		repository.save(user);
		return mapper.map(user, UserResponseDto.class);
	}

	private User findUser(String id) {
		return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	@Override
	public UserResponseDto login(String login) {
		return mapper.map(findUser(login), UserResponseDto.class);
	}

	@Override
	public UserResponseDto delete(String login) {
		User user = findUser(login);
		repository.deleteById(login);
		return mapper.map(user, UserResponseDto.class);
	}

	@Override
	public UserResponseDto update(String login, UpdateDto updateDto) {
		User user = findUser(login);
		user.setFirstName(updateDto.getFirstName());
		user.setLastName(updateDto.getLastName());
		repository.save(user);
		return mapper.map(user, UserResponseDto.class);
	}

	@Override
	public RoleResponseDto changeRoles(String login, String role, boolean isAddRole) {
		User user = findUser(login);
		boolean res;
		if (isAddRole) {
			res = user.addRole(role);
		} else {
			res = user.removeRole(role);
		}
		if (res)
			repository.save(user);
		return mapper.map(user, RoleResponseDto.class);
	}

	@Override
	public void changePassword(String login, String newPassword) {
		User user = findUser(login);
		String password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
		user.setPassword(password);
		repository.save(user);
	}

}
