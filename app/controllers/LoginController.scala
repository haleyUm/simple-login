package controllers

import java.util.concurrent.TimeUnit

import javax.inject._
import models.UserSchema.User
import play.api.mvc._
import services.LoginService
import utils.JwtUtility

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.Duration

@Singleton
class LoginController @Inject()(cc: ControllerComponents
                               ,loginService: LoginService) (implicit ec: ExecutionContext)extends AbstractController(cc) {

  def login = Action {
    Ok(views.html.login())
  }

  def loginAuth = Action.async{ implicit request =>

    val body = request.body.asJson

    for {
      result <- body match {
        case Some(json) => loginService.ldapAuth(body.get.as[User]).map(ldapRes => {
          if ( ( ldapRes.json \ "result" \ "code" ).as[String].equals("SUCCESS") ) {
            val token = JwtUtility.createToken(json.toString())
            val duration = Duration.create(1, TimeUnit.DAYS)

            Ok("success").withCookies(Cookie("jwt-token-test", token, Some(duration.toSeconds.toInt), "/", None, false, true))
          } else {
            Ok("fail")
          }
        })
        case None => Future.successful(Ok("fail"))
      }
    } yield result
  }

  def logout = Action.async{
    Future.successful(Ok("logout").discardingCookies(DiscardingCookie("jwt-token")))
  }
}
