package buckpal.java.application;

import buckpal.java.application.outboundports.AccountLock;
import buckpal.java.domain.ar.Account.AccountId;

import jakarta.inject.Singleton;

@Singleton
class NoOpAccountLock implements AccountLock {

	@Override
	public void lockAccount(AccountId accountId) {
		// do nothing
	}

	@Override
	public void releaseAccount(AccountId accountId) {
		// do nothing
	}

}
