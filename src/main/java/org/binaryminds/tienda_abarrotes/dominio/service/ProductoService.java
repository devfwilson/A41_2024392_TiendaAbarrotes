package org.binaryminds.tienda_abarrotes.dominio.service;

import org.binaryminds.tienda_abarrotes.persistence.crud.ProductoCrud;
import org.binaryminds.tienda_abarrotes.persistence.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    private ProductoCrud crud;

    @Override
    public List<Producto> listarProductos() {
        List<Producto> productos = crud.findAll();
        return productos;
    }

    @Override
    public Producto buscarProductoPorId(Integer codigo) {
        Producto producto = crud.findById(codigo).orElse(null);
        return producto;
    }

    @Override
    public void guardarProducto(Producto producto) {
        crud.save(producto);
    }

    @Override
    public void eliminarProducto(Producto producto) {
        crud.delete(producto);
    }
}
