package bo.gob.egpp.sistema.tdd.infrastructure.service;

import bo.gob.egpp.sistema.tdd.api.record.CarritoRecord;
import bo.gob.egpp.sistema.tdd.api.record.LineaCarritoRecord;
import bo.gob.egpp.sistema.tdd.infrastructure.repository.ICarritoRestRepository;
import bo.gob.egpp.sistema.tdd.infrastructure.repository.ILineaCarritoRepository;
import bo.gob.egpp.sistema.tdd.util.EstadoCarrito;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarritoService {

    private final ICarritoRestRepository carritoRestRepository;
    private final ILineaCarritoRepository lineaCarritoRepository;

    public CarritoRecord crearCarrito(CarritoRecord pCarrito) {
        return this.carritoRestRepository.save(pCarrito);
    }

    public void agregarLinea(Integer carritoId, LineaCarritoRecord linea) {
        this.carritoRestRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        this.lineaCarritoRepository.save(linea);
    }

    public void realizarPago(Integer carritoId) {
        CarritoRecord carrito = this.carritoRestRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        CarritoRecord carritoActualizado = new CarritoRecord(
                carrito.id(),
                carrito.fechaCreacion(),
                EstadoCarrito.PAGADO,
                carrito.version()
        );
        this.carritoRestRepository.save(carritoActualizado);
    }

}
