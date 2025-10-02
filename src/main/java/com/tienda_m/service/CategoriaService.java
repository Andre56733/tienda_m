package com.tienda_m.service;

import com.tienda_m.domain.Categoria;
import com.tienda_m.repository.CategoriaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)

    public List<Categoria> getCategorias(boolean activo) {
        if (activo) {
            return categoriaRepository.findByActivoTrue();
        }
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)

    public Optional<Categoria> getCategoria(Integer idCategoria) {
        return categoriaRepository.findById(idCategoria);
    }
    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Transactional
    public void save(Categoria categoria, MultipartFile imagenFile) {
        categoria = categoriaRepository.save(categoria);
        if (!imagenFile.isEmpty()) { //Hay imagen...
            try {
                String rutaImagen
                        = firebaseStorageService.uploadImage(imagenFile, "categoria", categoria.getIdCategoria());
                categoria.setRutaImagen(rutaImagen);
                categoriaRepository.save(categoria);
            } catch (Exception e) {

            }
        }
    }

    //Elimina registro
    @Transactional
    public void delete(Integer idCategoria) {
        //Se verifica si existe la categoria
        if (!categoriaRepository.existsById(idCategoria)) {
            //Lanza una excepcion
            throw new IllegalArgumentException("La categoria" + idCategoria + "No existe");
        }
        try {
            categoriaRepository.deleteById(idCategoria);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("No se puede eliminar esta categoria porque tiene asociacion");
        }
    }
}
