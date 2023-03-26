package buckpal.java.outbound.adapter.persistence;

import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;

@R2dbcRepository(dialect = Dialect.H2)
interface AccountRepository extends ReactorCrudRepository<AccountEntity, Long> {
}
