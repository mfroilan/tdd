package bo.gob.egpp.sistema.tdd.util;

import bo.gob.egpp.sistema.tdd.api.record.ProductosRecord;
import bo.gob.egpp.sistema.tdd.infrastructure.repository.IProductoRestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ProductoDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ProductoDataLoader.class);

    private final String PRODUCTOS_JSON = "/data/productos.json";

    private final ObjectMapper objectMapper;
    private final IProductoRestRepository productoRestRepository;

    public ProductoDataLoader(ObjectMapper objectMapper, IProductoRestRepository postRepository) {
        this.objectMapper = objectMapper;
        this.productoRestRepository = postRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (this.productoRestRepository.count() == 0) {
            log.info("Cargando productos en la base de datos desde el archivo JSON: {}", PRODUCTOS_JSON);

            try (InputStream inputStream = TypeReference.class.getResourceAsStream(PRODUCTOS_JSON)) {
                ProductosRecord response = this.objectMapper.readValue(inputStream, ProductosRecord.class);
                this.productoRestRepository.saveAll(response.productos());
            } catch (IOException e) {
                throw new RuntimeException("No se pudieron leer los datos del archivo JSON", e);
            }
        }

    }

}
