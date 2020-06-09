package org.comeia.project.converter;

import static java.util.Optional.ofNullable;

import java.util.Objects;

import org.comeia.project.domain.File;
import org.comeia.project.dto.FileDTO;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FileConverter implements Converter<File, FileDTO> {

	@Override
	public FileDTO from(File entity) {
		
		if(Objects.isNull(entity)) {
			return null;
		}
		
		FileDTO dto = new FileDTO();
		
		ofNullable(entity.getId())
			.ifPresent(dto::setId);
		
		ofNullable(entity.getName())
			.ifPresent(dto::setName);
		
		
		return dto;
	}
	
	@Override
	public File to(FileDTO dto, File entity) {
		
		if(Objects.isNull(dto)) {
			return null;
		}

		if(Objects.isNull(entity)) {
			entity = new File();
		}
		
		ofNullable(dto.getName())
			.ifPresent(entity::setName);
		
		return entity;
	}
}
