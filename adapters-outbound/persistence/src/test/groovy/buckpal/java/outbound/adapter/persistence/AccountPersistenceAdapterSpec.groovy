package buckpal.java.outbound.adapter.persistence

import buckpal.java.domain.ar.Account
import buckpal.java.domain.ar.Account.AccountId
import buckpal.java.domain.vo.ActivityWindow
import buckpal.java.domain.vo.Money

import io.micronaut.test.extensions.spock.annotation.MicronautTest

import jakarta.inject.Inject

import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


import static buckpal.java.testdata.AccountTestData.defaultAccount
import static buckpal.java.testdata.ActivityTestData.defaultActivity

@MicronautTest(transactional = false)
class AccountPersistenceAdapterSpec extends Specification {

    AccountRepository accountRepository = Mock()

    ActivityRepository activityRepository = Mock()

    AccountPersistenceAdapter adapterUnderTest

    @Shared
    @Inject
    AccountMapper accountMapper

    def setup() {
        adapterUnderTest = new AccountPersistenceAdapter(accountRepository, activityRepository, accountMapper)
    }

    def "loads Account"() {
        given:
            var accountId = new AccountId(1L)
            var baselineDate = LocalDateTime.of(2018, 8, 10, 0, 0)
            accountRepository.findById(accountId.value) >> Mono.just(new AccountEntity(1L))
            activityRepository.findByOwnerAccountIdEqualsAndTimestampGreaterThanEquals(accountId.value, baselineDate) >> Flux.just(
                new ActivityEntity(
                                    5,
                                    LocalDateTime.of(2019, 8, 9, 9, 0),
                                    1,
                                    1,
                                    2,
                                    1000
                            ),
                new ActivityEntity(
                                    7,
                                    LocalDateTime.of(2019, 8, 9, 10, 0),
                                    1,
                                    2,
                                    1,
                                    1000
                            )
            )
            activityRepository.getWithdrawalBalanceUntil(accountId.value, baselineDate) >> Mono.just(500L)
            activityRepository.getDepositBalanceUntil(accountId.value, baselineDate) >> Mono.just(1000L)
        when:
            Account account = adapterUnderTest.loadAccount(
                    accountId,
                    baselineDate
            )
        then:
            account.getActivityWindow().getActivities().size() == 2
        and:
            account.calculateBalance() == Money.of(500L)
    }

    def "updates Activities"() {
        given:
            Account account = defaultAccount()
                    .withBaselineBalance(Money.of(555L))
                    .withActivityWindow(new ActivityWindow(
                            defaultActivity()
                                    .withId(null)
                                    .withMoney(Money.of(1L))
                                    .build()))
                    .build()
        when:
            adapterUnderTest.updateActivities(account)
        then:
            1 * activityRepository.save(_)
    }

}
