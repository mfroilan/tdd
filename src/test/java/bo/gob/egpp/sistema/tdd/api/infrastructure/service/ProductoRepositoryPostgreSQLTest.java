package bo.gob.egpp.sistema.tdd.api.infrastructure.service;

import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import bo.gob.egpp.sistema.tdd.infrastructure.service.impl.ProductoRepositoryPostgreSQLImpl;
import org.junit.jupiter.api.Test;

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

}