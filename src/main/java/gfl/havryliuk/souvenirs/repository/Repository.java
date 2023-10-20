package gfl.havryliuk.souvenirs.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Repository<T>  {
    void save(final T item);
    List<T> getAll();
    Optional<T> getById(UUID id);
    void delete(UUID id);
}
