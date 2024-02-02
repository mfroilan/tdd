package bo.gob.egpp.sistema.tdd.infrastructure.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Producto {

    private int id;
    private ProductoData productoData;

}
