package fox.forum.accounting.controller;

import java.util.Base64;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fox.forum.accounting.dto.RoleResponseDto;
import fox.forum.accounting.dto.UpdateDto;
import fox.forum.accounting.dto.UserRegDto;
import fox.forum.accounting.dto.UserResponseDto;
import fox.forum.accounting.service.AccountService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountingController {

	AccountService service;

	@PostMapping("/register")
	public UserResponseDto register(@RequestBody UserRegDto regDto) {
		return service.register(regDto);
	}

	@PostMapping("/login")
	public UserResponseDto login(@RequestHeader("Authorization") String token) {
		return service.login(getLoginFromToken(token));
	}

	private String getLoginFromToken(String token) {
		token = token.split(" ")[1];
		String decode = new String(Base64.getDecoder().decode(token));
		return decode.split(":")[0];
	}

	@DeleteMapping("/user/{login}")
	public UserResponseDto delete(@PathVariable String login) {
		return service.delete(login);
	}

	@PutMapping("/user/{login}")
	public UserResponseDto update(@PathVariable String login, @RequestBody UpdateDto updateDto) {
		return service.update(login, updateDto);
	}

	@PutMapping("/user/{login}/role/{role}")
	public RoleResponseDto addRole(@PathVariable String login, @PathVariable String role) {
		return service.changeRoles(login, role, true);
	}

	@DeleteMapping("/user/{login}/role/{role}")
	public RoleResponseDto removeRole(@PathVariable String login, @PathVariable String role) {
		return service.changeRoles(login, role, false);
	}

	@PutMapping("/password")
	public void changePassword(@RequestHeader("Authorization") String token,
			@RequestHeader("X-Password") String newPassword) {
		service.changePassword(getLoginFromToken(token), newPassword);
	}
}
