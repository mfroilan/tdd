package bo.gob.egpp.sistema.tdd.infrastructure.service.impl;

import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import bo.gob.egpp.sistema.tdd.infrastructure.repository.IProductoRepository;
import bo.gob.egpp.sistema.tdd.infrastructure.service.IProductoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductoServiceImpl implements IProductoService {

    private final IProductoRepository productoRepository;

    @Override
    public Producto agregarProducto(Producto producto) {

        return this.productoRepository.registrarProducto(producto);

    }

}
