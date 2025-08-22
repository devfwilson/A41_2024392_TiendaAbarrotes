package org.binaryminds.tienda_abarrotes.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Compras")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoCompra;
    @Column
    private Integer codigoProducto;
    private String cantidad;
    private double total;
}
