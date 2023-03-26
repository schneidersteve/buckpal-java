package buckpal.java.inbound.adapter.web;

import buckpal.java.application.inboundports.GetAccountBalanceUseCase;
import buckpal.java.domain.ar.Account.AccountId;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Produces;

@Controller("/accounts")
public class GetAccountBalanceController {
    private final GetAccountBalanceUseCase getAccountBalanceQuery;

    public GetAccountBalanceController(GetAccountBalanceUseCase getAccountBalanceQuery) {
        this.getAccountBalanceQuery = getAccountBalanceQuery;
    }

    @Get("/{accountId}/balance")
    @Produces(MediaType.TEXT_PLAIN)
    Long getAccountBalance(
            @PathVariable("accountId") Long accountId) {
        return getAccountBalanceQuery.getAccountBalance(new AccountId(accountId)).getAmount().longValue();
    }

}
