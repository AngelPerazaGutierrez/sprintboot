package com.sistemadepagosibero.sistema_pagos_backend_ibero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemadepagosibero.sistema_pagos_backend_ibero.entities.Pago;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.enums.PagoStatus;
import com.sistemadepagosibero.sistema_pagos_backend_ibero.enums.TypePago;

@Repository
public interface PagoRepository extends JpaRepository <Pago, Long> {
    
    //Metodo personalizado para buscar pagos por un estudiante en especifico
    List<Pago> findByEstudianteCodigo(String codigo);

    //Metodopersonalizado para buscar los pagos por su estado /enum pagoStatus.java CREADO, VALIDADO, RECHAZADO
    List<Pago> findByStatus(PagoStatus status);

    //Metodopersonalizado para buscar los pagos por su tipo /enum TypePago.java EFECTIVO, CHEQUE, TRANSFERENCIA, DEPOSITO
    List<Pago> findByType(TypePago type);

}
