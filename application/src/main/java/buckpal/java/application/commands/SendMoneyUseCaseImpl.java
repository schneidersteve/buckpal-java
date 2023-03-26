package buckpal.java.application.commands;

import buckpal.java.application.inboundports.SendMoneyUseCase;
import buckpal.java.application.outboundports.AccountLock;
import buckpal.java.application.outboundports.LoadAccountPort;
import buckpal.java.application.outboundports.UpdateAccountStatePort;
import buckpal.java.domain.ar.Account;
import buckpal.java.domain.ar.Account.AccountId;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
public class SendMoneyUseCaseImpl implements SendMoneyUseCase {

	private final LoadAccountPort loadAccountPort;
	private final AccountLock accountLock;
	private final UpdateAccountStatePort updateAccountStatePort;
	private final MoneyTransferProperties moneyTransferProperties;

	@Override
	public boolean sendMoney(SendMoneyCommand command) {

		checkThreshold(command);

		LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

		Account sourceAccount = loadAccountPort.loadAccount(
				command.sourceAccountId(),
				baselineDate);

		Account targetAccount = loadAccountPort.loadAccount(
				command.targetAccountId(),
				baselineDate);

		AccountId sourceAccountId = sourceAccount.getId()
				.orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
		AccountId targetAccountId = targetAccount.getId()
				.orElseThrow(() -> new IllegalStateException("expected target account ID not to be empty"));

		accountLock.lockAccount(sourceAccountId);
		if (!sourceAccount.withdraw(command.money(), targetAccountId)) {
			accountLock.releaseAccount(sourceAccountId);
			return false;
		}

		accountLock.lockAccount(targetAccountId);
		if (!targetAccount.deposit(command.money(), sourceAccountId)) {
			accountLock.releaseAccount(sourceAccountId);
			accountLock.releaseAccount(targetAccountId);
			return false;
		}

		updateAccountStatePort.updateActivities(sourceAccount);
		updateAccountStatePort.updateActivities(targetAccount);

		accountLock.releaseAccount(sourceAccountId);
		accountLock.releaseAccount(targetAccountId);
		return true;
	}

	private void checkThreshold(SendMoneyCommand command) {
		if (command.money().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())) {
			throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(),
					command.money());
		}
	}

}
