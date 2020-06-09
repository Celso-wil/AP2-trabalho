package org.comeia.project.service;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileuploadService {
	public void uploadfile(@RequestParam MultipartFile file) throws IllegalStateException, IOException {
		file.transferTo(new File("C:\\Arquivos para teste\\Arquivos\\" + file.getOriginalFilename()));

	}
}	

