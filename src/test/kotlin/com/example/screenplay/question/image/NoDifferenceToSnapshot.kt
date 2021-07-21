package com.example.screenplay.question.image

import com.example.screenplay.action.image.ImageDifference
import com.example.screenplay.question.QuestionWithDefaultSubject
import net.serenitybdd.core.Serenity
import net.serenitybdd.screenplay.Actor
import java.nio.file.Path
import javax.imageio.IIOException
import javax.imageio.ImageIO

class NoDifferenceToSnapshot(private val snapshot: String) : QuestionWithDefaultSubject<Boolean>() {
  override fun answeredBy(actor: Actor): Boolean {
    val diff = actor.asksFor(ImageDifference.with(snapshot))
    val hasDiff = diff.hasDiff()
    if (hasDiff) {
      val snapshotPath = Path.of(this::class.java.getResource(".").toURI()).resolve("diffs")
      snapshotPath.toFile().mkdirs()
      try {
        val diffPath = snapshotPath.resolve(snapshot + "-diff.png")
        ImageIO.write(diff.markedImage, "png", diffPath.toFile())
        Serenity.recordReportData().withTitle("Image diff").downloadable().fromFile(diffPath)
      } catch (e: IIOException) {
        throw RuntimeException(e)
      }
    }
    return !hasDiff
  }

}
