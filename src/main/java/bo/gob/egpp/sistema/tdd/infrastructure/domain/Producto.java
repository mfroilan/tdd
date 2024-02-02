package bo.gob.egpp.sistema.tdd.infrastructure.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase de dominio que representa un producto en la aplicación.
 * Contiene la información esencial del producto, incluyendo su identificador y los datos asociados
 * representados por la clase {@link ProductoData}.
 * <p>
 * Las anotaciones de Lombok simplifican el código al generar automáticamente:
 * - Métodos getter para todas las propiedades, permitiendo la lectura de los valores de los campos.
 * - Métodos setter para todas las propiedades, permitiendo la modificación de los valores de los campos.
 * - Un patrón de constructor Builder, facilitando la creación de instancias de Producto con un código más limpio y legible.
 *
 * @Getter Genera automáticamente métodos getter públicos para cada campo.
 * @Setter Genera automáticamente métodos setter públicos para cada campo.
 * @Builder Implementa el patrón de diseño Builder, permitiendo la construcción de objetos Producto de manera más flexible.
 */
@Getter
@Setter
@Builder
public class Producto {
    private int id; // Identificador único del producto.
    private ProductoData productoData; // Objeto que encapsula datos adicionales del producto, como nombre, precio, etc.

    // Lombok generará automáticamente los métodos getter y setter, además de un constructor estilo Builder.
}