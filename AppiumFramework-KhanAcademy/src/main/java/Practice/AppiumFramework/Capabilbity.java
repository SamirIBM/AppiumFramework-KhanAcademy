package Practice.AppiumFramework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class Capabilbity {
	
	protected static String appPackage;
	
	protected static String appActivity;
	
	protected static String deviceName;
	
	protected static String chromedriverExecutable;
	
	public AppiumDriverLocalService service;
	
	public static String email="";
	
	public static String password="";
	
	public static String emailAddTeacher="";
	
	
	
	 //DesiredCapabilities cap=new DesiredCapabilities();
	
//	Capabilbity(){
//		
//		try {
//		
//		this.driver=new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
//		
//		}
//		
//		catch(Exception ex) {
//			
//			ex.printStackTrace();
//		}
//	}
	
	//To start my appium server through node js//this is given by appium server
	
	public AppiumDriverLocalService startserver()
	
	{
		boolean flag=checkifserverisRunning(4723);
		if(!flag) {
//		service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
//                .usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
//                .withAppiumJS(new File("C:\\Users\\SamirKhan\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
//                .withIPAddress("127.0.0.1").usingAnyFreePort());
			
		service=AppiumDriverLocalService.buildDefaultService();
		
		service.start();
		
		//return service;
		
		}
		return service;
		
	}
	
	@SuppressWarnings("resource")
	public static boolean checkifserverisRunning(int port) {
		
		boolean isserverrunning=false;
		
		ServerSocket serverSocket;
		
		
		try {
			
			serverSocket=new ServerSocket(port);
			//serverSocket.close();
			
			//serverSocket.
		}
		
		catch(Exception ex) {
			
			isserverrunning=true;
		}
		
		finally{
			
			serverSocket=null;
		}
		return isserverrunning;
		
		
	}
	
	public static void startEmulator() throws IOException, InterruptedException {
		
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\src\\main\\java\\Emulator.bat");
		
		Thread.sleep(8000);
	}
	

	public AndroidDriver<AndroidElement> capability(String appPackage, String appActivity, String deviceName, String chromedriverExecutable) throws IOException {
		// TODO Auto-generated method stub
		AndroidDriver<AndroidElement> driver = null;
		try {
		
		FileReader fis=new FileReader(System.getProperty("user.dir")+"\\src\\main\\java\\global.properties");
		
		Properties pro=new Properties();
		
		pro.load(fis);
		
		appPackage=pro.getProperty("appPackage");
		
		appActivity=pro.getProperty("appActivity");
		
		deviceName=pro.getProperty("deviceName");
		
		if(deviceName.contains("samir")) {
			
			startEmulator();
		}
		
		chromedriverExecutable=pro.getProperty("chromedriverExecutable");
		
		DesiredCapabilities cap=new DesiredCapabilities();
		
		//cap.setCapability(MobileCapabilityType.APP, fp.getAbsolutePath());
		
		   File fp=new File(System.getProperty("user.dir")+"\\src\\main\\java\\General-Store.apk");
		
		
		
		 cap.setCapability(MobileCapabilityType.APP, fp.getAbsolutePath());
		
		//cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
//		
		//cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
		
		cap.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, chromedriverExecutable);
		
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
		
		driver=new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
		
		service.stop();
		
		}
		
		catch(Exception ex) {
			
			ex.printStackTrace();
		}
		return driver;

	}

}
