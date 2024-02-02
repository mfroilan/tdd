package bo.gob.egpp.sistema.tdd.api.infrastructure.service;

import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import bo.gob.egpp.sistema.tdd.infrastructure.repository.IProductoRepository;
import bo.gob.egpp.sistema.tdd.infrastructure.service.IProductoService;
import bo.gob.egpp.sistema.tdd.infrastructure.service.impl.ProductoServiceImpl;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ProductoServiceImplTest {

    private final IProductoRepository productoRepository = mock(IProductoRepository.class);

    private final IProductoService sut = new ProductoServiceImpl(productoRepository);

    @Test
    public void deberiaLlamarAlRepositorioParaGuardarProducto() {

        Producto producto = Producto.builder().build();

        sut.agregarProducto(producto);

        verify(this.productoRepository).registrarProducto(producto);

    }

}
