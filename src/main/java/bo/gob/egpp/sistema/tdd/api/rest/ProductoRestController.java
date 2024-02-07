package bo.gob.egpp.sistema.tdd.api.rest;

import bo.gob.egpp.sistema.tdd.api.record.ProductoRecord;
import bo.gob.egpp.sistema.tdd.exception.ProductoNotFoundException;
import bo.gob.egpp.sistema.tdd.infrastructure.repository.IProductoRestRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/productos")
@AllArgsConstructor
public class ProductoRestController {

    private final IProductoRestRepository productoRestRepository;

    // GET
    @GetMapping
    public List<ProductoRecord> encontrarTodos() {
        return this.productoRestRepository.findAll();
    }

    // POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoRecord guardar(@RequestBody @Valid ProductoRecord producto) {
        return this.productoRestRepository.save(producto);
    }

    // GET
    @GetMapping("/{id}")
    public Optional<ProductoRecord> encontrarPorId(@PathVariable(name = "id") Integer productoId) {
        return Optional.ofNullable(
                this.productoRestRepository.findById(productoId)
                        .orElseThrow(ProductoNotFoundException::new)
        );
    }

    // PUT
    @PutMapping("/{id}")
    public ProductoRecord actualizar(@PathVariable Integer id, @RequestBody @Valid ProductoRecord producto) {

        Optional<ProductoRecord> existe = this.productoRestRepository.findById(id);

        if (existe.isPresent()) {
            ProductoRecord productoPorActualizar = new ProductoRecord(
                    existe.get().id(),
                    producto.nombre(),
                    producto.precio(),
                    existe.get().clienteId(),
                    existe.get().version()
            );
            return this.productoRestRepository.save(productoPorActualizar);
        } else {
            throw new ProductoNotFoundException();
        }

    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void elminar(@PathVariable Integer id) {
        this.productoRestRepository.deleteById(id);
    }

    @PatchMapping("/{id}")
    public ProductoRecord actualizarPrecio(@PathVariable Integer id, @RequestBody @Valid ProductoRecord producto) {

        Optional<ProductoRecord> existe = this.productoRestRepository.findById(id);

        if (existe.isPresent()) {

            ProductoRecord productoPorActualizar = new ProductoRecord(
                    existe.get().id(),
                    producto.nombre(),
                    existe.get().precio(),
                    existe.get().clienteId(),
                    existe.get().version()
            );

            return this.productoRestRepository.save(productoPorActualizar);
        } else {
            throw new ProductoNotFoundException();
        }

    }

}
