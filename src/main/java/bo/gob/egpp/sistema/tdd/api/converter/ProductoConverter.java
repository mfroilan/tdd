package bo.gob.egpp.sistema.tdd.api.converter;

import bo.gob.egpp.sistema.tdd.api.vo.ProductoVO;
import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import bo.gob.egpp.sistema.tdd.infrastructure.domain.ProductoData;
import org.springframework.stereotype.Component;

@Component
public class ProductoConverter {

    public ProductoVO convertirToVO(Producto pProducto) {

        return ProductoVO.builder()
                .id(pProducto.getId())
                .nombre(pProducto.getProductoData().getNombre())
                .precio(pProducto.getProductoData().getPrecio())
                .build();

    }

    public Producto convertirToDomain(ProductoVO pProductVO) {
        final ProductoData productData = ProductoData.builder()
                .nombre(pProductVO.getNombre())
                .precio(pProductVO.getPrecio())
                .build();

        return Producto.builder()
                .id(pProductVO.getId())
                .productoData(productData)
                .build();
    }

}
