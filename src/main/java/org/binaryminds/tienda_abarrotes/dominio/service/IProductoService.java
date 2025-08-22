package org.binaryminds.tienda_abarrotes.dominio.service;

import org.binaryminds.tienda_abarrotes.persistence.entity.Producto;

import java.util.List;

public interface IProductoService {
    List<Producto> listarProductos();
    Producto buscarProductoPorId(Integer codigo);
    void guardarProducto(Producto producto);
    void eliminarProducto(Producto producto);
}
