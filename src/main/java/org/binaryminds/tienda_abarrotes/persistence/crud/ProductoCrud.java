package org.binaryminds.tienda_abarrotes.persistence.crud;

import org.binaryminds.tienda_abarrotes.persistence.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductoCrud extends JpaRepository<Producto, Integer> {

}
