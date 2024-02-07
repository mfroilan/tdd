package bo.gob.egpp.sistema.tdd.api.record;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Table("productos")
public record ProductoRecord(
        @Id
        Integer id,
        @NotEmpty
        String nombre,
        Double precio,
        Integer clienteId,
        @Version
        Integer version
) {
}