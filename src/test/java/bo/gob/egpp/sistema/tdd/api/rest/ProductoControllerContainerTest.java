package bo.gob.egpp.sistema.tdd.api.rest;

import bo.gob.egpp.sistema.tdd.api.record.ProductoRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductoControllerContainerTest {

    public final String URL_API = "/api/v1/productos";

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    public TestRestTemplate restTemplate;

    @Test
    public void deberiaEstablecerConexionBD() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    public void deberiaEncontrarTodosLosProductos() {
        ProductoRecord[] productos
                = this.restTemplate.getForObject(URL_API, ProductoRecord[].class);
        assertThat(productos.length).isGreaterThanOrEqualTo(3);
    }

    @Test
    public void deberiaEcontrarUnProductoCuandoSeaValidoElID() {
        int productoID = 1;

        ResponseEntity<ProductoRecord> response
                = this.restTemplate.exchange(URL_API + "/" + productoID, HttpMethod.GET, null, ProductoRecord.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void noDeberiaEncontrarUnProductoCuandoExistanUnIdInvalido() {
        int productoID = 999;
        ResponseEntity<ProductoRecord> response
                = this.restTemplate.exchange(URL_API + "/" + productoID, HttpMethod.GET, null, ProductoRecord.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Rollback
    public void deberiaCrearUnNuevoProductoCuandoEnProductoEsValido() {
        ProductoRecord producto = new ProductoRecord(3, "Chirimoya", 9.82, 5000, 0);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductoRecord> request = new HttpEntity<>(producto, headers);

        ResponseEntity<ProductoRecord> response
                = this.restTemplate.exchange(URL_API, HttpMethod.POST, request, ProductoRecord.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assertThat(response.getBody()).isNotNull();

        assertThat(Objects.requireNonNull(response.getBody()).id()).isEqualTo(3);

        assertThat(response.getBody().nombre()).isEqualTo("Chirimoya");

        assertThat(response.getBody().precio()).isEqualTo(9.82);

        assertThat(response.getBody().clienteId()).isGreaterThanOrEqualTo(0);

    }

    @Test
    @Rollback
    public void noDeberiaCrearUnNuevoProductoCuandoLaValidacionDelProductoFalla() {
        ProductoRecord producto =
                new ProductoRecord(333, "", 3.5, 5000, 0);

        HttpHeaders encabezado = new HttpHeaders();
        encabezado.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductoRecord> request = new HttpEntity<>(producto, encabezado);
        ResponseEntity<ProductoRecord> response
                = this.restTemplate.exchange(URL_API, HttpMethod.POST, request, ProductoRecord.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Rollback
    void deberiaActualizarUnProductoCuandoElProductoEsValido() {
        int productoID = 2;
        ResponseEntity<ProductoRecord> response
                = this.restTemplate.exchange(URL_API + "/" + productoID, HttpMethod.GET, null, ProductoRecord.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ProductoRecord existeElProducto = response.getBody();

        assertThat(existeElProducto).isNotNull();

        ProductoRecord productoPorActualizar
                = new ProductoRecord(existeElProducto.id(), "Super Uva sin Pepas", existeElProducto.precio(), 8000, 1);

        assertThat(productoPorActualizar.id()).isEqualTo(productoID);

        assertThat(productoPorActualizar.nombre()).isEqualTo("Super Uva sin Pepas");
        assertThat(productoPorActualizar.precio()).isEqualTo(productoPorActualizar.precio());
        assertThat(productoPorActualizar.clienteId()).isEqualTo(productoPorActualizar.clienteId());
        assertThat(productoPorActualizar.version()).isEqualTo(productoPorActualizar.version());
    }

    @Test
    @Rollback
    void deberiaEliminarUnProductoCuandoExistaUnIDValido() {
        int productoID = 3;
        ResponseEntity<Void> response =
                this.restTemplate.exchange(URL_API + "/" + productoID, HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

}
