package bo.gob.egpp.sistema.tdd.infrastructure.repository;

import bo.gob.egpp.sistema.tdd.api.record.LineaCarritoRecord;
import org.springframework.data.repository.ListCrudRepository;

public interface ILineaCarritoRepository
        extends ListCrudRepository<LineaCarritoRecord, Integer> {
}
