package buckpal.java.outbound.adapter.persistence;

import io.micronaut.data.annotation.MappedEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@MappedEntity
record ActivityEntity(
	@Id @GeneratedValue Long id,
	LocalDateTime timestamp,
	Long ownerAccountId,
	Long sourceAccountId,
	Long targetAccountId,
	Long amount) {
}
