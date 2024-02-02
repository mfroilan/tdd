package bo.gob.egpp.sistema.tdd.api.vo;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoVO {

    private int id;
    private String nombre;
    private double precio;

}
