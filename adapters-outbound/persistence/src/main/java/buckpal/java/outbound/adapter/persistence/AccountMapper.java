package buckpal.java.outbound.adapter.persistence;

import buckpal.java.domain.ar.Account;
import buckpal.java.domain.ar.Account.AccountId;
import buckpal.java.domain.ar.Activity;
import buckpal.java.domain.ar.Activity.ActivityId;
import buckpal.java.domain.vo.ActivityWindow;
import buckpal.java.domain.vo.Money;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Singleton;

@Singleton
class AccountMapper {

	Account mapToDomainEntity(
			AccountEntity account,
			List<ActivityEntity> activities,
			Long withdrawalBalance,
			Long depositBalance) {

		Money baselineBalance = Money.subtract(
				Money.of(depositBalance),
				Money.of(withdrawalBalance));

		return Account.withId(
				new AccountId(account.id()),
				baselineBalance,
				mapToActivityWindow(activities));

	}

	ActivityWindow mapToActivityWindow(List<ActivityEntity> activities) {
		List<Activity> mappedActivities = new ArrayList<>();

		for (ActivityEntity activity : activities) {
			mappedActivities.add(new Activity(
					new ActivityId(activity.id()),
					new AccountId(activity.ownerAccountId()),
					new AccountId(activity.sourceAccountId()),
					new AccountId(activity.targetAccountId()),
					activity.timestamp(),
					Money.of(activity.amount())));
		}

		return new ActivityWindow(mappedActivities);
	}

	ActivityEntity mapToJpaEntity(Activity activity) {
		return new ActivityEntity(
				activity.getId() == null ? null : activity.getId().value(),
				activity.getTimestamp(),
				activity.getOwnerAccountId().value(),
				activity.getSourceAccountId().value(),
				activity.getTargetAccountId().value(),
				activity.getMoney().getAmount().longValue());
	}

}
