package bo.gob.egpp.sistema.tdd.api.rest;

import bo.gob.egpp.sistema.tdd.api.converter.ProductoConverter;
import bo.gob.egpp.sistema.tdd.api.vo.ProductoVO;
import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import bo.gob.egpp.sistema.tdd.infrastructure.service.IProductoService;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Clase de prueba para ProductoController.
 * Utiliza mocks para simular las dependencias de ProductoConverter e IProductoService,
 * permitiendo probar el controlador en aislamiento.
 * <p>
 * Se enfoca en probar la funcionalidad de agregar productos, asegurándose de que el controlador:
 * 1. Convierte el ProductoVO a la entidad Producto usando ProductoConverter.
 * 2. Invoca el método adecuado en IProductoService para agregar el producto al sistema.
 * <p>
 * Esto ayuda a verificar que el flujo de agregar un producto a través del controlador se ejecuta correctamente,
 * y que las interacciones con las dependencias clave se realizan como se espera.
 */
public class ProductoControllerTest {

    // Mocks de las dependencias que el controlador necesita.
    // ProductoConverter se usa para convertir entre objetos de dominio y objetos de valor (VO).
    // IProductoService es la interfaz del servicio que maneja la lógica de negocio para productos.
    private final ProductoConverter productoConverter = mock(ProductoConverter.class);
    private final IProductoService productoService = mock(IProductoService.class);

    // System Under Test (sut) es una instancia del controlador que se está probando.
    // Se inyectan los mocks como dependencias para aislar la prueba del resto del sistema.
    private final ProductoController sut = new ProductoController(productoConverter, productoService);

    @Test // Anotación que indica que este método es una prueba unitaria.
    public void deberiaLlamarAlServicioParaAgregarProductos() {
        // Creación de instancias de prueba para ProductoVO y Producto usando el patrón Builder.
        ProductoVO productoVO = ProductoVO.builder().build();
        Producto producto = Producto.builder().build();

        // Configuración de comportamientos simulados (mocks).
        // Cuando se llame a convertirToVO en el convertidor, se retornará productoVO.
        // Cuando se llame a convertirToDomain, se retornará producto.
        when(this.productoConverter.convertirToVO(any())).thenReturn(productoVO);
        when(this.productoConverter.convertirToDomain(any())).thenReturn(producto);

        // Ejecución del método bajo prueba en el controlador, pasando el ProductoVO.
        sut.agregarProductoEndpoint(productoVO);

        // Verificación de que el servicio de productos fue llamado con el objeto Producto correcto.
        // Esto asegura que el flujo de agregar un producto funciona como se espera.
        verify(this.productoService).agregarProducto(producto);
    }

}