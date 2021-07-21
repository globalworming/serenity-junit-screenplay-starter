package com.example.screenplay.action.image

import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.Performable
import net.serenitybdd.screenplay.abilities.BrowseTheWeb
import org.openqa.selenium.By
import ru.yandex.qatools.ashot.AShot
import java.nio.file.Path
import javax.imageio.ImageIO

class CreateSnapshot(private val snapshotFile: String) : Performable {
  override fun <T : Actor?> performAs(actor: T) {
    val driver = BrowseTheWeb.`as`(actor).driver
    val screenShot = AShot().takeScreenshot(driver, driver.findElement(By.cssSelector("body")))
    val snapshotPath = Path.of(this::class.java.getResource(".").toURI()).resolve("snapshots")
    snapshotPath.toFile().mkdirs()
    ImageIO.write(screenShot.image, "png", snapshotPath.resolve(snapshotFile).toFile())
  }
}
