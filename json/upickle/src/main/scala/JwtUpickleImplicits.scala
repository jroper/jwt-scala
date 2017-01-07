package pdi.jwt

import upickle.Js
import upickle.json
import upickle.default._

/**
  * Created by alonsodomin on 07/09/2016.
  */
private[jwt] trait JwtUpickleImplicits {

  implicit def headerReader(implicit strReader: Reader[String]): Reader[JwtHeader] = Reader[JwtHeader] {
    case obj: Js.Obj =>
      val fieldMap = obj.value.toMap
      JwtHeader(
        algorithm = fieldMap.get("alg").map(_.str).flatMap(alg => JwtAlgorithm.optionFromString(alg)),
        typ = fieldMap.get("typ").map(_.str),
        contentType = fieldMap.get("cty").map(_.str)
      )
  }

  implicit val headerWriter: Writer[JwtHeader] = Writer[JwtHeader] {
    header => json.read(header.toJson)
  }

  implicit val claimReader: Reader[JwtClaim] = Reader[JwtClaim] {
    case obj: Js.Obj =>
      val fieldMap = obj.value.toMap
      val content = fieldMap - "iss" - "sub" - "aud" - "exp" - "nbf" - "iat" - "jti"
      JwtClaim(
        content = json.write(Js.Obj(content.toSeq: _*)),
        issuer = fieldMap.get("iss").map(_.str),
        subject = fieldMap.get("sub").map(_.str),
        audience = fieldMap.get("aud").map(_.arr.map(_.str).toSet),
        expiration = fieldMap.get("exp").map(_.num.toLong),
        notBefore = fieldMap.get("nbf").map(_.num.toLong),
        issuedAt = fieldMap.get("iat").map(_.num.toLong),
        jwtId = fieldMap.get("jti").map(_.str)
      )
  }

  implicit val claimWriter: Writer[JwtClaim] = Writer[JwtClaim] {
    claim => json.read(claim.toJson)
  }

}
