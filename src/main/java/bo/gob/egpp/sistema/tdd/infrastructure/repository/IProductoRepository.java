package bo.gob.egpp.sistema.tdd.infrastructure.repository;

import bo.gob.egpp.sistema.tdd.infrastructure.domain.Producto;
import org.springframework.stereotype.Repository;

/**
 * Interfaz para el repositorio de productos, siguiendo el patrón de diseño de repositorio.
 * Define las operaciones de persistencia que se pueden realizar sobre las entidades Producto,
 * abstrayendo los detalles de implementación del acceso a datos. Esto facilita la flexibilidad
 * en la gestión de diferentes fuentes de datos y permite cambios en la implementación sin afectar
 * la lógica de negocio.
 * <p>
 * Funciona como una abstracción de una colección de objetos de dominio Producto, ofreciendo
 * una interfaz orientada a objetos para interactuar con la capa de persistencia.
 */
@Repository
// Anotación de Spring que indica que esta interfaz es un componente de repositorio, elegible para la creación de instancias y la inyección de dependencias por el contenedor de Spring.
public interface IProductoRepository {

    /**
     * Método para registrar un nuevo producto en la base de datos.
     * Se encarga de persistir la entidad Producto proporcionada, asegurando la correcta
     * almacenamiento de todos los atributos necesarios en la base de datos.
     *
     * @param producto La entidad Producto a registrar. Debe contener todos los datos necesarios
     *                 para su persistencia, como nombre, precio, etc.
     * @return La entidad Producto que ha sido registrada en la base de datos, posiblemente
     * modificada por el proceso de persistencia (como la asignación de un ID único).
     */
    Producto registrarProducto(Producto producto);

}