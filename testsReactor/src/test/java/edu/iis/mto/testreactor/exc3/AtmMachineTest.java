package edu.iis.mto.testreactor.exc3;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

public class AtmMachineTest {

    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test(expected = WrongMoneyAmountException.class)
    public void withdrawingNegativeAmountOfMoneyShouldThrowWrongMoneyAmountException(){
        CardProviderService cardProviderService = Mockito.mock(CardProviderService.class);
        BankService bankService = Mockito.mock(BankService.class);
        MoneyDepot moneyDepot = Mockito.mock(MoneyDepot.class);

        AtmMachine atmMachine = new AtmMachine(cardProviderService, bankService, moneyDepot);
        Money money = Money.builder().withAmount(-1).withCurrency(Currency.PL).build();
        Card card = Card.builder().withCardNumber("0").withPinNumber(1).build();

        atmMachine.withdraw(money, card);
    }

    @Test(expected = WrongMoneyAmountException.class)
    public void withdrawingAmoutThatCantBePaidWithBanknotesShouldThrowWrongMoneyAmoutException(){
        CardProviderService cardProviderService = Mockito.mock(CardProviderService.class);
        BankService bankService = Mockito.mock(BankService.class);
        MoneyDepot moneyDepot = Mockito.mock(MoneyDepot.class);

        AtmMachine atmMachine = new AtmMachine(cardProviderService, bankService, moneyDepot);
        Money money = Money.builder().withAmount(1).withCurrency(Currency.PL).build();
        Card card = Card.builder().withCardNumber("0").withPinNumber(1).build();

        atmMachine.withdraw(money, card);
    }

    @Test(expected = CardAuthorizationException.class)
    public void withdrawingMoneyWithoutAuthenticationTokenShouldThrowCardAuthorizationException(){
        CardProviderService cardProviderService = Mockito.mock(CardProviderService.class);
        BankService bankService = Mockito.mock(BankService.class);
        MoneyDepot moneyDepot = Mockito.mock(MoneyDepot.class);

        Mockito.when(cardProviderService.authorize(Mockito.any(Card.class))).thenReturn(Optional.empty());

        AtmMachine atmMachine = new AtmMachine(cardProviderService, bankService, moneyDepot);
        Money money = Money.builder().withAmount(10).withCurrency(Currency.PL).build();
        Card card = Card.builder().withCardNumber("0").withPinNumber(1).build();

        atmMachine.withdraw(money, card);
    }

    @Test(expected = InsufficientFundsException.class)
    public void withdrawingMoneyFromAccountWithInsufficientFundsShouldThrowInsufficientFundsException(){
        CardProviderService cardProviderService = Mockito.mock(CardProviderService.class);
        BankService bankService = Mockito.mock(BankService.class);
        MoneyDepot moneyDepot = Mockito.mock(MoneyDepot.class);
        Mockito.when(bankService.charge(Mockito.any(AuthenticationToken.class), Mockito.any(Money.class))).thenReturn(false); //Simulate insufficient funds

        AtmMachine atmMachine = new AtmMachine(cardProviderService, bankService, moneyDepot);
        Money money = Money.builder().withAmount(10).withCurrency(Currency.PL).build();
        Card card = Card.builder().withCardNumber("0").withPinNumber(1).build();
        AuthenticationToken authenticationToken = AuthenticationToken.builder().withAuthorizationCode(1).withUserId("userId").build();

        Mockito.when(cardProviderService.authorize(Mockito.any(Card.class))).thenReturn(Optional.of(authenticationToken));

        atmMachine.withdraw(money, card);
    }





}




