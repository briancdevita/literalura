package com.alura.literalura;




import com.alura.literalura.Repository.LibroRepository;
import com.alura.literalura.home.Principal;
import com.alura.literalura.service.JsonConverter;
import com.alura.literalura.service.LibroService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;


@SpringBootApplication
public class LiteraluraApplication {

	private static JsonConverter jsonConverter = new JsonConverter();
	public static void main(String[] args) throws Exception {

		ApplicationContext context =  SpringApplication.run(LiteraluraApplication.class, args);

		LibroService libroService = context.getBean(LibroService.class);
		LibroRepository libroRepository = context.getBean(LibroRepository.class);
		Principal principal = new Principal(libroRepository, libroService);
		principal.mostrarMenu();

	}


}
