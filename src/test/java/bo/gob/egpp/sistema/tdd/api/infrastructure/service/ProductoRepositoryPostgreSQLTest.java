package bo.gob.egpp.sistema.tdd.api.infrastructure.service;

import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import bo.gob.egpp.sistema.tdd.infrastructure.service.impl.ProductoRepositoryPostgreSQLImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductoRepositoryPostgreSQLTest {

    private final ProductoRepositoryPostgreSQLImpl sut = new ProductoRepositoryPostgreSQLImpl();

    @Test
    public void deberiaRegistrarProductoToBaseDeDatos() {

        final Producto producto = Producto.builder().build();

        sut.registrarProducto(producto);

        assertEquals(1, ProductoRepositoryPostgreSQLImpl.productos.size());

    }

}
