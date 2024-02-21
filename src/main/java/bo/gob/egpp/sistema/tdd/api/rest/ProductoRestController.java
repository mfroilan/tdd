package bo.gob.egpp.sistema.tdd.api.rest;

import bo.gob.egpp.sistema.tdd.api.record.ProductoRecord;
import bo.gob.egpp.sistema.tdd.exception.ProductoNotFoundException;
import bo.gob.egpp.sistema.tdd.infrastructure.repository.IProductoRestRepository;
import bo.gob.egpp.sistema.tdd.infrastructure.service.AlmacenamientoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/productos")
@AllArgsConstructor
public class ProductoRestController {

    private final IProductoRestRepository productoRestRepository;
    private final AlmacenamientoService almacenamientoService;

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
                    existe.get().rutaImagenProducto(),
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
                    existe.get().rutaImagenProducto(),
                    existe.get().clienteId(),
                    existe.get().version()
            );

            return this.productoRestRepository.save(productoPorActualizar);
        } else {
            throw new ProductoNotFoundException();
        }

    }

    @PostMapping("/{id}/cargar")
    public ResponseEntity<?> cargarImagen(
            @PathVariable(name = "id") Integer productoId,
            @RequestParam("image") MultipartFile file) {

        if (!Arrays.asList("image/jpeg", "image/png", "image/gif").contains(file.getContentType())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato de archivo no soportado");
        }

        try {
            this.productoRestRepository.findById(productoId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

            // Llama al servicio de almacenamietno para guardar la imagen y actualizar la ruta en el producto
            String filename = this.almacenamientoService.almacenarImagen(file, productoId);

            // Contruye una respuesta que incluya la ruta de la imagen guardada
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{filename}")
                    .buildAndExpand(filename)
                    .toUri();

            return ResponseEntity.created(location).body("La imagen fue cargada correctamente: " + filename);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hubo un error al subir la imagen: " + e.getMessage());
        }

    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String filename) {
        try {
            Resource file = this.almacenamientoService.cargarComoRecurso(filename);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/form")
    public ResponseEntity<?> crearProducto(
            @RequestPart("datos") @Valid String datosProducto,
            @RequestPart("imagen") MultipartFile imagen
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductoRecord producto = objectMapper.readValue(datosProducto, ProductoRecord.class);

        if (!Arrays.asList("image/jpeg", "image/png", "image/gif").contains(imagen.getContentType())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato de archivo no soportado");
        }

        try {
            var productoGuardado = this.productoRestRepository.save(producto);
            this.productoRestRepository.findById(productoGuardado.id())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

            var nombreArchivo = this.almacenamientoService.almacenarImagen(imagen, productoGuardado.id());

            ProductoRecord productoResponse = new ProductoRecord(
                    productoGuardado.id(),
                    productoGuardado.nombre(),
                    productoGuardado.precio(),
                    nombreArchivo,
                    productoGuardado.clienteId(),
                    productoGuardado.version()
            );

            return new ResponseEntity<>(productoResponse, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hubo un error al subir la imagen: " + e.getMessage());
        }
    }

}

