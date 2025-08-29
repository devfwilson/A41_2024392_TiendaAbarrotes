package org.binaryminds.tienda_abarrotes.dominio.service;

import org.binaryminds.tienda_abarrotes.persistence.entity.Compra;

import java.util.List;

public interface ICompraService {
    List<Compra> listarCompras();
    void guardarCompra(Compra compra);
    void eliminarCompra(Compra compra);
}
