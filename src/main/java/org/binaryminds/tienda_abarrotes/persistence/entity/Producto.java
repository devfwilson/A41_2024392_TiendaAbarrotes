package org.binaryminds.tienda_abarrotes.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoProducto;
    @Column
    private String nombre;
    private double precio;
    private Integer stock;
}
