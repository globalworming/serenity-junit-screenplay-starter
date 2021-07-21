package com.example.screenplay.action.image

import com.example.screenplay.question.QuestionWithDefaultSubject
import com.example.screenplay.question.image.WeWantToCreateSnapshots
import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.abilities.BrowseTheWeb
import net.serenitybdd.screenplay.conditions.Check
import org.openqa.selenium.By
import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.comparison.ImageDiff
import ru.yandex.qatools.ashot.comparison.ImageDiffer
import java.awt.image.BufferedImage
import java.nio.file.Path
import javax.imageio.IIOException
import javax.imageio.ImageIO


open class ImageDifference(private val snapshotFile: String?) : QuestionWithDefaultSubject<ImageDiff>() {


  override fun answeredBy(actor: Actor): ImageDiff {
    actor.attemptsTo(Check.whether(WeWantToCreateSnapshots()).andIfSo(CreateSnapshot(snapshotFile!!)))
    val expectedImage: BufferedImage = try {
      ImageIO.read(Path.of(this::class.java.getResource(".").toURI()).resolve("snapshots").resolve(snapshotFile).toFile())
    } catch (e: IIOException) {
      throw RuntimeException(e)
    }
    val driver = BrowseTheWeb.`as`(actor).driver
    val screenShot = AShot().takeScreenshot(driver, driver.findElement(By.cssSelector("body"))).image
    val imgdiffer = ImageDiffer()
    val imgdiff = imgdiffer.makeDiff(expectedImage, screenShot)
    return imgdiff
  }

  companion object {
    fun with(snapshot: String): ImageDifference = ImageDifference(snapshot)

  }

}

