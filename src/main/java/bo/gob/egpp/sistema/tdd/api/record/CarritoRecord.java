package bo.gob.egpp.sistema.tdd.api.record;

import bo.gob.egpp.sistema.tdd.util.EstadoCarrito;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table("carritos")
public record CarritoRecord(
        @Id Integer id,
        Date fechaCreacion,
        EstadoCarrito estado,
        @Version
        Integer version
) {
}