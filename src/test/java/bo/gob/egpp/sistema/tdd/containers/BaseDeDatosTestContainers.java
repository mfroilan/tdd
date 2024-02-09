package bo.gob.egpp.sistema.tdd.containers;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class BaseDeDatosTestContainers {

    @Container
    public static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16.0")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @Test
    public void deberiaConectarseBdCrearTablaInsertarVerificar() throws Exception {
        String jdbcUrl = postgres.getJdbcUrl();
        String username = postgres.getUsername();
        String password = postgres.getPassword();

        String crearSqlTable = """
                CREATE TABLE IF NOT EXISTS productos
                (
                    id         INT PRIMARY KEY,
                    nombre     VARCHAR(50)      NOT NULL,
                    precio     DOUBLE PRECISION NOT NULL,
                    cliente_id INT              NOT NULL,
                    version    INT
                );
                """;

        String insertarSql = """
                INSERT INTO productos (id, nombre, precio, cliente_id, version) VALUES (1, 'Uvas', 7.81, 1000, 0);
                """;

        String verSql = """
                SELECT nombre
                FROM productos;
                """;

        try (Connection connection
                     = DriverManager.getConnection(jdbcUrl, username, password)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(crearSqlTable);
                statement.execute(insertarSql);

                try (ResultSet resultSet = statement.executeQuery(verSql)) {
                    while (resultSet.next()) {
                        String nombreProducto = resultSet.getString("nombre");
                        assertEquals("Uvas", nombreProducto, "El nombre del producto debe coincidir");
                    }
                }
            }
        }


    }

}
