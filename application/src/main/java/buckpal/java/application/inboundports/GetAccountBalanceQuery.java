package buckpal.java.application.inboundports;

import buckpal.java.domain.ar.Account.AccountId;
import buckpal.java.domain.vo.Money;

public interface GetAccountBalanceQuery {

	Money getAccountBalance(AccountId accountId);

}
