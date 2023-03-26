package buckpal.java.application.outboundports;

import buckpal.java.domain.ar.Account;
import buckpal.java.domain.ar.Account.AccountId;

import java.time.LocalDateTime;

public interface LoadAccountPort {

	Account loadAccount(AccountId accountId, LocalDateTime baselineDate);
}
