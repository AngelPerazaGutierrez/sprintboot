package com.sistemadepagosibero.sistema_pagos_backend_ibero.entities;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Anotacion para indicar que es una entidad JPA.
@Builder // Anotacion de lombok que proporciona un patron builder.
@Data // Getter and setter, equals hashCode y toString.
@NoArgsConstructor  // Anotacion de lombok que genera un constructor sin argumentos.
@AllArgsConstructor // Anotacion de lombok que genera un constructor con todos los argumentos.

// define la clase estudiante
public class Estudiante {

    @Id // indica que este dato es primary key en la base de datos
    private String id;
    private String nombre;
    private String apellido;

    @Column(unique = true) // Anotacion que indica este campo debe ser unico en la base de datos
    private String codigo;

    private String programaId;
    private String foto;

    
    
}
