package bo.gob.egpp.sistema.tdd.api.rest;

import bo.gob.egpp.sistema.tdd.api.converter.ProductoConverter;
import bo.gob.egpp.sistema.tdd.api.vo.ProductoVO;
import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import bo.gob.egpp.sistema.tdd.infrastructure.service.IProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador REST para gestionar productos.
 * Expone endpoints HTTP para realizar operaciones CRUD sobre productos, como agregar un nuevo producto.
 * Utiliza {@link ProductoConverter} para convertir entre la entidad Producto y su DTO, ProductoVO,
 * y {@link IProductoService} para manejar la lógica de negocio relacionada con los productos.
 */
@RestController // Marca la clase como un controlador REST.
@RequestMapping("/api/productos") // Define la ruta base para todos los endpoints en este controlador.
@AllArgsConstructor // Lombok genera automáticamente un constructor con todos los campos finales como argumentos.
public class ProductoController {

    private final ProductoConverter productoConverter; // Dependencia para convertir entre Producto y ProductoVO.
    private final IProductoService productoService; // Servicio que contiene la lógica de negocio para productos.

    /**
     * Endpoint para agregar un nuevo producto.
     * Recibe los datos del producto en formato JSON, los convierte a la entidad Producto,
     * invoca el servicio de productos para agregar el producto a la base de datos y retorna
     * el producto agregado en formato ProductoVO.
     *
     * @param productVO Los datos del producto a agregar, recibidos como un JSON en el cuerpo de la solicitud.
     * @return ResponseEntity que encapsula el ProductoVO agregado, con un estado HTTP adecuado.
     */
    @PostMapping // Marca este método para manejar solicitudes POST.
    public ResponseEntity<ProductoVO> agregarProductoEndpoint(@RequestBody final ProductoVO productVO) {
        final Producto producto = productoConverter.convertirToDomain(productVO); // Convierte de ProductoVO a Producto.
        Producto resultado = productoService.agregarProducto(producto); // Agrega el producto a través del servicio.
        return ResponseEntity.of(Optional.of(productoConverter.convertirToVO(resultado))); // Retorna el ProductoVO convertido con un estado HTTP 200 OK.
    }

    @DeleteMapping("/{id}")
    public void eliminarProductoEndpoint(@PathVariable(name = "id") final int pIdentificadorToEliminar) {
        this.productoService.eliminarProducto(pIdentificadorToEliminar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoVO> encontrarProductoPorIdEndpoint(@PathVariable(name = "id") final int pIdentificadorToBuscar) {
        Producto resultado = this.productoService.buscarProductoPorId(pIdentificadorToBuscar);
        return ResponseEntity.of(Optional.of(this.productoConverter.convertirToVO(resultado)));
    }

    @GetMapping
    public ResponseEntity<List<ProductoVO>> obtenerListaDeProductos() {

        List<Producto> productos = this.productoService.listarProductos();

        List<ProductoVO> productoVOS = productos.stream()
                .map(producto -> this.productoConverter.convertirToVO(producto))
                .collect(Collectors.toList());

        return ResponseEntity.of(Optional.of(productoVOS));

    }

}