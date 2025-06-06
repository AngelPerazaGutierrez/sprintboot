package com.sistemadepagosibero.sistema_pagos_backend_ibero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemadepagosibero.sistema_pagos_backend_ibero.entities.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, String>{ //<> es para conectar estudiante por el tipo de dato primary key

    //metodo personalizado que busque un estudiante por su codigo unico
    Estudiante findBycodigo(String codigo);

    //metodo personalizado que muestre una lista de estudiantes que pertenecen a un programa en especifico
    List<Estudiante> findByProgramaId(String programaId);
    
} 