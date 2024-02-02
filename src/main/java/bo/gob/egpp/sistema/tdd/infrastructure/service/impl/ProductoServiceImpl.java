package bo.gob.egpp.sistema.tdd.infrastructure.service.impl;

import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import bo.gob.egpp.sistema.tdd.infrastructure.repository.IProductoRepository;
import bo.gob.egpp.sistema.tdd.infrastructure.service.IProductoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Servicio que implementa la lógica de negocio para operaciones relacionadas con productos.
 * Este servicio proporciona una capa de abstracción sobre el repositorio de productos,
 * permitiendo realizar operaciones de negocio adicionales antes o después del acceso a datos.
 *
 * @Service Anotación de Spring que marca esta clase como un componente de servicio,
 * permitiendo que Spring la gestione y la haga disponible para la inyección de dependencias.
 * @AllArgsConstructor Anotación de Lombok que genera un constructor con todos los campos como argumentos,
 * facilitando la inyección de dependencias por parte de Spring.
 */
@Service
@AllArgsConstructor
public class ProductoServiceImpl implements IProductoService {

    // Inyección del repositorio de productos, utilizado para interactuar con la capa de acceso a datos.
    private final IProductoRepository productoRepository;

    /**
     * Agrega un nuevo producto al sistema.
     * Delega la operación de persistencia al repositorio de productos y puede incluir
     * lógica de negocio adicional, como validaciones o transformaciones, antes o después
     * de la operación de registro en el repositorio.
     *
     * @param producto El producto a ser agregado al sistema.
     * @return El producto registrado, incluyendo cualquier modificación realizada durante el proceso de registro.
     */
    @Override
    public Producto agregarProducto(Producto producto) {
        // Delegación de la operación de registro al repositorio de productos.
        return this.productoRepository.registrarProducto(producto);
    }

}
