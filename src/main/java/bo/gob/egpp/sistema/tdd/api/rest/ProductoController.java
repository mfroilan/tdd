package bo.gob.egpp.sistema.tdd.api.rest;

import bo.gob.egpp.sistema.tdd.api.converter.ProductoConverter;
import bo.gob.egpp.sistema.tdd.api.vo.ProductoVO;
import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import bo.gob.egpp.sistema.tdd.infrastructure.service.IProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@AllArgsConstructor
public class ProductoController {

    private final ProductoConverter productoConverter;

    private final IProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoVO> agregarProductoEndpoint(@RequestBody final ProductoVO productVO) {
        final Producto producto = this.productoConverter.convertirToDomain(productVO);

        Producto resultado = this.productoService.agregarProducto(producto);

        return ResponseEntity.of(Optional.of(this.productoConverter.convertirToVO(resultado)));

    }

}
