package com.sistemadepagosibero.sistema_pagos_backend_ibero.web;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sistemadepagosibero.sistema_pagos_backend_ibero.entities.Estudiante;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.entities.Pago;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.enums.PagoStatus;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.enums.TypePago;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.repository.EstudianteRepository;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.repository.PagoRepository;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.services.PagoService;

@RestController
@CrossOrigin("*")
public class PagoController {

    @Autowired 
    private EstudianteRepository estudianteRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PagoService pagoService;

    @GetMapping("/estudiantes")
    public List<Estudiante> listarEstudiantes(){
        return estudianteRepository.findAll();
    }

    @GetMapping("/estudiantes/{codigo}")
    public Estudiante listarEstudiantePorCodigo(@PathVariable String codigo){
        return estudianteRepository.findBycodigo(codigo);
    }

    @GetMapping("/estudiantesPorPrograma")
    public List<Estudiante> listarEstudiantesPorPrograma(@RequestParam String programaId){
        return estudianteRepository.findByProgramaId(programaId);
    }
    // --------------------------
    @GetMapping("/pagos")
    public List<Pago> listarPagos(){
        return pagoRepository.findAll();
    }

    @GetMapping("/pago/{id}")
    public Pago listarPagosPorId(@PathVariable Long id){
        return pagoRepository.findById(id).get();
    }

    @GetMapping("/estudiantes/{codigo}/pagos")
    public List<Pago> listarPagosPorCodigoEstudiante(@PathVariable String codigo){
        return pagoRepository.findByEstudianteCodigo(codigo);
    }

    @GetMapping("/pagosPorStatus")
    public List<Pago> listarPagosPorStatus(@RequestParam PagoStatus status){
        return pagoRepository.findByStatus(status);
    }


    @GetMapping("/pagos/porTipo")
    public List<Pago> listarPagosPorType(@RequestParam TypePago type){
        return pagoRepository.findByType(type);
    }

    @PutMapping("pagos/{pagoId}/actualizarPago")
    public Pago ActualizarStatusPago(@RequestParam PagoStatus status, @PathVariable Long pagoId){
        return pagoService.actualizarPagoPorStatus(status, pagoId);
    }

    @PostMapping(path = "/pagos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Pago guardarPago(
        @RequestParam("file") MultipartFile file, //archivo adjunto
        double cantidad,
        TypePago type,
        LocalDate date,
        String codigoEstudiante) throws IOException {
            return pagoService.savePago(file, cantidad, type, date, codigoEstudiante);
    }

    @GetMapping(value = "/pagoFile/{pagoId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] listarArchivoPorId(@PathVariable Long pagoId) throws IOException{
        return pagoService.getArchivoPorId(pagoId);
    }
    
}
