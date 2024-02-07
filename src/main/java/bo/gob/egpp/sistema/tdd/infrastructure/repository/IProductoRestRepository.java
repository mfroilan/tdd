package bo.gob.egpp.sistema.tdd.infrastructure.repository;

import bo.gob.egpp.sistema.tdd.api.record.ProductoRecord;
import org.springframework.data.repository.ListCrudRepository;

public interface IProductoRestRepository extends ListCrudRepository<ProductoRecord, Integer> {
}
