package com.clearcapital.automation;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

public class Automation {

	@Test
	public void testAPI() {

		RestAssured.baseURI= "https://datausa.io";
		RequestSpecification httpRequest = RestAssured.given();
		Response res = httpRequest.queryParam("drilldowns","State").queryParam("measure", "Population")
				.queryParam("year", "latest").get("/api/data");

		Assert.assertNotNull(res.body());	
		Assert.assertEquals(res.body().asString().contains("data"), true);
		JsonPath jpath = new JsonPath(res.body().asString());

		int count = jpath.getInt("data.size()");
		Assert.assertNotEquals(count, 0);

		Set<Integer> years = new HashSet<Integer>();
		Set<String> states = new HashSet<String>();
		for(int i = 0; i < count; i++) {
			years.add(jpath.getInt("data["+i+"].Year"));
			states.add(jpath.getString("data["+i+"].State"));
		}

		//check there should only be 1 year
		Assert.assertEquals(years.size(), 1);

		//check there should only be unique state names
		Assert.assertEquals(states.size(), count);
	}

	@Test
	public void IkeaCCDemo() {
		System.setProperty("webdriver.chrome.driver", "/Users/namratanagaraja/Downloads/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		//open the website
		driver.get("https://www.ikea.com/us/en");

		// wait till the page load completes
		waitTillPageLoads(driver);

		//click on accept all cookies
		driver.findElement(By.cssSelector("button[id='onetrust-accept-btn-handler']")).click();

		//search for the word sofa
		WebElement search = driver.findElement(By.xpath("//input[@type='search']"));
		search.sendKeys("sofa" + "\n");

		//wait till the page load completes 
		waitTillPageLoads(driver);

		//add 1st element to cart
		List<WebElement> searchArr = driver.findElements(By.xpath("//div/button[contains(@id,'add_to_cart')]"));
		if (searchArr.size() > 0) {
			searchArr.get(0).click();
		}

		//wait till the page load completes
		waitTillPageLoads(driver);

		//clear the search box
		search = driver.findElement(By.xpath("//input[@type='search']"));
		while(!search.getAttribute("value").equals("")){
			search.sendKeys(Keys.BACK_SPACE);
		}

		//search for the word table
		search.sendKeys("table" + "\n");

		//wait till the page load completes 
		waitTillPageLoads(driver);

		//add 3rd element to cart
		List<WebElement> searchArrT = driver.findElements(By.xpath("//div/button[contains(@id,'add_to_cart')]"));
		if (searchArrT.size() > 0) {
			searchArrT.get(2).click();
		}
		
		//wait for 10 seconds for the add to cart banner to disappear
		try {
			Thread.sleep(java.time.Duration.ofSeconds(10).toMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//scroll to the top of the page
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");

		//click on the shopping cart
		driver.findElement(By.cssSelector("span[data-label-default='Shopping bag']")).click();

		//wait till the page load completes 
		waitTillPageLoads(driver);

		//find all the products on the page on the left side
		List<WebElement> prodP = driver.findElements(By.xpath("//li[contains(@class,  'product_name')]"));
		//check to make sure there are only 2 products
		Assert.assertEquals(prodP.size() ==2, true);
		
		//find the discount code link and click
		driver.findElement(By.xpath("//div[contains(text(),'Have a discount code?')]")).click();
		//wait till the page load completes 
		waitTillPageLoads(driver);
		//find the discount code textbox and enter the 15 character random code
		driver.findElement(By.id("discountCode")).sendKeys("123333333nnnnnnnnnnnddddddddddddd");
		//click on submit button
		driver.findElement(By.xpath("//button[@type='submit']//span[@class='cart-ingka-btn__inner']")).click();
		//wait till the page load completes 
		waitTillPageLoads(driver);
		
		//find the error message on the page
		String errTxt = driver.findElement(By.cssSelector(".cart-ingka-form-field__message")).getText();
		//check if there is an error message
		Assert.assertEquals(errTxt.contains("invalid"), true);
		//close the browser
		driver.close();
	}

	private void waitTillPageLoads(WebDriver driver) {
		// the wait here is only until the website dom loads the is ready
		ExpectedCondition<Boolean> expectation = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
			}
		};

		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(30));
			wait.until(expectation);
		} catch (Throwable error) {
			System.out.println("Timeout waiting for Page Load Request to complete.");
		}
	}
}
