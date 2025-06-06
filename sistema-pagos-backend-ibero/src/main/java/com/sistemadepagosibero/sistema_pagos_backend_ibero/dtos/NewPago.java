package com.sistemadepagosibero.sistema_pagos_backend_ibero.dtos;

import java.time.LocalDate;

import com.sistemadepagosibero.sistema_pagos_backend_ibero.enums.TypePago;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Getter and setter, equals hashCode y toString.
@NoArgsConstructor  // Anotacion de lombok que genera un constructor sin argumentos.
@AllArgsConstructor // Anotacion de lombok que genera un constructor con todos los argumentos.

public class NewPago {
    
    private double cantidad;
    private TypePago typePago;
    private LocalDate date;
    private String codigoEstudiante;


}
