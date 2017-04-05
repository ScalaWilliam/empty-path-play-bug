package controllers

import java.nio.file.Paths
import javax.inject.{Inject, Singleton}

import play.api.http.FileMimeTypes
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext

/**
  * Created by me on 06/04/2017.
  */
@Singleton
class Main @Inject()()(implicit executionContext: ExecutionContext,
                       fileMimeTypes: FileMimeTypes)
    extends Controller {
  def getFile = Action {
    Ok.sendPath(Paths.get(Main.SampleFilename))
  }
}

object Main {
  val SampleFilename = "test.txt"
}
