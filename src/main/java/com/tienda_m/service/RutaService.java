package com.tienda_m.service;

import com.tienda_m.domain.Ruta;
import com.tienda_m.repository.RutaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;

    public RutaService(RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }
    
    

    public List<Ruta> getRutas() {
        return rutaRepository.findAllByOrderByRequiereRolAsc();
    }

}
