package buckpal.java.application.outboundports;

import buckpal.java.domain.ar.Account;

public interface UpdateAccountStatePort {

	void updateActivities(Account account);

}
