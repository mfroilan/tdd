package bo.gob.egpp.sistema.tdd.api.rest;

import bo.gob.egpp.sistema.tdd.api.converter.ProductoConverter;
import bo.gob.egpp.sistema.tdd.api.vo.ProductoVO;
import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import bo.gob.egpp.sistema.tdd.infrastructure.service.IProductoService;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductoControllerTest {

    private final ProductoConverter productoConverter = mock(ProductoConverter.class);
    private final IProductoService productoService = mock(IProductoService.class);

    private final ProductoController sut = new ProductoController(productoConverter, productoService);

    @Test
    public void deberiaLlamarAlServicioParaAgregarProductos() {

        ProductoVO productoVO = ProductoVO.builder().build();

        Producto producto = Producto.builder().build();

        when(this.productoConverter.convertirToVO(any())).thenReturn(productoVO);
        when(this.productoConverter.convertirToDomain(any())).thenReturn(producto);

        sut.agregarProductoEndpoint(productoVO);

        verify(this.productoService).agregarProducto(producto);

    }


}
