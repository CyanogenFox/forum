package fox.forum.accounting.service;

import fox.forum.accounting.dto.RoleResponseDto;
import fox.forum.accounting.dto.UpdateDto;
import fox.forum.accounting.dto.UserRegDto;
import fox.forum.accounting.dto.UserResponseDto;

public interface AccountService {
	UserResponseDto register(UserRegDto regDto);

	UserResponseDto login(String login);

	UserResponseDto delete(String login);

	UserResponseDto update(String login, UpdateDto updateDto);

	RoleResponseDto changeRoles(String login, String role, boolean isAddRole);

	void changePassword(String login, String newPassword);
}
