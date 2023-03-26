package buckpal.java.application.commands;

import buckpal.java.domain.vo.Money;

import jakarta.inject.Singleton;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Configuration properties for money transfer use cases.
 */
@Singleton
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransferProperties {

  private Money maximumTransferThreshold = Money.of(1_000_000L);

}
