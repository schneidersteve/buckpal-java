package buckpal.java.outbound.adapter.persistence;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactorCrudRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import java.util.List;

@R2dbcRepository(dialect = Dialect.H2)
interface ActivityRepository extends ReactorCrudRepository<ActivityEntity, Long> {

	Flux<ActivityEntity> findByOwnerAccountIdEqualsAndTimestampGreaterThanEquals(
			Long ownerAccountId,
			LocalDateTime since);

	@Query("select sum(a.amount) from ActivityJpaEntity a " +
			"where a.targetAccountId = :accountId " +
			"and a.ownerAccountId = :accountId " +
			"and a.timestamp < :until")
	Mono<Long> getDepositBalanceUntil(
			Long accountId,
			LocalDateTime until);

	@Query("select sum(a.amount) from ActivityJpaEntity a " +
			"where a.sourceAccountId = :accountId " +
			"and a.ownerAccountId = :accountId " +
			"and a.timestamp < :until")
	Mono<Long> getWithdrawalBalanceUntil(
			Long accountId,
			LocalDateTime until);

}
