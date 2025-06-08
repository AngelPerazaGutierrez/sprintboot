package com.sistemadepagosibero.sistema_pagos_backend_ibero;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sistemadepagosibero.sistema_pagos_backend_ibero.entities.Estudiante;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.entities.Pago;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.enums.PagoStatus;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.enums.TypePago;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.repository.EstudianteRepository;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.repository.PagoRepository;

@SpringBootApplication
public class SistemaPagosBackendIberoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaPagosBackendIberoApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(EstudianteRepository estudianteRepository, PagoRepository pagoRepository){
		return args -> {
			// Guarda estudiante en db al iniciar la aplicacion

			estudianteRepository.save(Estudiante.builder()
			.id(UUID.randomUUID().toString())
			.nombre("Melisa")
			.apellido("Soto")
			.codigo("1234")
			.programaId("ISV1")
			.build());

			estudianteRepository.save(Estudiante.builder()
			.id(UUID.randomUUID().toString())
			.nombre("Juana")
			.apellido("Sopo")
			.codigo("12345")
			.programaId("PSV1")
			.build());

			estudianteRepository.save(Estudiante.builder()
			.id(UUID.randomUUID().toString())
			.nombre("Angel")
			.apellido("Peraza")
			.codigo("10337")
			.programaId("ISV1")
			.build());

			//Obtiene todo los valores posibles enumerdos type pago

			TypePago tiposPago[] = TypePago.values();
			//Crea un objeto random para seleccionar valores aleatorios
			Random random = new Random();

			//Itera sobre todos los estudiantes del repositorio
			estudianteRepository.findAll().forEach(estudiante -> {
				//Crea 10 pagos para cada estudiante 
				for(int i= 0; i<10; i++){
					//genere un index aleatorio
					int index = random.nextInt(tiposPago.length);

					Pago pago = Pago.builder()
					.cantidad(1000 + (int) (Math.random() * 20000))
					.type(tiposPago[index])
					.status(PagoStatus.CREADO)
					.fecha(LocalDate.now())
					.estudiante(estudiante)
					.build();

					//Guarda el pago en la db.

					pagoRepository.save(pago);
	
				}
			});
		};
	}

}
