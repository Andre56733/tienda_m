package com.tienda_m.service;

import com.tienda_m.domain.Producto;
import com.tienda_m.repository.ProductoRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional(readOnly = true)

    public List<Producto> getProductos(boolean activo) {
        if (activo) {
            return productoRepository.findByActivoTrue();
        }
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)

    public Optional<Producto> getProducto(Integer idProducto) {
        return productoRepository.findById(idProducto);
    }
    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Transactional
    public void save(Producto producto, MultipartFile imagenFile) {
        producto = productoRepository.save(producto);
        if (!imagenFile.isEmpty()) { //Hay imagen...
            try {
                String rutaImagen
                        = firebaseStorageService.uploadImage(imagenFile, "producto", producto.getIdProducto());
                producto.setRutaImagen(rutaImagen);
                productoRepository.save(producto);
            } catch (Exception e) {

            }
        }
    }

    //Elimina registro
    @Transactional
    public void delete(Integer idProducto) {
        //Se verifica si existe la producto
        if (!productoRepository.existsById(idProducto)) {
            //Lanza una excepcion
            throw new IllegalArgumentException("La producto" + idProducto + "No existe");
        }
        try {
            productoRepository.deleteById(idProducto);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("No se puede eliminar esta producto porque tiene asociacion");
        }

    }

    @Transactional(readOnly = true)
    public List<Producto> consultaDerivada(BigDecimal precioInf, BigDecimal precioSup) {
        return productoRepository.findByPrecioBetweenOrderByPrecioAsc(precioInf, precioSup);
    }
    
    @Transactional(readOnly = true)
    public List<Producto> consultaJPQL(BigDecimal precioInf, BigDecimal precioSup) {
        return productoRepository.consultaJPQL(precioInf, precioSup);
    }
    
    @Transactional(readOnly = true)
    public List<Producto> consultaSQL(BigDecimal precioInf, BigDecimal precioSup) {
        return productoRepository.consultaSQL(precioInf, precioSup);
    }
    
    @Transactional(readOnly = true)
    public List<Producto> tarea2JPQL(BigDecimal precioInf, BigDecimal precioSup) {
        return productoRepository.tarea2JPQL(precioInf, precioSup);
    }
    
}
