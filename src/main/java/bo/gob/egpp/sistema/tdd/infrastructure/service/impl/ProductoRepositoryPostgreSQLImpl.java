package bo.gob.egpp.sistema.tdd.infrastructure.service.impl;

import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import bo.gob.egpp.sistema.tdd.infrastructure.repository.IProductoRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación simulada de {@link IProductoRepository} para propósitos de demostración.
 * Aunque el nombre sugiere una implementación para PostgreSQL, esta versión utiliza una lista en memoria
 * para almacenar productos. No es adecuada para producción, pero puede ser útil para pruebas o demostraciones.
 */
public class ProductoRepositoryPostgreSQLImpl implements IProductoRepository {

    // Lista en memoria para simular el almacenamiento de productos.
    public static List<Producto> productos = new ArrayList<>();

    /**
     * Registra un producto simulando una base de datos en memoria.
     * En una implementación real, este método interactuaría con una base de datos PostgreSQL
     * para persistir el producto.
     *
     * @param producto El producto a registrar.
     * @return El producto registrado si la adición a la lista es exitosa, de lo contrario null.
     */
    @Override
    public Producto registrarProducto(Producto producto) {
        // Intenta agregar el producto a la lista en memoria. Si es exitoso, retorna el producto,
        // de lo contrario retorna null.
        if (productos.add(producto))
            return producto;

        return null;
    }

}