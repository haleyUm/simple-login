package services

import javax.inject.{Inject, Singleton}
import models.UserSchema.User
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class LoginService @Inject()(ws: WSClient)(implicit ec: ExecutionContext) {

  def ldapAuth(user: User): Future[WSResponse] ={
    ws.url("http://hello.daumcorp.com:9124/hellomis/rest/identity/members/auth")
      .addHttpHeaders("authkey" -> "oU7mD2ib%2B%2FbWnLu8hItggVg0wZD8%2F8f9V1Gx4gizm4VIuErFpVhffmMJ3wzdfMBm3fFVOybn5pE7%0AZR5Gm5500w%3D%3D" )
      .addHttpHeaders("Content-Type" -> "application/x-www-form-urlencoded")
      .post(Map("id" -> user.id, "pw" -> user.password, "userip" -> ""))
  }
}
