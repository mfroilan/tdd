package bo.gob.egpp.sistema.tdd.infrastructure.service;

import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;

import java.util.List;

/**
 * Interfaz para el servicio de productos.
 * Define las operaciones de alto nivel que se pueden realizar sobre los productos dentro de la aplicación.
 * Este servicio encapsula la lógica de negocio relacionada con los productos, proporcionando una abstracción
 * clara entre la capa de presentación y la capa de acceso a datos.
 */
public interface IProductoService {

    /**
     * Agrega un nuevo producto al sistema.
     * Esta operación puede incluir lógica de negocio adicional, como validaciones,
     * transformaciones de datos, o acciones adicionales que deben realizarse cuando se agrega un producto.
     *
     * @param producto El producto a agregar al sistema. Debe contener la información necesaria
     *                 para crear un nuevo registro de producto en la base de datos.
     * @return El producto agregado, potencialmente con modificaciones adicionales realizadas durante
     * el proceso de agregación, como la asignación de un identificador único.
     */
    Producto agregarProducto(Producto producto);

    void eliminarProducto(int pIdentificadorToEliminar);

    Producto buscarProductoPorId(int pIdentificadorToBuscar);

    List<Producto> listarProductos();

}