package bo.gob.egpp.sistema.tdd.infrastructure.service;

import bo.gob.egpp.sistema.tdd.api.record.ProductoRecord;
import bo.gob.egpp.sistema.tdd.infrastructure.repository.IProductoRestRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class AlmacenamientoService {

    @Autowired
    private IProductoRestRepository productoRestRepository;

    private final Path rootLocation;

    public AlmacenamientoService(@Value("${storage.location}") String storageLocation) {
        this.rootLocation = Paths.get(storageLocation);

    }

    @PostConstruct
    public void inciar() {
        try {
            Files.createDirectories(this.rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar el almanenamiento", e);
        }
    }

    public String almacenarImagen(MultipartFile file, Integer productoId) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Archivo vacio " + filename);
            }

            if (filename.contains("..")) {
                throw new RuntimeException("No se puede almacenar archivo con ruta relativa fuera del directorio actual " + filename);
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename));
            }

            // Buscar al producto por ID y actualizar su ruta de imagen
            ProductoRecord producto = this.productoRestRepository.findById(productoId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));

            // Crea una instancia de producto
            ProductoRecord productoActualizado = new ProductoRecord(
                    producto.id(),
                    producto.nombre(),
                    producto.precio(),
                    rootLocation.resolve(filename).toString(),
                    producto.clienteId(),
                    producto.version()
            );

            this.productoRestRepository.save(productoActualizado);

            return filename;

        } catch (IOException e) {
            throw new RuntimeException("Hubo un fallo al almacenar el archivo " + filename, e);
        }
    }

    public Resource cargarComoRecurso(String filename) {
        try {
            Path file = this.rootLocation.resolve(filename).normalize(); // Resulve la ruta del archivo
            Resource resource = new UrlResource(file.toUri()); // Crear un recurso a partir de la URI del archivo

            if (resource.exists() ||
                    resource.isReadable()
            ) {
                return resource; // Si el recurso existe y es legible, entonces lo devulve
            } else {
                throw new RuntimeException("No se pudo leer el archivo: " + filename);
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("Hubo un error al cargar el archivo: " + filename, e);
        }
    }

}
