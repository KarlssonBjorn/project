package tests.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import controller.OrderController;
import controller.ServiceController;


class ServiceControllerTest {
	
	ServiceController sc = new ServiceController();

	@Test
	void createServiceSuccessful() {
		assertEquals(sc.newService("companyName", "title", "description", 300), "Service saved successfully!");
	}
	
	@Test
	void createServiceFailDueToEmptyCompanyName() {
		assertEquals(sc.newService("companyName", "title", "description", 300), "Title is missing!");
	}
	
	@Test
	void createServiceFailDueToMulitpleErrors() {
		assertEquals(sc.newService("", "", "", 300), "Ops, something went wrong!"); 
	}
	
	@Test
	void editServiceSuccessful() {
		assertEquals(sc.editService("testSuccess", "title", "description", 300, 1), "Service Edited!");
	}
}
