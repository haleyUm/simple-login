package models

import play.api.libs.json.{Json, Reads, Writes}

object UserSchema {
  case class User(id: String, password: String)

  implicit val reads: Reads[User] = Json.reads[User]
  implicit val writes:  Writes[User] = Json.writes[User]
}
