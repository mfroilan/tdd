package bo.gob.egpp.sistema.tdd.integration;


import bo.gob.egpp.sistema.tdd.api.vo.ProductoVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AplicacionIntegrationTest {

    public final String URL_TEMPLATE_API = "/api/productos";
    public final String CONTENT_TYPE = "application/json";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void deberiaGuardarUnProducto() throws Exception {
        ProductoVO productoVO = ProductoVO.builder().build();

        mockMvc.perform(post(URL_TEMPLATE_API)
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(productoVO)))
                .andExpect(status().isOk());

    }

    @Test
    public void deberiaEliminarUnProducto() throws Exception {
        final int identificadorProducto = 1;

        ProductoVO productoVO =
                ProductoVO.builder()
                        .id(identificadorProducto)
                        .build();

        mockMvc.perform(post(URL_TEMPLATE_API)
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(productoVO)));

        final int identificadorToEliminar = 1;

        mockMvc.perform(delete(URL_TEMPLATE_API + "/{id}", identificadorToEliminar)
                .contentType(CONTENT_TYPE))
                .andExpect(status().isOk());

    }

    @Test
    public void deberiaLlamarAlServicioParaEncontrarProducto() throws Exception {
        final int identificadorProducto = 1;

        ProductoVO productoVO =
                ProductoVO.builder()
                        .id(identificadorProducto)
                        .build();

        mockMvc.perform(post(URL_TEMPLATE_API)
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(productoVO)));

        mockMvc.perform(get(URL_TEMPLATE_API + "/{id}", identificadorProducto)
                .contentType(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(identificadorProducto));
    }

    @Test
    public void deberiaLlamarAlServicioParaListarProductos() throws Exception {
        mockMvc.perform(get(URL_TEMPLATE_API)
                .contentType(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

}
