package buckpal.java.application.outboundports;

import buckpal.java.domain.ar.Account;

public interface AccountLock {

	void lockAccount(Account.AccountId accountId);

	void releaseAccount(Account.AccountId accountId);

}
