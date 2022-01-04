package com.example.demo;

import com.example.demo.DTO.CustomerDTO;
import com.example.demo.model.Role;

import com.example.demo.service.CustomerService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@Slf4j
public class CustomerApp {





	public static void main(String[] args) {
		SpringApplication.run(CustomerApp.class, args);
	}


	@Bean
	PasswordEncoder passwordEncoder(){

		return new BCryptPasswordEncoder();

}
	//DTO'dan dataya datadan DTO'ya dönüştürme işlemlerinde kullandığım model mapperım.
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	//Swagger dokümantasyonu içim oluşturduğum bean.
	@Bean
	public OpenAPI customOpenAPI(@Value("${application-description}")String description,@Value("${application-version")String version){
		return new OpenAPI()
				.info(new Info().
						title("CustomerAppAPI")
						.version(version)
						.description(description)
						.license(new License().name("Customer Application API Lıcense")));
	}
	//Database'e uygulama ayağa kalkarken veri eklemek için CommandLineRunner kullandım ve user rolüyle iki kullanıcı ekledim.
	@Bean
	CommandLineRunner run(CustomerService customerService) {
		return args->{

			//Üç adet rol oluşturdum.
			Role r1=new Role(1L,"ROLE_USER");
			Role r2=new Role(null,"ROLE_MANAGER");
			Role r3=new Role(3l,"ROLE_ADMIN");
			//Rolleri veritabanına kaydettiğim kısım.
			customerService.addRole(r1);
			customerService.addRole(r2);
			customerService.addRole(r3);


			//İki adet customer objesi
			CustomerDTO c1=new CustomerDTO(null,"ahmet","ahmet11","ahmet111",new ArrayList<>());
			CustomerDTO c2=new CustomerDTO(null,"ahmet2","ahmet22","ahmet222",new ArrayList<>());

			//Customer objelerinin veritabanına kadedildiği bölüm
			customerService.addCustomer(c1);
			customerService.addCustomer(c2);
			//Rollerin customerlara atandığı kısım
			customerService.addRoleToCustomer("ahmet11","ROLE_USER");
			customerService.addRoleToCustomer("ahmet11","ROLE_ADMIN");
			customerService.addRoleToCustomer("ahmet22","ROLE_USER");
		};


	}


}
