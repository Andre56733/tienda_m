package com.tienda_m.repository;

import com.tienda_m.domain.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Integer>{
    
}
