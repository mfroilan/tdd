package bo.gob.egpp.sistema.tdd.infrastructure.repository;

import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;

public interface IProductoRepository {

    Producto registrarProducto(Producto producto);

}
