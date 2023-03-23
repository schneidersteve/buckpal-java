package buckpal.java.domain;

import buckpal.java.domain.ar.Account;
import buckpal.java.domain.ar.Account.AccountId;
import buckpal.java.domain.vo.ActivityWindow;
import buckpal.java.domain.vo.Money;
import static buckpal.java.testdata.AccountTestData.defaultAccount;
import static buckpal.java.testdata.ActivityTestData.defaultActivity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class AccountTest {

	@Test
	void calculatesBalance() {
		AccountId accountId = new AccountId(1L);
		Account account = defaultAccount()
				.withAccountId(accountId)
				.withBaselineBalance(Money.of(555L))
				.withActivityWindow(new ActivityWindow(
						defaultActivity()
								.withTargetAccount(accountId)
								.withMoney(Money.of(999L)).build(),
						defaultActivity()
								.withTargetAccount(accountId)
								.withMoney(Money.of(1L)).build()))
				.build();

		Money balance = account.calculateBalance();

		assertEquals(Money.of(1555L), balance);
	}

	@Test
	void withdrawalSucceeds() {
		AccountId accountId = new AccountId(1L);
		Account account = defaultAccount()
				.withAccountId(accountId)
				.withBaselineBalance(Money.of(555L))
				.withActivityWindow(new ActivityWindow(
						defaultActivity()
								.withTargetAccount(accountId)
								.withMoney(Money.of(999L)).build(),
						defaultActivity()
								.withTargetAccount(accountId)
								.withMoney(Money.of(1L)).build()))
				.build();

		boolean success = account.withdraw(Money.of(555L), new AccountId(99L));

		assertTrue(success);
		assertEquals(3, account.getActivityWindow().getActivities().size());
		assertEquals(Money.of(1000L), account.calculateBalance());
	}

	@Test
	void withdrawalFailure() {
		AccountId accountId = new AccountId(1L);
		Account account = defaultAccount()
				.withAccountId(accountId)
				.withBaselineBalance(Money.of(555L))
				.withActivityWindow(new ActivityWindow(
						defaultActivity()
								.withTargetAccount(accountId)
								.withMoney(Money.of(999L)).build(),
						defaultActivity()
								.withTargetAccount(accountId)
								.withMoney(Money.of(1L)).build()))
				.build();

		boolean success = account.withdraw(Money.of(1556L), new AccountId(99L));

		assertFalse(success);
		assertEquals(2, account.getActivityWindow().getActivities().size());
		assertEquals(Money.of(1555L), account.calculateBalance());
	}

	@Test
	void depositSuccess() {
		AccountId accountId = new AccountId(1L);
		Account account = defaultAccount()
				.withAccountId(accountId)
				.withBaselineBalance(Money.of(555L))
				.withActivityWindow(new ActivityWindow(
						defaultActivity()
								.withTargetAccount(accountId)
								.withMoney(Money.of(999L)).build(),
						defaultActivity()
								.withTargetAccount(accountId)
								.withMoney(Money.of(1L)).build()))
				.build();

		boolean success = account.deposit(Money.of(445L), new AccountId(99L));

		assert (success);
		assertEquals(3, account.getActivityWindow().getActivities().size());
		assertEquals(Money.of(2000L), account.calculateBalance());
	}

}