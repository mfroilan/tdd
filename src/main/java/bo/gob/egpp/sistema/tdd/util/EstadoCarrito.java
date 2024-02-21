package bo.gob.egpp.sistema.tdd.util;

import lombok.Getter;

@Getter
public enum EstadoCarrito {

    ACTIVO("Activo"),
    PAGADO("Pagado"),
    CANCELADO("Cancelado");

    private String nombre;

    EstadoCarrito(String nombre) {
        this.nombre = nombre;
    }

}
