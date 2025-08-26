package org.binaryminds.tienda_abarrotes.persistence.crud;

import org.binaryminds.tienda_abarrotes.persistence.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraCrud extends JpaRepository<Compra, Integer> {
}
