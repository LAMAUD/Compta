package com.clamaud.compta.compta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.junit.Test;

import com.clamaud.compta.jpa.account.Category;
import com.clamaud.compta.jpa.account.User;

public class StreamTest {

	
	@Test
	@Ignore
	public void test_streamAndLambdaFilter() {
		
		List<User> users = new ArrayList<User>();
		
		User ced = new User("LAMAUD", "Cédric", 29);
		User mathilde = new User("DELBARD", "Mathilde", 32);
		User nico = new User("LANDRA", "Nicolas", 30);
		User fab = new User("BORDES", "Fabrice", 27);
		User nono = new User("DERUE", "Arnaud", 28);
		
		users.add(nono);
		users.add(fab);
		users.add(nico);
		users.add(mathilde);
		users.add(ced);
		
		List<User> userOlderThan29 = users
				.stream()
				.filter(u -> u.getAge() > 29)
				.collect(Collectors.toList());
		
		List<User> userOlderOrEqualThan30 = users
				.stream()
				.filter(User::hasMoreThan30YearsOld)
				.collect(Collectors.toList());
		
		for (User user : userOlderThan29) {
			System.out.println(user.getFirstName());
		}
		
		for (User user : userOlderOrEqualThan30) {
			System.out.println(user.getFirstName());
		}
		
	}
	
	@Test
	@Ignore
	public void test_streamAndLambdaExpressionFilterDoublePredicate() {
		
	List<User> users = new ArrayList<User>();
		
		User ced = new User("LAMAUD", "Cédric", 29);
		User mathilde = new User("DELBARD", "Mathilde", 32);
		User nico = new User("LANDRA", "Nicolas", 30);
		User fab = new User("BORDES", "Fabrice", 27);
		User nono = new User("DERUE", "Arnaud", 28);
		
		users.add(nono);
		users.add(fab);
		users.add(nico);
		users.add(mathilde);
		users.add(ced);
		
     List<User> containAandIinFirstName = users
    		 .stream()
    		 .filter(u -> u.getFirstName().contains("a") &&  u.getFirstName().contains("i"))
    		 .collect(Collectors.toList());
		
     for (User user : containAandIinFirstName) {
			System.out.println(user.getFirstName());
		}
	}
	
	@Test
	@Ignore
	public void test_StreamAnyMatch() {
		
		List<User> users = new ArrayList<User>();
		
		User ced = new User("LAMAUD", "Cédric", 29);
		User mathilde = new User("DELBARD", "Mathilde", 32);
		User nico = new User("LANDRA", "Nicolas", 30);
		User fab = new User("BORDES", "Fabrice", 27);
		User nono = new User("DERUE", "Arnaud", 28);
		
		users.add(nono);
		users.add(fab);
		users.add(nico);
		users.add(mathilde);
		users.add(ced);
		
		boolean isUser32 = users.stream().anyMatch(u -> u.getAge() == 33);
		System.out.println(isUser32);
		
	}
	
	@Test
	@Ignore
	public void test_StreamMapping() {
		
		List<User> users = new ArrayList<User>();
		
		User ced = new User("LAMAUD", "Cédric", 29);
		User mathilde = new User("DELBARD", "Mathilde", 32);
		User nico = new User("LANDRA", "Nicolas", 30);
		User fab = new User("BORDES", "Fabrice", 27);
		User nono = new User("DERUE", "Arnaud", 28);
		
		users.add(nono);
		users.add(fab);
		users.add(nico);
		users.add(mathilde);
		users.add(ced);
		
		List<String> names = users.stream().map(u -> u.getName()).collect(Collectors.toList());
		names.stream().forEach(n -> System.out.println(n));
		
	}
	
	@Test
	public void test_countOccurencesInList() {
		List<Category> categories = new ArrayList<>();
		categories.add(Category.CHAT);
		categories.add(Category.ESPECE);
		categories.add(Category.ESPECE);
		categories.add(Category.FRAIS_BANCAIRE);
		categories.add(Category.ESPECE);
		categories.add(Category.CHAT);
		
		Map<Category, Long> mapCategories = categories
				.stream()
				.collect(Collectors.groupingBy(c -> c, Collectors.counting()));
		
		
		Category category = mapCategories.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
		
		System.out.println(category);
		
	}
	
	
	
}
