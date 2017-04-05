package controllers

import java.nio.file.Paths
import javax.inject.{Inject, Singleton}

import akka.stream.scaladsl.Source
import play.api.http.{FileMimeTypes, HttpEntity}
import play.api.mvc.{Action, Controller, ResponseHeader, Result}

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

  def getEntity = Action {
    Result(ResponseHeader(200),
           HttpEntity.Streamed(
             Source.empty,
             Some(0),
             Some("text/plain")
           ))
  }
}

object Main {
  val SampleFilename = "test.txt"
}
