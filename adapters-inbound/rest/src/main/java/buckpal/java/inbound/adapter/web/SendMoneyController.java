package buckpal.java.inbound.adapter.web;

import buckpal.java.application.inboundports.SendMoneyUseCase.SendMoneyCommand;
import buckpal.java.application.inboundports.SendMoneyUseCase;
import buckpal.java.domain.ar.Account.AccountId;
import buckpal.java.domain.vo.Money;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;

import lombok.RequiredArgsConstructor;

@Controller("/accounts")
@RequiredArgsConstructor
public class SendMoneyController {
    private final SendMoneyUseCase sendMoneyUseCase;

    @Post("/send/{sourceAccountId}/{targetAccountId}/{amount}")
    void sendMoney(
            @PathVariable("sourceAccountId") Long sourceAccountId,
            @PathVariable("targetAccountId") Long targetAccountId,
            @PathVariable("amount") Long amount) {
        var command = new SendMoneyCommand(
                new AccountId(sourceAccountId),
                new AccountId(targetAccountId),
                Money.of(amount));

        sendMoneyUseCase.sendMoney(command);
    }

}
