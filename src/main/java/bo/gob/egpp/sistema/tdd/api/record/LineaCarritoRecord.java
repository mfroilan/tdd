package bo.gob.egpp.sistema.tdd.api.record;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Table("lineas_carritos")
public record LineaCarritoRecord(
        @Id Long id,
        Integer carritoId,
        Integer productoId,
        Integer cantidad,
        Double precioUnitario,
        @Version
        Integer version
) {
}