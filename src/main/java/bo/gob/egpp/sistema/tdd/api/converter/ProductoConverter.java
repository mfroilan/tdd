package bo.gob.egpp.sistema.tdd.api.converter;

import bo.gob.egpp.sistema.tdd.api.vo.ProductoVO;
import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import bo.gob.egpp.sistema.tdd.infrastructure.domain.ProductoData;
import org.springframework.stereotype.Component;

/**
 * Componente encargado de convertir entre la entidad de dominio Producto y su DTO, ProductoVO.
 * Esta clase facilita la transformación de datos entre la capa de persistencia (entidades)
 * y la capa de presentación o servicios (DTOs), asegurando que cada capa maneje el tipo de objeto adecuado.
 *
 * @Component Anotación de Spring que marca esta clase como un componente gestionado por el contenedor de Spring,
 * permitiendo su inyección automática en otras clases.
 */
@Component
public class ProductoConverter {

    /**
     * Convierte una entidad Producto a un ProductoVO.
     * Esta conversión permite exponer solo los datos necesarios en la capa de presentación o servicios,
     * ocultando detalles de la entidad de dominio que no deben ser expuestos.
     *
     * @param pProducto La entidad Producto a convertir.
     * @return Un ProductoVO que contiene los datos de pProducto.
     */
    public ProductoVO convertirToVO(Producto pProducto) {
        return ProductoVO.builder()
                .id(pProducto.getId())
                .nombre(pProducto.getProductoData().getNombre())
                .precio(pProducto.getProductoData().getPrecio())
                .build();
    }

    /**
     * Convierte un ProductoVO a una entidad Producto.
     * Esta conversión es útil para crear o actualizar entidades en la base de datos
     * a partir de datos recibidos en la capa de presentación o servicios.
     *
     * @param pProductVO El ProductoVO a convertir.
     * @return Un Producto que contiene los datos de pProductVO.
     */
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