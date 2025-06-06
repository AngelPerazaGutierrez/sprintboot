package com.sistemadepagosibero.sistema_pagos_backend_ibero.entities;

import java.time.LocalDate;

import com.sistemadepagosibero.sistema_pagos_backend_ibero.enums.PagoStatus;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.enums.TypePago;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Anotacion para indicar que es una entidad JPA.
@Builder // Anotacion de lombok que proporciona un patron builder.
@Data // Getter and setter, equals hashCode y toString.
@NoArgsConstructor  // Anotacion de lombok que genera un constructor sin argumentos.
@AllArgsConstructor // Anotacion de lombok que genera un constructor con todos los argumentos.

public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // primary key generada con el @
    private Long id;

    private LocalDate fecha;
    private double cantidad;
    private TypePago type; // se crea de enum  y se importa de sistema_pagos_backend_ibero.enums.TypePago
    private PagoStatus status;
    private String file;

    @ManyToOne
    private Estudiante estudiante;

    
}
