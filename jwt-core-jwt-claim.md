## JwtClaim Class

```scala
import java.time.Clock
import pdi.jwt.JwtClaim

JwtClaim()
// res1: JwtClaim = JwtClaim({}, None, None, None, None, None, None, None)

implicit val clock: Clock = Clock.systemUTC
// clock: Clock = SystemClock[Z]

// Specify the content as JSON string
// (don't use var in your code if possible, this is just to ease the sample)
var claim = JwtClaim("""{"user":1}""")
// claim: JwtClaim = JwtClaim({"user":1}, None, None, None, None, None, None, None)

// Append new content
claim = claim + """{"key1":"value1"}"""
claim = claim + ("key2", true)
claim = claim ++ (("key3", 3), ("key4", Seq(1, 2)), ("key5", ("key5.1", "Subkey")))

// Stringify as JSON
claim.toJson
// res5: String = "{\"user\":1,\"key1\":\"value1\",\"key2\":true,\"key3\":3,\"key4\":[1,2],\"key5\":{\"key5.1\":\"Subkey\"}}"

// Manipulate basic attributes
// Set the issuer
claim = claim.by("Me")

// Set the audience
claim = claim.to("You")

// Set the subject
claim = claim.about("Something")

// Set the id
claim = claim.withId("42")

// Set the expiration
// In 10 seconds from now
claim = claim.expiresIn(5)
// At a specific timestamp (in seconds)
claim.expiresAt(1431520421)
// res11: JwtClaim = JwtClaim({"user":1,"key1":"value1","key2":true,"key3":3,"key4":[1,2],"key5":{"key5.1":"Subkey"}}, Some(Me), Some(Something), Some(Set(You)), Some(1431520421), None, None, Some(42))
// Right now! (the token is directly invalid...)
claim.expiresNow
// res12: JwtClaim = JwtClaim({"user":1,"key1":"value1","key2":true,"key3":3,"key4":[1,2],"key5":{"key5.1":"Subkey"}}, Some(Me), Some(Something), Some(Set(You)), Some(1622035370), None, None, Some(42))

// Set the beginning of the token (aka the "not before" attribute)
// 5 seconds ago
claim.startsIn(-5)
// res13: JwtClaim = JwtClaim({"user":1,"key1":"value1","key2":true,"key3":3,"key4":[1,2],"key5":{"key5.1":"Subkey"}}, Some(Me), Some(Something), Some(Set(You)), Some(1622035375), Some(1622035365), None, Some(42))
// At a specific timestamp (in seconds)
claim.startsAt(1431520421)
// res14: JwtClaim = JwtClaim({"user":1,"key1":"value1","key2":true,"key3":3,"key4":[1,2],"key5":{"key5.1":"Subkey"}}, Some(Me), Some(Something), Some(Set(You)), Some(1622035375), Some(1431520421), None, Some(42))
// Right now!
claim = claim.startsNow

// Set the date when the token was created
// (you should always use claim.issuedNow, but I let you do otherwise if needed)
// 5 seconds ago
claim.issuedIn(-5)
// res16: JwtClaim = JwtClaim({"user":1,"key1":"value1","key2":true,"key3":3,"key4":[1,2],"key5":{"key5.1":"Subkey"}}, Some(Me), Some(Something), Some(Set(You)), Some(1622035375), Some(1622035370), Some(1622035365), Some(42))
// At a specific timestamp (in seconds)
claim.issuedAt(1431520421)
// res17: JwtClaim = JwtClaim({"user":1,"key1":"value1","key2":true,"key3":3,"key4":[1,2],"key5":{"key5.1":"Subkey"}}, Some(Me), Some(Something), Some(Set(You)), Some(1622035375), Some(1622035370), Some(1431520421), Some(42))
// Right now!
claim = claim.issuedNow

// We can test if the claim is valid => testing if the current time is between "not before" and "expiration"
claim.isValid
// res19: Boolean = true

// Also test the issuer and audience
claim.isValid("Me", "You")
// res20: Boolean = true

// Let's stringify the final version
claim.toJson
// res21: String = "{\"iss\":\"Me\",\"sub\":\"Something\",\"aud\":\"You\",\"exp\":1622035375,\"nbf\":1622035370,\"iat\":1622035370,\"jti\":\"42\",\"user\":1,\"key1\":\"value1\",\"key2\":true,\"key3\":3,\"key4\":[1,2],\"key5\":{\"key5.1\":\"Subkey\"}}"
```
