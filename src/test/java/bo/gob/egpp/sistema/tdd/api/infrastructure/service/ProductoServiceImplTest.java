package bo.gob.egpp.sistema.tdd.api.infrastructure.service;

import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import bo.gob.egpp.sistema.tdd.infrastructure.repository.IProductoRepository;
import bo.gob.egpp.sistema.tdd.infrastructure.service.IProductoService;
import bo.gob.egpp.sistema.tdd.infrastructure.service.impl.ProductoServiceImpl;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Pruebas unitarias para la clase ProductoServiceImpl.
 * Verifica el correcto funcionamiento de la lógica de negocio relacionada con los productos,
 * especialmente que las interacciones con el repositorio de productos se realicen correctamente.
 */
public class ProductoServiceImplTest {

    // Creación de un mock para IProductoRepository para simular su comportamiento
    // y aislar las pruebas de la capa de acceso a datos.
    private final IProductoRepository productoRepository = mock(IProductoRepository.class);

    // System Under Test (SUT): instancia de ProductoServiceImpl que se está probando.
    // Se inyecta el mock del repositorio para controlar el entorno de prueba.
    private final IProductoService sut = new ProductoServiceImpl(productoRepository);

    @Test // Anotación que indica que este método es una prueba unitaria.
    public void deberiaLlamarAlRepositorioParaGuardarProducto() {
        // Creación de una instancia de Producto usando el patrón Builder para el test.
        Producto producto = Producto.builder().build();

        // Ejecución del método agregarProducto del SUT con el producto de prueba.
        sut.agregarProducto(producto);

        // Verificación de que el método registrarProducto del repositorio fue invocado con el producto.
        // Esto asegura que la lógica de negocio para agregar un producto está correctamente delegada al repositorio.
        verify(this.productoRepository).registrarProducto(producto);
    }

}