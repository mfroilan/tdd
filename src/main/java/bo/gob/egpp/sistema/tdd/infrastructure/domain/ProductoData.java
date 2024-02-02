package bo.gob.egpp.sistema.tdd.infrastructure.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductoData {

    private String nombre;
    private double precio;

}
