package bo.gob.egpp.sistema.tdd.api.vo;

import lombok.*;

/**
 * Clase que representa un "Value Object" para un Producto en la aplicación.
 * Se utiliza para transferir datos de productos entre diferentes capas de la aplicación,
 * como la capa de presentación y la capa de servicio.
 * <p>
 * Las anotaciones de Lombok (@Getter, @Setter, @Builder, @NoArgsConstructor, @AllArgsConstructor)
 * automatizan la generación de código boilerplate, como métodos getter y setter para todas las propiedades,
 * un constructor sin argumentos, un constructor con todos los argumentos, y un patrón de constructor estilo builder.
 *
 * @Getter genera automáticamente métodos getter públicos para cada campo.
 * @Setter genera automáticamente métodos setter públicos para cada campo.
 * @Builder implementa el patrón de diseño "Builder" para la creación de instancias de ProductoVO.
 * @NoArgsConstructor genera un constructor sin argumentos.
 * @AllArgsConstructor genera un constructor que acepta un argumento para cada campo en la clase.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoVO {
    private int id; // Identificador único del producto.
    private String nombre; // Nombre descriptivo del producto.
    private double precio; // Precio unitario del producto.
}