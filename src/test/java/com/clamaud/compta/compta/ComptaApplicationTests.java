package com.clamaud.compta.compta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import com.clamaud.compta.jpa.account.Account;
import com.clamaud.compta.jpa.service.ImportService;

@RunWith(Parameterized.class)
@SpringBootTest
public class ComptaApplicationTests {

	@ClassRule
	public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();
	
	@Autowired
	private ImportService importService;
	
	
	private String filePath;
	private int sizeList;
	private double value;
	
	public ComptaApplicationTests(String filePath, int sizeList, double value) {
		this.filePath = filePath;
		this.sizeList = sizeList;
		this.value = value;
	}
	
	@Parameters
	public static Collection data(){
	  return Arrays.asList(new Object[][]{
		  {"src/test/resources/Avril.csv", 1, 100},
		  {"src/test/resources/Mai.csv", 2, 200}
	  });
	}
	
	
	@Test
	public void test_fileIsReadCorrectly() throws NumberFormatException, FileNotFoundException, IOException, ParseException {
		
		File file = new File(filePath);
		List<Account> accountsFromFile = importService.getAccountsFromFile(file);
		assertTrue(accountsFromFile.size() == sizeList);
		assertEquals(accountsFromFile.get(0).getAmount(), value, 0.001);
		
	}
	@Test(expected=FileNotFoundException.class)
	public void test_fileNotFoundException() throws NumberFormatException, FileNotFoundException, IOException, ParseException {
		
		File file = new File("resources/Mai.csv");
		List<Account> accountsFromFile = importService.getAccountsFromFile(file);
		
	}

}
