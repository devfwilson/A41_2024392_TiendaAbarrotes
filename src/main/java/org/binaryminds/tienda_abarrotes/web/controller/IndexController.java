package org.binaryminds.tienda_abarrotes.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.binaryminds.tienda_abarrotes.dominio.service.ICompraService;
import org.binaryminds.tienda_abarrotes.dominio.service.IProductoService;
import org.binaryminds.tienda_abarrotes.persistence.entity.Compra;
import org.binaryminds.tienda_abarrotes.persistence.entity.Producto;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ViewScoped
public class IndexController {

    @Autowired
    IProductoService productoService;

    private Producto productoSeleccionado;
    private List<Producto> productos;

    @Autowired
    ICompraService compraService;

    private Compra compraSeleccionada;
    private List<Compra> compras;

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @PostConstruct
    public void init(){
        cargarDatos();
    }

    public void cargarDatos(){
        this.productos = this.productoService.listarProductos();
        this.productos.forEach(producto -> logger.info(producto.toString()));

        this.compras = this.compraService.listarCompras();
        this.compras.forEach(compra -> logger.info(compra.toString()));
    }

    public void agregarProducto(){
        this.productoSeleccionado = new Producto();
    }

    public void guardarProducto(){
        logger.info("Producto a guardar: "+this.productoSeleccionado);
        //Agregar (insert)
        if (this.productoSeleccionado.getCodigo_producto() == null){
            this.productoService.guardarProducto(this.productoSeleccionado);
            this.productos.add(productoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Producto agregado"));
        }

        //Modificar (update)
        else {
            this.productoService.guardarProducto(this.productoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Producto actualizado"));
        }

        //Ocultar la ventana modal
        PrimeFaces.current().executeScript("PF('ventanaModalProducto').hide()");

        //Actualizar la tabla con un método AJAX
        PrimeFaces.current().ajax().update("formulario-productos:mensaje_emergente",
                "formulario-productos:tabla-productos");

        //Reset del producto seleccionado
        this.productoSeleccionado = null;
    }

    public void eliminarProducto(){
        //Mostrar en consola
        logger.info("Producto a eliminar: "+this.productoSeleccionado);

        //Llamar a nuestro servicio de eliminación de cliente
        this.productoService.eliminarProducto(productoSeleccionado);

        //Eliminarlo de la lista clientes
        this.productos.remove(productoSeleccionado);

        //Limpiar nuestro cliente seleccionado
        this.productoSeleccionado = null;

        //Enviar un mensaje emergente de confirmación
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Producto eliminado"));

        //Actualizar la tabla con AJAX
        PrimeFaces.current().ajax().update("formulario-productos:mensaje_emergente",
                "formulario-productos:tabla-productos");
    }

    public void agregarCompra(){
        this.compraSeleccionada = new Compra();
    }

    public void guardarCompra(){
        logger.info("Compra a realizar: "+this.compraSeleccionada);
        this.compraService.guardarCompra(this.compraSeleccionada);
        this.compras.add(compraSeleccionada);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Compra realizada"));
    }

    public void eliminarCompra(){
        logger.info("Compra a eliminar: "+this.compraSeleccionada);
        this.compraService.eliminarCompra(compraSeleccionada);
        this.compras.remove(compraSeleccionada);
        this.compraSeleccionada = null;
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Compra eliminada"));
        PrimeFaces.current().ajax().update("formulario-compras:mensaje_emergente",
                "formulario-compras:tabla-compras");
    }
}
