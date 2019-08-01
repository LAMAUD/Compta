package com.clamaud.compta.compta;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.clamaud.compta.jpa.account.Account;
import com.clamaud.compta.jpa.account.Account.User;


@RunWith(Parameterized.class)
public class AccountTest {

	  
	@Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {     
                 { "ACHAT CB ESSFLOREALYG84 28.07.19 CARTE NUMERO                843", " ESSFLOREALYG84", User.CEDRIC.toString() },
                 { "CARTE X2843    27/07/19 A 12H32 RETRAIT DAB PLAN DE CAMPAGNE", "RETRAIT DAB PLAN DE CAMPAGNE", null },
                 {"VIREMENT DE MR  LAMAUD CEDRIC VIREMENT VERS DELBARD LAMAUD BP VIREMENT DE MR LAMAUD CEDRIC REFERENCE : 0190210900104040"," DE MR  LAMAUD CEDRIC VIREMENT VERS DELBARD LAMAUD BP VIREMENT DE MR LAMAUD CEDRIC REFERENCE ", null},
                 {"PRELEVEMENT DE EDF clients parti iers REF : Z016678271296 11408 1   SI 114"," DE EDF clients parti iers REF ", null},
                 {"REMISE COMMERCIALE D AGIOS"," D AGIOS", null},
                 {"MINIMUM FORFAITAIRE TRIMESTRIEL D UTILISATION DU DECOUVERT","", null},
                 {"AVANTAGE CREDIT IMMOBILIER SUR COTISATION FORMULE DE COMPTE","", null},
                 {"COTISATION TRIMESTRIELLE DE VOTRE FORMULE DE COMPTE","", null},
                 {"ACHAT CB DECATHLON ERES 28.06.19 CARTE NUMERO                154"," DECATHLON ERES", User.MATHILDE.toString()},
                 {"ACHAT CB BOULANGERIE PA 29.06.19 CARTE NUMERO                843"," BOULANGERIE PA", User.CEDRIC.toString()},
                 {"CHEQUE N  9445009"," N  9445009", null}
           });
    }
	
    private String label;
    private String codeExpected;
    private String userExpected;
    
    
	
	public AccountTest(String label, String codeResult, String userExpected) {
		this.label = label;
		this.codeExpected = codeResult;
		this.userExpected = userExpected;
	}



	@Test
	public void test_account() {
		
		Account account = new Account(new Date(), label, 45);
		System.out.println(String.format("%s ======> %s =====> %s", label, account.getCode(), account.getUser()));
		assertTrue(StringUtils.equals(account.getCode(), codeExpected));
		assertTrue(StringUtils.equals(account.getUser(), userExpected));
		
	}
	
	
	
}
