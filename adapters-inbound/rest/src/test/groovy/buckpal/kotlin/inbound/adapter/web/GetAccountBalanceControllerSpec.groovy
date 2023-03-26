package buckpal.kotlin.inbound.adapter.web

import buckpal.java.application.inboundports.GetAccountBalanceQuery
import buckpal.java.application.queries.GetAccountBalanceQueryImpl
import buckpal.java.domain.ar.Account.AccountId
import buckpal.java.domain.vo.Money

import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.spock.annotation.MicronautTest

import jakarta.inject.Inject

import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import static io.micronaut.http.HttpRequest.GET

@MicronautTest
class GetAccountBalanceControllerSpec extends Specification {

    @MockBean
    @Replaces(GetAccountBalanceQueryImpl)
    GetAccountBalanceQuery getAccountBalanceQuery = Mock()

    @Shared
    @AutoCleanup
    @Inject
    @Client("/accounts")
    HttpClient client

    def "test get balance"() {
        when:
            HttpResponse<String> response = client.toBlocking().exchange(GET("/41/balance"), String)

        then:
            response.status == HttpStatus.OK
        and:
            response.body() == "500"

        and:
            1 * getAccountBalanceQuery.getAccountBalance(new AccountId(41L)) >> Money.of(500L)

    }

}
