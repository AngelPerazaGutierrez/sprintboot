package com.sistemadepagosibero.sistema_pagos_backend_ibero.services;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sistemadepagosibero.sistema_pagos_backend_ibero.entities.Estudiante;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.entities.Pago;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.enums.PagoStatus;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.enums.TypePago;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.repository.EstudianteRepository;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.repository.PagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional // Para asegurar que los metodos de esta clase se ejecuten dentro de una
               // transaccion.
public class PagoService {

    // inyeccion de dependencias de pagoReposotorio para interactuar con la db de
    // pagos.
    @Autowired
    private PagoRepository pagoRepository;

    // inyeccion de dependencias de estudiuantereposotorio para interactuar con la
    // db de estudiantes.
    @Autowired
    private EstudianteRepository estudianteRepository;

    /*
     * *
     * metodo para guardar el pago en la db y almacenar un archivo pdf en el server
     * 
     * @param file archivo pdf que se sube al server
     * 
     * @param cantidad monto del pago realizado
     * 
     * @param type tipo de pago EFECTIVO, CHEQUE, TRANSFERENCIA, DEPOSITO
     * 
     * @param date fecha en que se realiza el pago
     * 
     * @para codigoEstudiante codigo del estudiante que realiza el pago
     * 
     * @return objeto del pago guardado en la db
     * 
     * @throws IOExepcion excepcion lanzada si hay un error al manejar el file pdf
     */

    public Pago savePago( MultipartFile file, double cantidad, TypePago type, LocalDate date, String codigoEstudiante) 
            throws IOException {
        
        /*
             * Construir la ruta donde se guardará el archivo dentro del sistema.
             * System.getproperty("user.home") obtiene la ruta del directorio personal del usuario del actual sistema operativo
             * Paths.get Construir una ruta dentro del directorio personal "enset-data/pagos"             * 
             */
        Path folderPath = Paths.get(System.getProperty("user.home"), "enset-data", "pagos");

        // Verificar si la carpeta existe o si la debe crear

        if (!Files.exists(folderPath)) {
            Files.createDirectories( folderPath);
        }

        // generamos un nombre unico para el archivo usando UUID (identificador unico universal)
        String fileName = UUID.randomUUID().toString();

        //Construimos ruta completa del archivo agregando la extensión .pdf        
        Path filePath = Paths.get(System.getProperty("user.home"), "enset-data", "pagos", fileName + ".pdf");
        //guardamos el archivo recibido en la ubicacion especificada dentro del sistema de archivos
        Files.copy(file.getInputStream(), filePath);

        //buscar en la db el estudiante que pago con su codigo
        Estudiante estudiante = estudianteRepository.findBycodigo(codigoEstudiante);

        //crear un nuevo objeto pago utilizando un patron de diseño builder
        Pago pago = Pago.builder()
            .type(type)
            .status(PagoStatus.CREADO) //estado inicial del pago
            .fecha(date)
            .estudiante(estudiante)
            .cantidad(cantidad)
            .file(filePath.toUri().toString()) //ruta del archivo almacenado
            .build(); //contruccion final del objeto pago

        return pagoRepository.save(pago);

    
    }

    public byte[] getArchivoPorId(Long pagoId) throws IOException {

        // busca un objeto pago en la db por id
        Pago pago = pagoRepository.findById(pagoId).get();
        /**
         * pago.getFile obtiene la URI del archivo guardado como una cadena de texto
         * URI.create convierte la cadena de texto en un objeto URI
         * Path.of convierte la URI en un path (folder) para poder acceder al archivo
         * Files.readAllBytes leer en¿l contenido del archivo y lo va a devolver en un
         * arrive vector de bytes
         * Esto permite obtener el contenido del archivo para su posterior uso para
         * ejemplo descargarlo
         */
        return Files.readAllBytes(Path.of(URI.create(pago.getFile())));
    }

    public Pago actualizarPagoPorStatus(PagoStatus status, Long id) {

        //Busca en la db por su id
        Pago pago = pagoRepository.findById(id).get();

        // Actualiza el estado de pago puede ser VALIDADO O RECHAZADO
        pago.setStatus(status);

        // Guarda el objeto en la Db y lo devuelve 
        return pagoRepository.save(pago);
    }

}
