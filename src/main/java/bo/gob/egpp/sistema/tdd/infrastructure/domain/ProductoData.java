package bo.gob.egpp.sistema.tdd.infrastructure.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase que encapsula los datos esenciales de un producto.
 * Incluye propiedades como el nombre y precio del producto, que son comunes
 * a las operaciones relacionadas con productos en la aplicación.
 * <p>
 * Las anotaciones de Lombok simplifican la creación de código al generar automáticamente:
 * - Métodos getter para todas las propiedades, permitiendo la lectura de los valores de los campos.
 * - Métodos setter para todas las propiedades, permitiendo la modificación de los valores de los campos.
 * - Un patrón de constructor Builder, facilitando la creación de instancias de ProductoData de manera más clara y flexible.
 *
 * @Getter Genera automáticamente métodos getter públicos para cada campo.
 * @Setter Genera automáticamente métodos setter públicos para cada campo.
 * @Builder Implementa el patrón de diseño Builder, permitiendo la construcción de objetos ProductoData de manera fluida.
 */
@Getter
@Setter
@Builder
public class ProductoData {
    private String nombre; // Nombre descriptivo del producto.
    private double precio; // Precio unitario del producto.

    // Lombok generará automáticamente los métodos getter y setter, y el constructor estilo Builder.
}
