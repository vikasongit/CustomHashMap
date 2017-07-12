package com.alnt.map.controller;

import static org.junit.Assert.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author vikas.singla
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HashMapControllerTest {
	@InjectMocks
	HashMapController controller = new HashMapController();

	@Mock
	HttpServletRequest request;

	@Mock
	HttpSession session;

	@Test
	public void testController() {
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getId()).thenReturn("testSessionId");

		String token = controller.create(request);

		assertNotNull(token);

		Mockito.when(request.getHeader(Matchers.any(String.class))).thenReturn(
				token);
		controller.put(request, "key1", "123");
		controller.put(request, "key2", "456");
		controller.put(request, "key3", "789");

		assertEquals("123", controller.get(request, "key1"));
		assertEquals("456", controller.get(request, "key2"));
		assertEquals("789", controller.get(request, "key3"));

		controller.remove(request, "key2");

		assertEquals("123", controller.get(request, "key1"));
		assertNull(controller.get(request, "key2"));
		assertEquals("789", controller.get(request, "key3"));

		String token1 = controller.create(request);

		assertNotNull(token1);

		Mockito.when(request.getHeader(Matchers.any(String.class))).thenReturn(
				token1);
		controller.put(request, "FB", "1231");
		controller.put(request, "Ea", "4561");
		controller.put(request, "key31", "7891");
		controller.put(request, "FB", "269");

		assertEquals("269", controller.get(request, "FB"));
		assertEquals("4561", controller.get(request, "Ea"));
		assertEquals("7891", controller.get(request, "key31"));
		assertEquals("269", controller.get(request, "FB"));

		controller.remove(request, "Ea");

		assertEquals("269", controller.get(request, "FB"));
		assertNull(controller.get(request, "Ea"));
		assertEquals("7891", controller.get(request, "key31"));

	}

	@Test(expected = NullPointerException.class)
	public void testPut_NoToken() {
		Mockito.when(request.getHeader(Matchers.any(String.class))).thenReturn(
				null);
		controller.put(request, "key1", "123");

		assertNull(controller.get(request, "key1"));
	}

	@Test(expected = NullPointerException.class)
	public void testGet_NoToken() {
		Mockito.when(request.getHeader(Matchers.any(String.class))).thenReturn(
				null);

		assertNull(controller.get(request, "key1"));
	}

	@Test(expected = NullPointerException.class)
	public void testRemove_NoToken() {
		Mockito.when(request.getHeader(Matchers.any(String.class))).thenReturn(
				null);
		controller.remove(request, "key1");

		assertNull(controller.get(request, "key1"));
	}
}
