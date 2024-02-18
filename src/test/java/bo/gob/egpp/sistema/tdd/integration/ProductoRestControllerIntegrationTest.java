package bo.gob.egpp.sistema.tdd.integration;

import bo.gob.egpp.sistema.tdd.api.record.ProductoRecord;
import bo.gob.egpp.sistema.tdd.api.rest.ProductoRestController;
import bo.gob.egpp.sistema.tdd.infrastructure.repository.IProductoRestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@WebMvcTest(ProductoRestController.class)
@AutoConfigureMockMvc
public class ProductoRestControllerIntegrationTest {

    public final String URL_TEMPLATE = "/api/v1/productos";

    List<ProductoRecord> productos = new ArrayList<>();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IProductoRestRepository productoRestRepository;

    @BeforeEach
    public void inicializarProductos() {
        this.productos = List.of(
                new ProductoRecord(1, "Naranja", 2.38, "", 5000, null),
                new ProductoRecord(2, "Frutilla", 4.52, "", 5000, null)
        );
    }

    @Test
    public void deberiaEncontrarTodosLosProductos() throws Exception {

        String arrayEsperado = """
                [
                    {
                        "id": 1,
                        "nombre": "Naranja",
                        "precio": 2.38,
                        "clienteId": 5000,
                        "version": null
                    },
                    {
                        "id": 2,
                        "nombre": "Frutilla",
                        "precio": 4.52,
                        "clienteId": 5000,
                        "version": null
                    }
                ]
                """;

        when(this.productoRestRepository.findAll()).thenReturn(this.productos);

        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(content().json(arrayEsperado));


    }

    @Test
    public void deberiaCrearUnNuevoProductoCuandoUnProductoEsValido() throws Exception {

        var producto = new ProductoRecord(3, "Chirimoya", 9.82, "", 5000, null);

        when(this.productoRestRepository.save(producto)).thenReturn(producto);

        String precioFormateado = String.format(Locale.US, "%.2f", producto.precio());

        String productoEnviado = String.format("""
                {
                    "id": %d,
                    "nombre": "%s",
                    "precio": %s,
                    "clienteId": %d
                }
                """, producto.id(), producto.nombre(), precioFormateado, producto.clienteId());

        mockMvc
                .perform(post(URL_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productoEnviado))
                .andExpect(status().isCreated())
                .andExpect(content().json(productoEnviado));


    }

    @Test
    public void deberiaEncontrarElProductoCuandoExistanUnaIdentificacionValida() throws Exception {
        int identificador = 1;

        var producto = this.productos.get(0);

        String precioFormateado = String.format(Locale.US, "%.2f", producto.precio());

        String productoEsperado = String.format("""
                 {
                    "id": %d,
                    "nombre": %s,
                    "precio": %s,
                    "clienteId": %d,
                    "version": null
                 }
                """, producto.id(), producto.nombre(), precioFormateado, producto.clienteId());

        when(this.productoRestRepository.findById(identificador))
                .thenReturn(Optional.of(this.productos.get(0)));

        mockMvc.perform(get(URL_TEMPLATE + "/" + identificador))
                .andExpect(status().isOk())
                .andExpect(content().json(productoEsperado));

    }

    @Test
    public void deberiaActualizarUnProductoCuandoExistanUnIdentificadorValido() throws Exception {

        var productoActualizado = new ProductoRecord(1, "Super Naranaja", 6.69, "", 6000, 1);

        int identificadorValido = productoActualizado.id();

        when(this.productoRestRepository.findById(identificadorValido))
                .thenReturn(Optional.of(this.productos.get(0)));

        when(this.productoRestRepository.save(productoActualizado))
                .thenReturn(productoActualizado);

        String precioFormateado = String.format(Locale.US, "%.2f", productoActualizado.precio());

        String productoEnviadoBody = String.format("""
                {
                    "id": %d,
                    "nombre": "%s",
                    "precio": %s,
                    "clienteId": %d
                }
                """, productoActualizado.id(), productoActualizado.nombre(), precioFormateado, productoActualizado.clienteId());

        mockMvc
                .perform(put(URL_TEMPLATE + "/" + identificadorValido)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productoEnviadoBody))
                .andExpect(status().isOk());
    }

    @Test
    public void deberiaEliminarUnProductoCuandoExistanUnIdentificadorValido() throws Exception {
        int identificadorValido = 2;

        doNothing().when(this.productoRestRepository).deleteById(identificadorValido);

        mockMvc.perform(delete(URL_TEMPLATE + "/" + identificadorValido))
                .andExpect(status().isNoContent());

        verify(this.productoRestRepository, times(1)).deleteById(identificadorValido);

    }
}
