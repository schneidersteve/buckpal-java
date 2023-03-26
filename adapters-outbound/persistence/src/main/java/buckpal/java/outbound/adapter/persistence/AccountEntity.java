package buckpal.java.outbound.adapter.persistence;

import io.micronaut.data.annotation.MappedEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@MappedEntity
record AccountEntity(@Id @GeneratedValue Long id) {
}
