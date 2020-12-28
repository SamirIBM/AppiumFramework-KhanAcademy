package Practice.AppiumFramework;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofSeconds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;

public class TC_01 extends Capabilbity {
	AndroidDriver<AndroidElement> driver;

	@BeforeClass
	public void bt() throws IOException, InterruptedException {

		try {

			service = startserver();

			FileReader fis = new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\global.properties");

			Properties pro = new Properties();

			pro.load(fis);

			appPackage = pro.getProperty("appPackage");

			appActivity = pro.getProperty("appActivity");

			deviceName = pro.getProperty("deviceName");

			chromedriverExecutable = pro.getProperty("chromedriverExecutable");
			
			email=pro.getProperty("email");
			
			password=pro.getProperty("password");
			
			emailAddTeacher=pro.getProperty("email_add_teacher");

			DesiredCapabilities cap = new DesiredCapabilities();

			// cap.setCapability(MobileCapabilityType.APP, fp.getAbsolutePath());

			File fp = new File(System.getProperty("user.dir") + "\\src\\main\\java\\khan_academy.apk");

			cap.setCapability(MobileCapabilityType.APP, fp.getAbsolutePath());

			// cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
//			
			// cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);

			cap.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, chromedriverExecutable);

			cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);

			if (deviceName.contains("samir")) {

				startEmulator();
			}

			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");

			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);

			driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);

			// AndroidDriver<AndroidElement>

			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			Thread.sleep(5000);

			AndroidElement dismiss = driver.findElementByAndroidUIAutomator("new UiSelector().clickable(true)");

			if (dismiss.isDisplayed()) {

				dismiss.click();
			}

		}

		catch (Exception ex) {

			ex.printStackTrace();
			service.stop();
		}

	}

	@Test(priority = 1, enabled = true)
	public void sign_in() throws InterruptedException, IOException {

		try {

			Thread.sleep(2000);

			driver.findElementByAndroidUIAutomator("text(\"Sign in\")").click();

			Thread.sleep(2000);

			driver.findElementByAndroidUIAutomator("text(\"Sign in\")").click();

			driver.findElement(MobileBy.AccessibilityId("Enter an e-mail address or username"))
					.sendKeys(email);

			driver.findElement(MobileBy.AccessibilityId("Password")).sendKeys(password);

			Thread.sleep(2000);

			driver.findElement(MobileBy.AccessibilityId("Sign in")).click();

			Thread.sleep(3000);

			AndroidElement joinClass = driver.findElementByAndroidUIAutomator("text(\"Join class\")");

			if (joinClass.isDisplayed()) {

				System.out.println("Successfully Signed In");
			}

			else {

				System.out.println("Sign in Failed");

			}

		}

		catch (Exception ex) {

			ex.printStackTrace();
			service.stop();
		}
	}

	@Test(dependsOnMethods = { "sign_in" }, enabled = true, priority = 2)
	public void join_class() throws InterruptedException, IOException {

		try {

			Thread.sleep(6000);

			driver.findElementByAndroidUIAutomator("text(\"Join class\")").click();

			Thread.sleep(2000);

			driver.findElement(MobileBy.AccessibilityId("e.g. ABC123 or teacher@example.com"))
					.sendKeys(emailAddTeacher);

			Thread.sleep(2000);

			driver.hideKeyboard();

			Thread.sleep(5000);

			// driver.switchTo().frame(0);

			try {

//		System.out.println("No of elements"+driver.findElements(MobileBy.AndroidUIAutomator("UiSelector().text(\"ADD\")")).size());
//		
//		driver.findElement(MobileBy.AndroidUIAutomator("UiSelector().text(\"ADD\")")).click();
//		
//		Thread.sleep(2000);
//		
//		driver.findElement(MobileBy.AndroidUIAutomator("UiSelector().text(\"ADD\")")).click();

				driver.pressKey(new KeyEvent(AndroidKey.ENTER));

			}

			catch (Exception ex) {

				ex.printStackTrace();

				System.out.println("Unable to click on the add button");
			}
//		
//		 TouchAction t=new TouchAction(driver);
//		
//		 t.tap(tapOptions().withElement(element(add))).perform();

			// driver.findElements(MobileBy.className("android.widget.Button")).get(0).click();

			// System.out.println("NO of
			// elements"+driver.findElements(MobileBy.className("android.widget.Button")).size());

			Thread.sleep(4000);

			AndroidElement teacherAdded = driver.findElement(MobileBy.id("android:id/alertTitle"));

			if (teacherAdded.isDisplayed()) {

				System.out.println("Teacher has been successfully added to join the class");

				driver.switchTo().alert().accept();
			
			}

		}

		catch (Exception ex) {

			ex.printStackTrace();
			service.stop();
		}
	}

	@Test(dependsOnMethods = { "sign_in" }, enabled = true, priority = 3)
	public void terms_of_service() throws InterruptedException, IOException {

		try {

			Thread.sleep(3000);

			driver.findElement(MobileBy.AccessibilityId("Settings")).click();

			Thread.sleep(2000);

			driver.findElementByAndroidUIAutomator(
					"new UiScrollable(new UiSelector()).scrollIntoView(text(\"Licenses\"))").click();

			Thread.sleep(2000);

			driver.navigate().back();

			Thread.sleep(2000);

			driver.findElementByAndroidUIAutomator(
					"new UiScrollable(new UiSelector()).scrollIntoView(text(\"Terms of service\"))").click();
			// driver.switchTo().frame(0);

			Thread.sleep(16000);

			try {

				Set<String> contextNames = driver.getContextHandles();
				for (String contextName : contextNames) {
					System.out.println(contextName); // prints out something like NATIVE_APP \n WEBVIEW_1
				}

				try {
					driver.context("WEBVIEW_chrome");
					System.out.println("Successfully switched to web view");

				}

				catch (Exception ex) {

					ex.printStackTrace();
					System.out.println("Could not switch to web view");
				}

				Thread.sleep(4000);

				boolean webElementIsDisplayed = driver.findElement(By.xpath("//a[@aria-label='Khan Academy']"))
						.isDisplayed();

				if (webElementIsDisplayed) {

					System.out.println("The web element is displayed");
				}

			}

			catch (Exception ex) {

				ex.printStackTrace();

				System.out.println("Some Exception is there");

				// driver.quit();
			}

			Thread.sleep(2000);

			try {

				driver.context("NATIVE_APP");

				Thread.sleep(2000);

				System.out.println("Successfully switched to native view");

				driver.navigate().back();

				Thread.sleep(2000);

				driver.navigate().back();

			}

			catch (Exception ex) {

				ex.printStackTrace();

				System.out.println("Could not switch to native view");
			}

		}

		catch (Exception ex) {

			ex.printStackTrace();
			service.stop();
		}
	}

	@Test(dependsOnMethods = { "sign_in" }, enabled = true, priority = 4)
	public void manage_teachers() throws InterruptedException {

		try {

			Thread.sleep(3000);

			driver.findElement(MobileBy.AccessibilityId("Settings")).click();

			Thread.sleep(2000);

			driver.findElementByAndroidUIAutomator(
					"new UiScrollable(new UiSelector()).scrollIntoView(text(\"Manage teachers\"))").click();

			driver.findElements(MobileBy.AccessibilityId("Delete")).get(0).click();
			
			Thread.sleep(2000);

			driver.switchTo().alert().accept();
			
			Thread.sleep(2000);

			driver.navigate().back();
			
			Thread.sleep(2000);
			
			driver.navigate().back();
		}

		catch (Exception ex) {

			ex.printStackTrace();

			service.stop();
		}

	}

	@Test(dependsOnMethods = { "sign_in" }, priority = 5, enabled = true)
	public void search_course() throws InterruptedException {

		try {

		Thread.sleep(10000);

		AndroidElement dismiss = driver.findElementByAndroidUIAutomator("text(\"Dismiss\")");

		if (dismiss.isDisplayed()) {

			dismiss.click();
		}

		Thread.sleep(2000);

		driver.findElement(MobileBy.AccessibilityId("Search tab")).click();

		Thread.sleep(2000);

		driver.findElementByAndroidUIAutomator("text(\"Math\")").click();

		Thread.sleep(2000);

		driver.findElementByAndroidUIAutomator("text(\"Class 8 (Foundation) - Hindi\")").click();

		Thread.sleep(2000);

		TouchAction t = new TouchAction(driver);

		AndroidElement inetegersHindi = driver.findElementByAndroidUIAutomator("text(\"Integers (Hindi)\")");

		t.tap(tapOptions().withElement(element(inetegersHindi))).perform();

		Thread.sleep(2000);

		AndroidElement negativeNumbersHindi = driver
				.findElementByAndroidUIAutomator("text(\"Intro to negative numbers (Hindi)\")");

		if (negativeNumbersHindi.isDisplayed()) {

			System.out.println("Negative Numbers Hindi Module Video is Present");
		}

		else {

			System.out.println("Negative Numbers Hindi Module Video is not Present");
		}
		
		}
		
		catch(Exception ex) {
			
			ex.printStackTrace();
			service.stop();
		}

	}

	@AfterClass
	public void stop_server() {

		service.stop();
	}

}
