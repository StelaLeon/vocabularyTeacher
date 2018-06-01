package controllers
import scala.concurrent._
import play.api.routing.Router
import play.api.mvc.Results._
import play.api.mvc._
import play.api._
import play.api.http.DefaultHttpErrorHandler
import javax.inject._

/**
 * Created by stela on 31.05.18.
 */
class ErrorHandler @Inject() (
    env: Environment,
    config: Configuration,
    sourceMapper: OptionalSourceMapper,
    router: Provider[Router]) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  override protected def onNotFound(request: RequestHeader, message: String): Future[Result] = {
    Future.successful {
      NotFound("Could not find " + request)
    }
  }

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful {
      NotImplemented("Don't worry we'll be implementing this soon")
    }
  }
}
