package buckpal.kotlin.inbound.adapter.web

import buckpal.java.application.inboundports.SendMoneyUseCase.SendMoneyCommand
import buckpal.java.application.inboundports.SendMoneyUseCase
import buckpal.java.application.commands.SendMoneyUseCaseImpl
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

import static io.micronaut.http.HttpRequest.POST

@MicronautTest
class SendMoneyControllerSpec extends Specification {

    @MockBean
    @Replaces(SendMoneyUseCaseImpl)
    SendMoneyUseCase sendMoneyUseCase = Mock()

    @Shared
    @AutoCleanup
    @Inject
    @Client("/accounts")
    HttpClient client

    def "test send money"() {
        when:
            HttpResponse response = client.toBlocking().exchange(POST("/send/41/42/500", ""))

        then:
            response.status == HttpStatus.OK

        and:
            // Kotlin suspend function is extended by one more parameter at compile time
            1 * sendMoneyUseCase.sendMoney(
                    new SendMoneyCommand(
                            new AccountId(41L),
                            new AccountId(42L),
                            Money.of(500L))
                        )
    }

}
