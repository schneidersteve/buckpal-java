package buckpal.java.outbound.adapter.persistence;

import buckpal.java.application.outboundports.LoadAccountPort;
import buckpal.java.application.outboundports.UpdateAccountStatePort;
import buckpal.java.domain.ar.Account;
import buckpal.java.domain.ar.Account.AccountId;
import buckpal.java.domain.ar.Activity;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;

import jakarta.inject.Singleton;

@Singleton
@RequiredArgsConstructor
class AccountPersistenceAdapter implements
		LoadAccountPort,
		UpdateAccountStatePort {

	private final AccountRepository accountRepository;
	private final ActivityRepository activityRepository;
	private final AccountMapper accountMapper;

	@Override
	public Account loadAccount(
					AccountId accountId,
					LocalDateTime baselineDate) {

		var account =
				accountRepository.findById(accountId.value()).block();
						// .orElseThrow(EntityNotFoundException::new);

		var activities =
				activityRepository.findByOwnerAccountIdEqualsAndTimestampGreaterThanEquals(
						accountId.value(),
						baselineDate).collectList().block();

		var withdrawalBalance = orZero(activityRepository
				.getWithdrawalBalanceUntil(
						accountId.value(),
						baselineDate).block());

		var depositBalance = orZero(activityRepository
				.getDepositBalanceUntil(
						accountId.value(),
						baselineDate).block());

		return accountMapper.mapToDomainEntity(
				account,
				activities,
				withdrawalBalance,
				depositBalance);

	}

	private Long orZero(Long value){
		return value == null ? 0L : value;
	}


	@Override
	public void updateActivities(Account account) {
		for (Activity activity : account.getActivityWindow().getActivities()) {
			if (activity.getId() == null) {
				activityRepository.save(accountMapper.mapToJpaEntity(activity));
			}
		}
	}

}
