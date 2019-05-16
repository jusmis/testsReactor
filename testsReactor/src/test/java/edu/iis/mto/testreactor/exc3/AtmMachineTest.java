package edu.iis.mto.testreactor.exc3;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class AtmMachineTest {

    /*@Mock
    private MoneyDepot moneyDepot;

    @Mock
    private BankService bankService;

    @Mock
    private CardProviderService cardProviderService;
    */

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


}
