package buckpal.java.domain;

import buckpal.java.domain.ar.Account.AccountId;
import buckpal.java.domain.vo.ActivityWindow;
import buckpal.java.domain.vo.Money;
import static buckpal.java.testdata.ActivityTestData.defaultActivity;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ActivityWindowTest {

	@Test
	void calculatesStartTimestamp() {
		ActivityWindow window = new ActivityWindow(
				defaultActivity().withTimestamp(startDate()).build(),
				defaultActivity().withTimestamp(inBetweenDate()).build(),
				defaultActivity().withTimestamp(endDate()).build());

		assertEquals(startDate(), window.getStartTimestamp());
	}

	@Test
	void calculatesEndTimestamp() {
		ActivityWindow window = new ActivityWindow(
				defaultActivity().withTimestamp(startDate()).build(),
				defaultActivity().withTimestamp(inBetweenDate()).build(),
				defaultActivity().withTimestamp(endDate()).build());

		assertEquals(endDate(), window.getEndTimestamp());
	}

	@Test
	void calculatesBalance() {

		AccountId account1 = new AccountId(1L);
		AccountId account2 = new AccountId(2L);

		ActivityWindow window = new ActivityWindow(
				defaultActivity()
						.withSourceAccount(account1)
						.withTargetAccount(account2)
						.withMoney(Money.of(999)).build(),
				defaultActivity()
						.withSourceAccount(account1)
						.withTargetAccount(account2)
						.withMoney(Money.of(1)).build(),
				defaultActivity()
						.withSourceAccount(account2)
						.withTargetAccount(account1)
						.withMoney(Money.of(500)).build());

		assertEquals(Money.of(-500), window.calculateBalance(account1));
		assertEquals(Money.of(500), window.calculateBalance(account2));
	}

	private LocalDateTime startDate() {
		return LocalDateTime.of(2019, 8, 3, 0, 0);
	}

	private LocalDateTime inBetweenDate() {
		return LocalDateTime.of(2019, 8, 4, 0, 0);
	}

	private LocalDateTime endDate() {
		return LocalDateTime.of(2019, 8, 5, 0, 0);
	}

}