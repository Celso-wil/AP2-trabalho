package org.comeia.project.converter;
import static java.util.Optional.ofNullable;
import java.util.Objects;

import org.comeia.project.domain.Login;
import org.comeia.project.dto.LoginDTO;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LoginConverter implements Converter<Login, LoginDTO> {

	@Override
	public LoginDTO from(Login entity) {
		
		if(Objects.isNull(entity)) {
			return null;
		}
		
		LoginDTO dto = new LoginDTO();
		
		ofNullable(entity.getId())
			.ifPresent(dto::setId);
		
		ofNullable(entity.getFullName())
			.ifPresent(dto::setFullName);
		
		ofNullable(entity.getSenha())
		.ifPresent(dto::setSenha);
		
		return dto;
	}
	
	@Override
	public Login to(LoginDTO dto, Login entity) {
		
		if(Objects.isNull(dto)) {
			return null;
		}

		if(Objects.isNull(entity)) {
			entity = new Login();
		}
		
		ofNullable(dto.getFullName())
			.ifPresent(entity::setFullName);
		
		ofNullable(dto.getSenha())
			.ifPresent(entity::setSenha);
		
		return entity;
	}
}
