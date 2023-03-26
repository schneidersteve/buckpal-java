package buckpal.java.application.commands;

import buckpal.java.domain.vo.Money;

import jakarta.inject.Singleton;

import lombok.Data;

/**
 * Configuration properties for money transfer use cases.
 */

@Singleton
@Data
class MoneyTransferProperties {
  private Money maximumTransferThreshold = Money.of(1_000_000L);
}

// record MoneyTransferProperties(Money maximumTransferThreshold) {
//   public MoneyTransferProperties(Money maximumTransferThreshold) {
//     if (maximumTransferThreshold != null) {
//       this.maximumTransferThreshold = maximumTransferThreshold;
//       return;
//     }
//     this.maximumTransferThreshold = Money.of(1_000_000L);
//   }
// }
