package buckpal.java.application.inboundports;

import buckpal.java.domain.ar.Account.AccountId;
import buckpal.java.domain.vo.Money;

public interface SendMoneyUseCase {

    boolean sendMoney(SendMoneyCommand command);

    // TODO implement reflection free validating
    public record SendMoneyCommand(AccountId sourceAccountId, AccountId targetAccountId, Money money) {
    }

}
