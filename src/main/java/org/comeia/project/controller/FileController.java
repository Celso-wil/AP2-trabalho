package org.comeia.project.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import org.comeia.project.converter.FileConverter;
import org.comeia.project.dto.FileDTO;
import org.comeia.project.locale.ErrorMessageKeys;
import org.comeia.project.repository.FileRepository;
import org.comeia.project.service.FileuploadService;
import org.comeia.project.validator.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
public class FileController extends ResourceController {

	private final FileValidator validator;
	private final FileRepository repository;
	private final FileConverter converter;
	

	@Autowired
	FileuploadService fileuploadService;
	
	@RequestMapping(path = "/upload")
		@PostMapping
		public void upload(@RequestParam MultipartFile file) throws IllegalStateException, IOException {
		fileuploadService.uploadfile(file);
			
		}
	@RequestMapping(path="/download",method=RequestMethod.GET)
    public  ResponseEntity<InputStreamReader> downloadDocument(
                String acquistionId,
                String fileType,
                Integer expressVfId) throws IOException {
        File file2Upload = new File("C:\\Arquivos para teste\\Arquivos");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        InputStreamReader i = new InputStreamReader(new FileInputStream(file2Upload));
        System.out.println("The length of the file is : "+file2Upload.length());

        return ResponseEntity.ok().headers(headers).contentLength(file2Upload.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(i);
        }
		
	
	@PostMapping(path = "/create")
	public MappingJacksonValue criarFile(@Validated @RequestBody FileDTO dto,
			@RequestParam(required = false) String attributes) {
		
		if(Objects.isNull(dto)) {
			throw new HttpMessageNotReadableException("Required request body is missing");
		}
		
		FileDTO fileDTO = Optional.of(dto)
				.map(this.converter::to)
				.map(this.repository::save)
				.map(this.converter::from)
				.orElseThrow(() -> throwsException("Error"));
		
		try {
			File myObj = new File(dto.getName());
			if (myObj.createNewFile()) {
				System.out.println("\n Arquivo Criado: --> " + myObj.getName());
			}else {
				System.out.println("\n Arquivo Existe: ");
			}
			}catch (IOException e) {
				System.out.println("\n Erro ao criar arquivo: --> ");
				e.printStackTrace();
			}	
		return buildResponse(fileDTO, attributes);
	}
	
	
	@PostMapping(path = "/read")
	public MappingJacksonValue readFile(@Validated @RequestBody FileDTO dto,
			@RequestParam(required = false) String attributes) {
		
		if(Objects.isNull(dto)) {
			throw new HttpMessageNotReadableException("Required request body is missing");
		}
		
		FileDTO fileDTO = Optional.of(dto)
				.map(this.converter::to)
				.map(this.repository::save)
				.map(this.converter::from)
				.orElseThrow(() -> throwsException("Error"));
		
		try {
			File myObj = new File(dto.getName());
			Scanner myReader = new Scanner(myObj);
			while(myReader.hasNextLine());{
			String data = myReader.nextLine();
			System.out.println("\n");
			System.out.println(data);
			System.out.println("\n");
		}
			myReader.close();		
		}catch (IOException e) {
				System.out.println("\n Erro ao ler arquivo: --> ");
				e.printStackTrace();
			}	
		return buildResponse(fileDTO, attributes);
	}
	
	@PostMapping(path = "/deleted")
	public MappingJacksonValue deleteFile(@Validated @RequestBody FileDTO dto,
			@RequestParam(required = false) String attributes) {
		
		if(Objects.isNull(dto)) {
			throw new HttpMessageNotReadableException("Required request body is missing");
		}
		
		FileDTO fileDTO = Optional.of(dto)
				.map(this.converter::to)
				.map(this.repository::save)
				.map(this.converter::from)
				.orElseThrow(() -> throwsException("Error"));
		
			File myObj = new File(dto.getName());
			if(myObj.delete()) {
				System.out.println("\n Arquivo Deletado: --> ");
		}else  {
				System.out.println("\n Erro ao ler arquivo: --> ");
			}	
		
			return buildResponse(fileDTO, attributes);
	}
	@GetMapping(path = "{id}")
	public MappingJacksonValue getById(@PathVariable long id,
			@RequestParam(required = false) String attributes) {
		
		FileDTO dto = this.repository.findByIdAndDeletedIsFalse(id)
				.map(this.converter::from)
				.orElseThrow(() -> throwsException(ErrorMessageKeys.ERROR_FILE_NOT_FOUND_BY_ID, String.valueOf(id)));
		return buildResponse(dto, attributes);
	}
	
	@PostMapping
	public MappingJacksonValue create(@Validated @RequestBody FileDTO dto,
			@RequestParam(required = false) String attributes) {
		
		if(Objects.isNull(dto)) {
			throw new HttpMessageNotReadableException("Required request body is missing");
		}
		
		FileDTO FileDTO = Optional.of(dto)
				.map(this.converter::to)
				.map(this.repository::save)
				.map(this.converter::from)
				.orElseThrow(() -> throwsException("Error"));
		
		return buildResponse(FileDTO, attributes);
	}
	
	@PutMapping(path = "{id}")
	public MappingJacksonValue update(@PathVariable long id,
			@Validated @RequestBody FileDTO dto,
			@RequestParam(required = false) String attributes) {
		
		if(Objects.isNull(dto)) {
			throw new HttpMessageNotReadableException("Required request body is missing");
		}
		
		FileDTO fileDTO = this.repository.findByIdAndDeletedIsFalse(id)
				.map(file -> this.converter.to(dto, file))
				.map(this.repository::save)
				.map(this.converter::from)
				.orElseThrow(() -> throwsException(String.valueOf(id)));
		
		return buildResponse(fileDTO, attributes);
	}
}
