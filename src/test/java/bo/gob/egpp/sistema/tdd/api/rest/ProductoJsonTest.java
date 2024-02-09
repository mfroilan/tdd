package bo.gob.egpp.sistema.tdd.api.rest;

import bo.gob.egpp.sistema.tdd.api.record.ProductoRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.io.IOException;
import java.util.Locale;

@JsonTest
public class ProductoJsonTest {

    @Autowired
    private JacksonTester<ProductoRecord> jacksonTester;


    @BeforeEach
    public void iniciar() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void deberiaSerializarUnProducto() throws IOException {
        var producto = new ProductoRecord(2025, "Papaya del Futuro", 12.3, 6000, 1);

        String precioFormateado = String.format(Locale.US, "%.2f", producto.precio());

        String productoEsperadoBody = String.format("""
                {
                    "id": %d,
                    "nombre": "%s",
                    "precio": %s,
                    "clienteId": %d
                }
                """, producto.id(), producto.nombre(), precioFormateado, producto.clienteId());

        assertThat(this.jacksonTester.write(producto)).isEqualToJson(productoEsperadoBody);
    }

    @Test
    public void deberiaDeserializarUnProducto() throws IOException {

        var producto = new ProductoRecord(2024, "Papaya Dulce", 12.3, 6000, 1);
        String precioFormateado = String.format(Locale.US, "%.2f", producto.precio());

        String productoBody = String.format("""
                {
                    "id": %d,
                    "nombre": "%s",
                    "precio": %s,
                    "clienteId": %d,
                    "version": %d
                }
                """, producto.id(), producto.nombre(), precioFormateado, producto.clienteId(), producto.version());

        assertThat(this.jacksonTester.parse(productoBody)).isEqualTo(producto);
        assertThat(this.jacksonTester.parseObject(productoBody).id()).isEqualTo(2024);
        assertThat(this.jacksonTester.parseObject(productoBody).nombre()).isEqualTo("Papaya Dulce");
        assertThat(this.jacksonTester.parseObject(productoBody).precio()).isEqualTo(12.3);
        assertThat(this.jacksonTester.parseObject(productoBody).clienteId()).isEqualTo(6000);

    }


}
