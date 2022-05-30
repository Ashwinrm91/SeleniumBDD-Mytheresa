package stepDefinitions;


import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import utils.TestUtils;

public class UseCaseMethod {

	WebDriver driver;
	String url;
	HttpURLConnection huc = null;
	int respCode = 200;
	
    private static ObjectMapper mapper = new ObjectMapper();
	//private static ResourceBundle resourceBundle = ResourceBundle.getBundle("application", Locale.ENGLISH);
    //private static ObjectMapper mapper = new ObjectMapper();

	@Given("user opens the browser")
	public void user_opens_browser() throws Exception {
		Properties prop = TestUtils.readPropertiesFile("Mytheresa.properties");

		if (prop.getProperty("browser").equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");

			driver = new ChromeDriver();

		}

		else if (prop.getProperty("browser").equals("firefox")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\Drivers\\geckodriver.exe");

			driver = new FirefoxDriver();
		}

		else if (prop.getProperty("browser").equals("ie")) {

			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\Drivers\\IEDriverServer.exe");

			driver = new InternetExplorerDriver();

		}

		else if (prop.getProperty("browser").equals("edge")) {

			System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "\\Drivers\\msedgedriver.exe");

			driver = new EdgeDriver();

		}
	}

	@Then("user loads the URL")
	public void user_loads_URL() throws Exception {

		driver.manage().window().maximize();
		Properties prop = TestUtils.readPropertiesFile("Mytheresa.properties");

		if (prop.getProperty("URL").equals("Staging")) {
			driver.get("https://staging.mytheresa.com/en-de/men.html");

		} else if (prop.getProperty("URL").equals("Local")) {
			driver.get("https://staging.mytheresa.com/en-de/men.html");

		} else if (prop.getProperty("URL").equals("Test")) {
			driver.get("https://staging.mytheresa.com/en-de/men.html");

		} else if (prop.getProperty("URL").equals("Production")) {
			driver.get("https://www.mytheresa.com/en-de/men.html");

		}

	}

	@Then("user validate Href links using status code")
	public void user_validate_Href_links() throws Exception {
		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("No of links are " + links.size());

		Iterator<WebElement> it = links.iterator();

		while (it.hasNext()) {

			url = it.next().getAttribute("href");

			System.out.println(url);

			if (url == null || url.isEmpty()) {
				System.out.println("URL is either not configured for anchor tag or it is empty");
				continue;
			}

			// handle Exception
			try {
				huc = (HttpURLConnection) (new URL(url).openConnection());

				huc.setRequestMethod("HEAD");

				huc.connect();

				respCode = huc.getResponseCode();

				System.out.println("Status code of the above is URL " + respCode);

			} catch (MalformedURLException e1) {
				System.out.println("URL is not wellformed" + url);

			}

		}

	}

	@Then("user enters login details")
	public void user_enters_login_detail() throws Exception {

		Thread.sleep(5000);
		Properties prop = TestUtils.readPropertiesFile("Mytheresa.properties");

		// Clicks My account
		driver.findElement(By.id("myaccount")).click();

		// Waits for the visibility of email input
		WebDriverWait wait = new WebDriverWait(driver, 30);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@id='qa-login-email']//input[contains(@type,'email')]")));

		driver.findElement(By.xpath("//div[@id='qa-login-email']//input[contains(@type,'email')]"))
				.sendKeys(prop.getProperty("email"));

		// Waits for the visibility of password input

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@id='qa-login-password']//input[contains(@type,'password')]")));

		driver.findElement(By.xpath("//div[@id='qa-login-password']//input[contains(@type,'password')]"))
				.sendKeys(prop.getProperty("password"));

		// Click submit button
		driver.findElement(By.xpath("//div[@id='qa-login-button']//button[contains(@type,'submit')]")).click();

		// Verifies the page after login
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='welcome-msg']")));
		Boolean accountname = driver.findElement(By.xpath("//p[@class='welcome-msg']")).isDisplayed();
		Assert.assertEquals(accountname, true);

	}
	
	@Then("user is getting pull request as CSV")
	public void user_is_getting_pull_request_as_CSV() throws Exception{
		Properties prop = TestUtils.readPropertiesFile("Mytheresa.properties");

	        try {
	            FileWriter fileWriter = new FileWriter("Pull Request\\pr.csv");
	            CSVWriter csvWriter = new CSVWriter(fileWriter);
//	            String authorization = String.format("token %s", resourceBundle.getString("GH_PERSONAL_ACCESS_TOKEN"));
	            String url = new StringBuilder().append("https://api.github.com/repos")
	                    .append(prop.getProperty("GH_REPO_PATH"))
	                    .append("/pulls").toString();
	            HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL(url).openConnection());
	            httpURLConnection.setRequestMethod("GET");
//	            httpURLConnection.setRequestProperty("Authorization", authorization);
	            httpURLConnection.connect();
	            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	                StringBuffer responseBuffer = new StringBuffer();
	                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
	                    String line;
	                    while ((line = bufferedReader.readLine()) != null) {
	                        responseBuffer.append(line);
	                    }
	                }
	                List<Map<String, Object>> responseList =
	                        mapper.readValue(responseBuffer.toString(), new TypeReference<List<Map<String, Object>>>() {});
	                for(Map<String, Object> pullRequest : responseList) {
	                    String[] line = {(String) pullRequest.get("title"), (String) ((Map)pullRequest.get("user")).get("login"), (String) pullRequest.get("created_at")};
	                    csvWriter.writeNext(line);
	                }
	                csvWriter.flush();
	            }
	        } catch (IOException exception) {
	            exception.printStackTrace();
	        }
	    }
	}


