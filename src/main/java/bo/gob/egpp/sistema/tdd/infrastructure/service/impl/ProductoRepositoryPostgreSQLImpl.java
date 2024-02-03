package bo.gob.egpp.sistema.tdd.infrastructure.service.impl;

import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import bo.gob.egpp.sistema.tdd.infrastructure.repository.IProductoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación simulada de {@link IProductoRepository} para propósitos de demostración.
 * Aunque el nombre sugiere una implementación para PostgreSQL, esta versión utiliza una lista en memoria
 * para almacenar productos. No es adecuada para producción, pero puede ser útil para pruebas o demostraciones.
 */
@Repository
public class ProductoRepositoryPostgreSQLImpl implements IProductoRepository {

    // Lista en memoria para simular el almacenamiento de productos.
    public static List<Producto> productos;

    public ProductoRepositoryPostgreSQLImpl() {
        this.productos = new ArrayList<>();
    }

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

    @Override
    public void removerProducto(int pIdentificadorToEliminar) {
        productos = productos.stream()
                .filter(product -> product.getId() != pIdentificadorToEliminar)
                .collect(Collectors.toList());
    }

    @Override
    public Producto encontrarProductoPorId(int pIdentificadorToBuscar) {
        List<Producto> productosBuscados;
        productosBuscados = productos.stream()
                .filter(product -> product.getId() == pIdentificadorToBuscar)
                .collect(Collectors.toList());
        return productosBuscados.get(0);
    }

    @Override
    public List<Producto> listarTodosLosProductos() {
        return productos;
    }

}