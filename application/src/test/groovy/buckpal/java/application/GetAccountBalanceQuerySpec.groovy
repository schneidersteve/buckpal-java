package buckpal.java.application

import buckpal.java.application.outboundports.LoadAccountPort
import buckpal.java.application.inboundports.GetAccountBalanceQuery
import buckpal.java.application.queries.GetAccountBalanceQueryImpl
import buckpal.java.domain.ar.Account
import buckpal.java.domain.ar.Account.AccountId
import buckpal.java.domain.vo.Money

import spock.lang.Specification

class GetAccountBalanceQuerySpec extends Specification {

    LoadAccountPort loadAccountPort = Mock()

    GetAccountBalanceQuery getAccountBalanceQuery = new GetAccountBalanceQueryImpl(loadAccountPort)

    def "Succeeds"() {
        given: "a account"
            Account account = Mock()
            var accountId = new AccountId(41L)
            loadAccountPort.loadAccount(accountId, _) >> account

        when: "balance is queried"
            var balance = getAccountBalanceQuery.getAccountBalance(accountId)

        then: "balance is 500"
            balance == Money.of(500L)
        and: "account balance is 500"
            1 * account.calculateBalance() >> Money.of(500L)
    }
}
