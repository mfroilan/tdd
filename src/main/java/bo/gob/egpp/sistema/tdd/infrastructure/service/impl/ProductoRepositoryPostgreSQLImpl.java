package bo.gob.egpp.sistema.tdd.infrastructure.service.impl;

import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import bo.gob.egpp.sistema.tdd.infrastructure.repository.IProductoRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryPostgreSQLImpl implements IProductoRepository {

    public static List<Producto> productos = new ArrayList<>();

    @Override
    public Producto registrarProducto(Producto producto) {

        if (productos.add(producto))
            return producto;

        return null;

    }

}
