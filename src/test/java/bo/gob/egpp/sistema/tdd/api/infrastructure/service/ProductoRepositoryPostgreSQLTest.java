package bo.gob.egpp.sistema.tdd.api.infrastructure.service;

import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import bo.gob.egpp.sistema.tdd.infrastructure.service.impl.ProductoRepositoryPostgreSQLImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Pruebas unitarias para la clase ProductoRepositoryPostgreSQLImpl.
 * Aunque el nombre sugiere una implementación de repositorio para PostgreSQL,
 * esta prueba se centra en la implementación actual que utiliza una lista en memoria.
 * <p>
 * El objetivo es verificar que la operación de registrar un producto
 * efectivamente añade el producto a la lista simulada.
 */
public class ProductoRepositoryPostgreSQLTest {

    // SUT (System Under Test): instancia de ProductoRepositoryPostgreSQLImpl que se está probando.
    private final ProductoRepositoryPostgreSQLImpl sut = new ProductoRepositoryPostgreSQLImpl();

    @Test // Anotación que indica que este método es una prueba unitaria.
    public void deberiaRegistrarProductoToBaseDeDatos() {
        // Creación de un producto vacío usando el patrón Builder para la prueba.
        final Producto producto = Producto.builder().build();

        // Registro del producto a través del método del SUT.
        sut.registrarProducto(producto);

        // Verificación de que la lista de productos contiene exactamente un elemento después del registro.
        // Esto indica que el producto fue añadido correctamente.
        assertEquals(1, ProductoRepositoryPostgreSQLImpl.productos.size());
    }

    @Test
    public void deberiaEliminarProductoToBaseDeDatos() {
        final int identificadorToEliminar = 1;
        final Producto producto = Producto.builder()
                .id(identificadorToEliminar)
                .build();
        sut.registrarProducto(producto);
        sut.removerProducto(identificadorToEliminar);
        assertEquals(0, ProductoRepositoryPostgreSQLImpl.productos.size());
    }

    @Test
    public void deberiaEncontrarProductoPorId() {
        final int identificadorToBuscar = 1;
        final Producto producto = Producto.builder()
                .id(identificadorToBuscar)
                .build();
        sut.registrarProducto(producto);
        Producto productoBuscado = sut.encontrarProductoPorId(identificadorToBuscar);
        assertEquals(identificadorToBuscar, productoBuscado.getId());
    }

    @Test
    public void deberiaRetornarTodosLosProductos() {
        final Producto producto_1 = Producto.builder().id(1).build();
        final Producto producto_2 = Producto.builder().id(2).build();
        final Producto producto_3 = Producto.builder().id(3).build();
        final Producto producto_4 = Producto.builder().id(4).build();
        final Producto producto_5 = Producto.builder().id(5).build();

        sut.registrarProducto(producto_1);
        sut.registrarProducto(producto_2);
        sut.registrarProducto(producto_3);
        sut.registrarProducto(producto_4);
        sut.registrarProducto(producto_5);

        List<Producto> productos = sut.listarTodosLosProductos();
        final int totalProductos = 5;
        assertEquals(totalProductos, productos.size());
        int identificadorEsperado = 3;
        for (Producto p : productos) {
            if (p.getId() == identificadorEsperado)
                assertEquals(identificadorEsperado, p.getId());
            identificadorEsperado++;
        }
    }

}
