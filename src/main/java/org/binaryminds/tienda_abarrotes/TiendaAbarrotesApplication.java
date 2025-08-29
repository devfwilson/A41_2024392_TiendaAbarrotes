package org.binaryminds.tienda_abarrotes;

import org.binaryminds.tienda_abarrotes.dominio.service.ICompraService;
import org.binaryminds.tienda_abarrotes.dominio.service.IProductoService;
import org.binaryminds.tienda_abarrotes.persistence.entity.Compra;
import org.binaryminds.tienda_abarrotes.persistence.entity.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

//@SpringBootApplication
public class TiendaAbarrotesApplication implements CommandLineRunner{

	@Autowired
	private IProductoService productoService;

	@Autowired
	private ICompraService compraService;

	private static final Logger logger = LoggerFactory.getLogger(TiendaAbarrotesApplication.class);

	String sl = System.lineSeparator();

	public static void main(String[] args) {
		SpringApplication.run(TiendaAbarrotesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		tiendaApp();
	}

	private void tiendaApp() {
		logger.info(sl+sl+"+++++++++SISTEMA DE CONTROL DE ABARROTES+++++++++");
		var salir = false;
		var consola = new Scanner(System.in);
		while (!salir) {
			var opcion = mostrarMenu(consola);
			salir = ejecutarOpciones(consola, opcion);
			logger.info(sl);
		}
	}

	private int mostrarMenu(Scanner consola) {
		logger.info("""
				\n***Aplicación***
				1. Listar todos los productos.
				2. Buscar producto por código.
				3. Agregar nuevo producto.
				4. Modificar producto.
				5. Eliminar producto.
				6. Realizar compra.
				7. Ver compras realizadas.
				8. Salir.
				Elije una opción: \s""");
		var opcion = Integer.parseInt(consola.nextLine());
		return opcion;
	}

	private boolean ejecutarOpciones(Scanner consola, int opcion) {
		var salir = false;
		switch (opcion) {
			case 1 -> {
				logger.info("***Listado de todos los productos***"+sl);
				List<Producto> productos = productoService.listarProductos();
				productos.forEach(producto -> logger.info(producto.toString()+sl));
			}
			case 2 -> {
				logger.info(sl+"***Buscar producto por su código***"+sl);
				logger.info("Ingrese el id: ");
				var codigo = Integer.parseInt(consola.nextLine());
				Producto producto = productoService.buscarProductoPorId(codigo);
				if (producto != null){
					logger.info("Producto encontrado: "+sl +producto +sl);
				}else {
					logger.info("Producto NO encontrado: "+sl +producto +sl);
				}
			}
			case 3 -> {
				logger.info(sl+"***Agregar nuevo producto***"+sl);
				logger.info("Ingrese el nombre: ");
				var nombre = consola.nextLine();
				logger.info("Ingrese el precio: ");
				var precio = Double.parseDouble(consola.nextLine());
				logger.info("Ingrese el número de existencias: ");
				var stock = Integer.parseInt(consola.nextLine());
				var producto = new Producto();
				producto.setNombre(nombre);
				producto.setPrecio(precio);
				producto.setStock(stock);
				productoService.guardarProducto(producto);
				logger.info("Producto agregado: "+sl +producto +sl);
			}
			case 4 -> {
				logger.info("***Modificar producto***"+sl);
				logger.info("Ingrese el codigo del producto a editar: ");
				var codigo = Integer.parseInt(consola.nextLine());
				var producto = productoService.buscarProductoPorId(codigo);
				if (producto != null){
					logger.info("Producto encontrado: "+sl +producto +sl);
					logger.info("Ingrese el nuevo nombre: ");
					var nombre = consola.nextLine();
					logger.info("Ingrese el nuevo precio: ");
					var precio = Double.parseDouble(consola.nextLine());
					logger.info("Ingrese el nuevo stock: ");
					var stock = Integer.parseInt(consola.nextLine());
					producto.setNombre(nombre);
					producto.setPrecio(precio);
					producto.setStock(stock);
					productoService.guardarProducto(producto);
					logger.info("Producto modificado: "+sl +producto +sl);
				}else {
					logger.info("Producto NO encontrado "+sl +producto +sl);
				}
			}
			case 5 -> {
				logger.info(sl+"***Eliminar producto***"+sl);
				logger.info("Ingrese el codigo del producto a eliminar: ");
				var codigo = Integer.parseInt(consola.nextLine());
				var producto = productoService.buscarProductoPorId(codigo);
				if (producto != null) {
					productoService.eliminarProducto(producto);
					logger.info("Producto eliminado: "+sl +producto +sl);
				}else {
					logger.info("Producto NO encontrado "+sl +producto +sl);
				}
			}
			case 6 -> {
				logger.info(sl+"***Comprar producto***"+sl);
				logger.info("Productos disponibles: ");
				List<Producto> productos = productoService.listarProductos();
				productos.forEach(producto -> logger.info(producto.toString()+sl));
				logger.info(sl+"Ingrese el codigo del producto que desea comprar: ");
				var codigo = Integer.parseInt(consola.nextLine());
				var producto = productoService.buscarProductoPorId(codigo);
				if (producto==null){
					logger.info(sl+"Código de producto inválido"+sl);
				}else {
					logger.info(sl+"Ingrese la cantidad que desea comprar de " + producto.getNombre() + ": ");
					var cantidad = Integer.parseInt(consola.nextLine());
					if (cantidad <= 0){
						logger.info(sl+"Cantidad de producto inválida"+sl);
					}else if(cantidad > producto.getStock()) {
						logger.info(sl+"Lo sentimos, solo hay "+producto.getStock()+" existencias de "+producto.getNombre());
					}else {
						if (cantidad >=3 && cantidad < 6) {
							var total = (cantidad * producto.getPrecio()) * 0.90;
							var compra = new Compra();
							compra.setProducto(producto);
							compra.setCantidad(cantidad);
							compra.setTotal(total);
							compraService.guardarCompra(compra);
							producto.setStock(producto.getStock()-cantidad);
							productoService.guardarProducto(producto);
							logger.info(sl + "Compra completada correctamente, descuento aplicado del 10%."+sl);
							logger.info("Total (sin descuento): Q"+cantidad*producto.getPrecio()+", Total: "+total+sl);
						} else if (cantidad >= 6 && cantidad < 10) {
							var total = (cantidad * producto.getPrecio()) * 0.75;
							var compra = new Compra();
							compra.setProducto(producto);
							compra.setCantidad(cantidad);
							compra.setTotal(total);
							compraService.guardarCompra(compra);
							producto.setStock(producto.getStock()-cantidad);
							productoService.guardarProducto(producto);
							logger.info(sl+"Compra agregada correctamente, descuento aplicado del 25%."+sl);
							logger.info("Total (sin descuento): Q"+cantidad*producto.getPrecio()+", Total: "+total+sl);
						} else if (cantidad > 10) {
							var total = (cantidad * producto.getPrecio()) * 0.50;
							var compra = new Compra();
							compra.setProducto(producto);
							compra.setCantidad(cantidad);
							compra.setTotal(total);
							compraService.guardarCompra(compra);
							producto.setStock(producto.getStock()-cantidad);
							productoService.guardarProducto(producto);
							logger.info(sl+"Compra agregada correctamente, descuento aplicado del 50%."+sl);
							logger.info("Total (sin descuento): Q"+cantidad*producto.getPrecio()+", Total: "+total+sl);
						}
					}
				}
			}
			case 7 -> {
				logger.info("***Listado de todas las compras***"+sl);
				List<Compra> compras = compraService.listarCompras();
				compras.forEach(compra -> logger.info(compra.toString()+sl));
			}
			case 8 -> {
				logger.info("Hasta pronto. vaquero!"+sl+sl);
				salir = true;
				System.exit(0);
			}
			default -> {
				logger.info(sl+"Opción inválida"+sl);
			}
		}
		return false;
	}
}