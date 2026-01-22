package es.upm.grise.profundizacion.subscriptionService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.upm.grise.profundizacion.exceptions.ExistingUserException;
import es.upm.grise.profundizacion.exceptions.LocalUserDoesNotHaveNullEmailException;
import es.upm.grise.profundizacion.exceptions.NullUserException;

public class SubscriptionServiceTest {
	
	private SubscriptionService subscriptionService;
	private User user;
	
	@BeforeEach
	public void setUp() {
		subscriptionService = new SubscriptionService();
		user = new User();
	}
	
	@Test
	public void testAddSubscriberWithNullUserThrowsException() {
		assertThrows(NullUserException.class, () -> {
			subscriptionService.addSubscriber(null);
		});
	}
	
	@Test
	public void testAddSubscriberWithValidUser() throws NullUserException, ExistingUserException, LocalUserDoesNotHaveNullEmailException {
		user.setEmail("ricardo@espe.com");
		user.setDelivery(Delivery.DO_NOT_DELIVER);
		
		subscriptionService.addSubscriber(user);
		
		assertTrue(subscriptionService.getSubscribers().contains(user));
		assertEquals(1, subscriptionService.getSubscribers().size());
	}
	
	@Test
	public void testAddExistingUserThrowsException() throws NullUserException, ExistingUserException, LocalUserDoesNotHaveNullEmailException {
		user.setEmail("ricardo@espe.com");
		user.setDelivery(Delivery.DO_NOT_DELIVER);
		
		subscriptionService.addSubscriber(user);
		
		assertThrows(ExistingUserException.class, () -> {
			subscriptionService.addSubscriber(user);
		});
	}
	
	@Test
	public void testAddLocalUserWithEmailThrowsException() {
		user.setEmail("ricardo@espe.com");
		user.setDelivery(Delivery.LOCAL);
		
		assertThrows(LocalUserDoesNotHaveNullEmailException.class, () -> {
			subscriptionService.addSubscriber(user);
		});
	}
}
