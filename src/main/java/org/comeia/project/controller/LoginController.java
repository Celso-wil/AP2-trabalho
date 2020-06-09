package org.comeia.project.controller;


import static org.comeia.project.search.DocumentSpecification.listAllByCriteria;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.comeia.project.converter.LoginConverter;
import org.comeia.project.dto.LoginDTO;
import org.comeia.project.dto.LoginFilterDTO;
import org.comeia.project.locale.ErrorMessageKeys;
import org.comeia.project.repository.LoginRepository;
import org.comeia.project.search.SearchCriteria;
import org.comeia.project.validator.LoginValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/login")
@AllArgsConstructor
public class LoginController extends ResourceController {

	private final LoginRepository repository;
	private final LoginConverter converter;
	private final LoginValidator validator;
	


	@GetMapping(path = "{id}")
	public MappingJacksonValue getById(@PathVariable long id,
			@RequestParam(required = false) String attributes) {
		
		LoginDTO dto = this.repository.findByIdAndDeletedIsFalse(id)
				.map(this.converter::from)
				.orElseThrow(() -> throwsException(ErrorMessageKeys.ERROR_LOGIN_NOT_FOUND_BY_ID, String.valueOf(id)));
		return buildResponse(dto, attributes);
	}
	
	@PostMapping
	public MappingJacksonValue create(@Validated @RequestBody LoginDTO dto,
			@RequestParam(required = false) String attributes) {
		
		if(Objects.isNull(dto)) {
			throw new HttpMessageNotReadableException("Required request body is missing");
		}
		
		LoginDTO LoginDTO = Optional.of(dto)
				.map(this.converter::to)
				.map(this.repository::save)
				.map(this.converter::from)
				.orElseThrow(() -> throwsException("Error"));
		
		return buildResponse(LoginDTO, attributes);
	}
	
	@PutMapping(path = "{id}")
	public MappingJacksonValue update(@PathVariable long id,
			@Validated @RequestBody LoginDTO dto,
			@RequestParam(required = false) String attributes) {
		
		if(Objects.isNull(dto)) {
			throw new HttpMessageNotReadableException("Required request body is missing");
		}
		
		LoginDTO loginDTO = this.repository.findByIdAndDeletedIsFalse(id)
				.map(login -> this.converter.to(dto, login))
				.map(this.repository::save)
				.map(this.converter::from)
				.orElseThrow(() -> throwsException(String.valueOf(id)));
		
		return buildResponse(loginDTO, attributes);
	}
}

