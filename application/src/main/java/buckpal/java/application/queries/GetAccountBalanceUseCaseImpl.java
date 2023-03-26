package buckpal.java.application.queries;

import java.time.LocalDateTime;

import buckpal.java.application.inboundports.GetAccountBalanceUseCase;
import buckpal.java.application.outboundports.LoadAccountPort;
import buckpal.java.domain.ar.Account.AccountId;
import buckpal.java.domain.vo.Money;

import jakarta.inject.Singleton;

import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class GetAccountBalanceUseCaseImpl implements GetAccountBalanceUseCase {

	private final LoadAccountPort loadAccountPort;

	@Override
	public Money getAccountBalance(AccountId accountId) {
		return loadAccountPort.loadAccount(accountId, LocalDateTime.now())
				.calculateBalance();
	}
}
