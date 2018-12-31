package actions

import javax.inject.Inject
import play.api.mvc.Results.Redirect
import play.api.mvc.{ActionBuilderImpl, BodyParsers, Request, Result}
import utils.JwtUtility

import scala.concurrent.{ExecutionContext, Future}

class JwtAuthAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext) extends ActionBuilderImpl(parser){

  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    val jwtToken = request.cookies.get("jwt-token-test") match {
      case Some(cookie) => cookie.value
      case None => ""
    }

    if(JwtUtility.isValidToken(jwtToken)) {
      JwtUtility.decodePayload(jwtToken).fold(
        Future.successful(Redirect("/login"))
      ) { _ =>
          block(request)
      }
    }else{
      Future.successful(Redirect("/login"))
    }
  }

}



