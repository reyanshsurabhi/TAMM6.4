package com.selenium.module64.tests;

import static org.testng.Assert.assertEquals;
import com.selenium.module64.utility.SingletonDriverManager;
import com.selenium.module64.pages.FindFlightsPage;
import com.selenium.module64.pages.SummaryPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FindFlightsUsingSingltonPatternTest {

	public WebDriver driver;
	private String START_URL = "https://www.ryanair.com/gb/en/";
	public FindFlightsPage findFlightsPage;
	public SummaryPage summaryPage;	

	@BeforeClass
	public void openBrowser() {
		driver=SingletonDriverManager.getWebDriverInstance();		
		driver.manage().window().maximize();
		driver.get(START_URL);
		findFlightsPage = new FindFlightsPage(driver);
	}

	@Test
	public void verifyLandingPage() throws Exception {
		String ref = findFlightsPage.getLogoAttribute();
		assertEquals(ref, "https://www.ryanair.com/gb/en/", "Ryanair webpage is not loaded");
	}

	@Test
	public void selectAirportAndVerifyItAppeardInFrom() throws Exception {
		String fromFlightValue = findFlightsPage.getFromFlightText();
		Assert.assertEquals(fromFlightValue, "Brussels Charleroi", "The airport Brussels Charleroi is not selected");

		String germanStatus = findFlightsPage.getGermanCountryStatua();
		Assert.assertTrue(germanStatus.contains("unavailable"));
		
		String toAirportName = findFlightsPage.selectToAirport();
		Assert.assertEquals(toAirportName, "Manchester", "The airport Manchester is not selected");
	}
	
	@Test
	public void selectDateAndValidateSelectedTeen() throws Exception{
		String selectedPeople = findFlightsPage.selectDateAndTeen();
		Assert.assertTrue(selectedPeople.contains("1 Adult (age 16+), 1 others"), "Teen passenger is not added to list");
		summaryPage = findFlightsPage.clickOnSearch();				
	}

	@Test
	public void verifySearchPage() throws Exception{
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.ryanair.com/gb/en/booking/home", "The page is not redirected to correct path");
		String destination = summaryPage.getDestination();
		Assert.assertTrue(destination.contains("Manchester"));
		
		String seconddest = summaryPage.getArrival();
		System.out.println(seconddest);
		Assert.assertTrue(seconddest.contains("Brussels Charleroi"));
	}
	
	@AfterClass
	public void afterClass() {
		SingletonDriverManager.closeWebBrowser();
	}
	
	
}
