import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class tech_blog {

	public static void main(String[] args) {

		// declaration and instantiation of objects
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\nazte\\Downloads\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		// launch the web site
		driver.navigate().to("https://techcrunch.com/");

		// verify if each news have author
		List<WebElement> listOfAuthors = driver.findElements(By.className("river-byline__authors"));
		for (WebElement news : listOfAuthors) {
			if (listOfAuthors.size() != 0) {
				System.out.println("Author is present");
			} else {
				System.out.println("Author is not present");
			}
		}

		// verify if first news have image
		List<WebElement> listOfImages = driver.findElements(By.cssSelector("#tc-main-content .river--homepage article img"));
		for (WebElement news : listOfAuthors) {
			if (listOfImages.size() != 0) {
				System.out.println("Image is present");
			} else {
				System.out.println("Image is not present");
			}
		}

		// find and click to the first news
		driver.findElement(By.cssSelector("#tc-main-content .river--homepage article > header > h2 > a")).click();

		// verify the browser title is the same with the news title
		WebElement newsTitle = driver.findElement(By.cssSelector("h1"));
		System.out.println(newsTitle.getText());

		boolean windowTitle = driver.getTitle().equals(newsTitle.getText() + " | TechCrunch") ? true : false;
		System.out.println(windowTitle);

		// verify the links within the news content
		String url = driver.getCurrentUrl();

		HttpURLConnection huc = null;
		int respCode = 200;

		List<WebElement> links = driver.findElements(By.tagName("a"));
		Iterator<WebElement> it = links.iterator();

		while (it.hasNext()) {

			url = it.next().getAttribute("href");

			System.out.println(url);

			if (url == null || url.isEmpty()) {
				System.out.println("URL is either not configured for anchor tag or it is empty");
				continue;
			}

			try {
				huc = (HttpURLConnection) (new URL(url).openConnection());

				huc.setRequestMethod("HEAD");

				huc.connect();

				respCode = huc.getResponseCode();

				if (respCode >= 400) {
					System.out.println(url + " is a broken link");
				} else {
					System.out.println(url + " is a valid link");
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
