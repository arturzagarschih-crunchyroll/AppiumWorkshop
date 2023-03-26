import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated
import org.openqa.selenium.support.ui.FluentWait
import java.net.URL
import java.time.Duration

class MigrationTest {
    private val driver = RemoteWebDriver(
        URL("http://127.0.0.1:4444"),
        DesiredCapabilities(
            mapOf(
                "browserName" to "chrome",
                "browserVersion" to "111.0",
                "goog:chromeOptions" to mapOf(
                    "mobileEmulation" to mapOf(
                        "deviceName" to "Pixel 5"
                    )
                )
            )
        )
    )

    @AfterEach
    fun tearDown() {
        driver.quit()
    }

    @Test
    fun playground() {
        // TODO: your code
    }

    @Test
    fun `Submit a request for a demo`() {
        // Open target
        driver.get("https://phptravels.com/demo")

        // Fill form
        driver.findElement(By.name("first_name")).sendKeys("foo")
        driver.findElement(By.name("last_name")).sendKeys("bar")
        driver.findElement(By.name("business_name")).sendKeys("foobar")
        driver.findElement(By.name("email")).sendKeys("foo.bar@foobar.com")

        // Solve captcha
        driver.executeScript(
            """
            const resultInput = document.querySelector('#number');
            const challengeElement = document.querySelector('.fcr h2');
            resultInput.value = eval(challengeElement.textContent.replace('=', ''));
            """
        )

        // Submit form
        val submit = driver.findElement(By.id("demo"))

        Actions(driver)
            .moveToElement(submit, submit.rect.width / -3, 0)
            .click()
            .perform()

        // Assert result
        FluentWait(driver)
            .withTimeout(Duration.ofSeconds(5))
            .withMessage("Completed sign is present")
            .until(visibilityOfElementLocated(By.className("completed")))
    }
}
