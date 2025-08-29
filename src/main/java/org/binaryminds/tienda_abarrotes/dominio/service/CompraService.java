package org.binaryminds.tienda_abarrotes.dominio.service;

import org.binaryminds.tienda_abarrotes.persistence.crud.CompraCrud;
import org.binaryminds.tienda_abarrotes.persistence.entity.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraService implements ICompraService{

    @Autowired
    private CompraCrud crud;

    @Override
    public List<Compra> listarCompras() {
        List<Compra> compras = crud.findAll();
        return compras;
    }

    @Override
    public void guardarCompra(Compra compra) {
        crud.save(compra);
    }

    @Override
    public void eliminarCompra(Compra compra) {
        crud.delete(compra);
    }
}
