package bo.gob.egpp.sistema.tdd.infrastructure.repository;

import bo.gob.egpp.sistema.tdd.api.record.CarritoRecord;
import org.springframework.data.repository.ListCrudRepository;

public interface ICarritoRestRepository
        extends ListCrudRepository<CarritoRecord, Integer> {
}
