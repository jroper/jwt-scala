## JwtHeader Case Class

```scala
import pdi.jwt.{JwtHeader, JwtAlgorithm}

JwtHeader()
// res1: JwtHeader = JwtHeader(None, None, None, None)
JwtHeader(JwtAlgorithm.HS256)
// res2: JwtHeader = JwtHeader(Some(HS256), Some(JWT), None, None)
JwtHeader(JwtAlgorithm.HS256, "JWT")
// res3: JwtHeader = JwtHeader(Some(HS256), Some(JWT), None, None)

// You can stringify it to JSON
JwtHeader(JwtAlgorithm.HS256, "JWT").toJson
// res4: String = "{\"typ\":\"JWT\",\"alg\":\"HS256\"}"

// You can assign the default type (but it would have be done automatically anyway)
JwtHeader(JwtAlgorithm.HS256).withType
// res5: JwtHeader = JwtHeader(Some(HS256), Some(JWT), None, None)
```
