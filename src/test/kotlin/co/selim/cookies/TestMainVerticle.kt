package co.selim.cookies

import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.Session
import io.vertx.ext.web.sstore.cookie.CookieSessionStore
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.TimeUnit.DAYS

@ExtendWith(VertxExtension::class)
class TestMainVerticle {

  @RepeatedTest(10)
  fun testInterStoreCommunication(vertx: Vertx, ctx: VertxTestContext) {
    val secret = "KeyboardCat!"
    val salt = Buffer.buffer("salt")
    val store1: CookieSessionStore = CookieSessionStore.create(vertx, secret, salt)
    val store2: CookieSessionStore = CookieSessionStore.create(vertx, secret, salt)
    val sesh: Session = store1.createSession(DAYS.toMillis(1))
    store2.get(sesh.value())
      .onFailure { ctx.failNow(it) }
      .onSuccess { ctx.completeNow() }
  }
}
