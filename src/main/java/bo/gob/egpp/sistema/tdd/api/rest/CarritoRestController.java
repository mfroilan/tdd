package bo.gob.egpp.sistema.tdd.api.rest;

import bo.gob.egpp.sistema.tdd.api.record.CarritoRecord;
import bo.gob.egpp.sistema.tdd.api.record.LineaCarritoRecord;
import bo.gob.egpp.sistema.tdd.infrastructure.service.CarritoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carritos")
@AllArgsConstructor
public class CarritoRestController {

    private final CarritoService carritoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarritoRecord crearCarrito(@RequestBody CarritoRecord carrito) {
        return this.carritoService.crearCarrito(carrito);
    }

    @PostMapping("/{id}/agregar-linea")
    public ResponseEntity<?> agregarLinea(
            @PathVariable(name = "id") Integer carritoId,
            @RequestBody LineaCarritoRecord linea
    ) {
        try {
            this.carritoService.agregarLinea(carritoId, linea);
            return ResponseEntity.ok().body("El registro del detalle ha sido creado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("No se pudo crear el registro, debido a: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/realizar-pago")
    public ResponseEntity<?> pagar(@PathVariable(name = "id") Integer carritoId) {
        try {
            this.carritoService.realizarPago(carritoId);
            return ResponseEntity.ok().body("El pago ha sido realizado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Hubo un error al realizar el pago, debido a: " + e.getMessage());
        }
    }

}
