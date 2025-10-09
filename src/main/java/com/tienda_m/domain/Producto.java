package com.tienda_m.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;
    private Integer idCategoria;


    @Column(unique = true, nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    private String descripcion;
    
    @Column(columnDefinition = "TEXT")
    private String detalle;
    
    @Column(precision=12, scale=2)
    @NotNull(message = "El precio no puede estar vacío")
    @DecimalMin(value = "0.00", inclusive=true, message="El precio debe ser mayo ro igual a 0")
    private BigDecimal precio;
    
    @NotNull(message = "El precio no puede estar vacío")
    @Min(value =0, message="Las existencias deben ser mayor o igual a 0")
    private Integer existencias;

    
    @Column(length = 1024)
    @Size(max = 1024)
    private String rutaImagen;
    
    @Column(name = "activo")
    private boolean activo;
}
