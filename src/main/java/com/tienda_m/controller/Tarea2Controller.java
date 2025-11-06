package com.tienda_m.controller;

import com.tienda_m.service.ProductoService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;


@Controller
@RequestMapping("/tareas2")
public class Tarea2Controller {

    private final ProductoService productoService;

    public Tarea2Controller(ProductoService productoService, MessageSource messageSource) {
        this.productoService = productoService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);
        return "/tareas2/listado";

    }



    @PostMapping("/tarea2JPQL")
    public String tarea2JPQL(
            @RequestParam() BigDecimal precioInf,
            @RequestParam() BigDecimal precioSup,
            Model model) {
        var productos = productoService.tarea2JPQL(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);

        return "/tareas2/listado";

    }


    
    

}
