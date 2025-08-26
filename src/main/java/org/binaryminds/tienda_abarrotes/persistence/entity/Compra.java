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
    private Integer codigo_compra;
    @ManyToOne
    @JoinColumn(name = "codigo_producto", referencedColumnName = "codigo_producto")
    private Producto producto;
    @Column
    private Integer cantidad;
    private double total;
}
