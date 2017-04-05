# empty-path-play-bug

If we do `Ok.sendPath()` where the path is an empty file, it'll lead to an error because Akka wants you to be explicit when you send
a zero-size body response.

Run: `sbt run`.

Get:

```
$ curl -i http://localhost:900/
HTTP/1.1 500 Internal Server Error

...
        <h1>Execution exception</h1>
                <p id="detail" class="pre">[ServerResultException: Error converting Play Result for server backend]</p>
```

Stacktrace:

```
! @73hokk4ed - Internal server error, for (GET) [/] ->
 
play.api.http.HttpErrorHandlerExceptions$$anon$1: Execution exception[[ServerResultException: Error converting Play Result for server backend]]
        at play.api.http.HttpErrorHandlerExceptions$.throwableToUsefulException(HttpErrorHandler.scala:257)
        at play.api.http.DefaultHttpErrorHandler.onServerError(HttpErrorHandler.scala:184)
        at play.core.server.common.ServerResultUtils.play$core$server$common$ServerResultUtils$$handleConversionError$1(ServerResultUtils.scala:117)
        at play.core.server.common.ServerResultUtils$$anonfun$resultConversionWithErrorHandling$9.applyOrElse(ServerResultUtils.scala:136)
        at play.core.server.common.ServerResultUtils$$anonfun$resultConversionWithErrorHandling$9.applyOrElse(ServerResultUtils.scala:136)
        at scala.concurrent.Future.$anonfun$recoverWith$1(Future.scala:412)
        at scala.concurrent.impl.Promise.$anonfun$transformWith$1(Promise.scala:37)
        at scala.concurrent.impl.CallbackRunnable.run(Promise.scala:60)
        at play.api.libs.streams.Execution$trampoline$.executeScheduled(Execution.scala:109)
        at play.api.libs.streams.Execution$trampoline$.execute(Execution.scala:71)
Caused by: play.core.server.common.ServerResultException: Error converting Play Result for server backend
        at play.core.server.common.ServerResultUtils.play$core$server$common$ServerResultUtils$$handleConversionError$1(ServerResultUtils.scala:117)
        at play.core.server.common.ServerResultUtils$$anonfun$resultConversionWithErrorHandling$9.applyOrElse(ServerResultUtils.scala:136)
        at play.core.server.common.ServerResultUtils$$anonfun$resultConversionWithErrorHandling$9.applyOrElse(ServerResultUtils.scala:136)
        at scala.concurrent.Future.$anonfun$recoverWith$1(Future.scala:412)
        at scala.concurrent.impl.Promise.$anonfun$transformWith$1(Promise.scala:37)
        at scala.concurrent.impl.CallbackRunnable.run(Promise.scala:60)
        at play.api.libs.streams.Execution$trampoline$.executeScheduled(Execution.scala:109)
        at play.api.libs.streams.Execution$trampoline$.execute(Execution.scala:71)
        at scala.concurrent.impl.CallbackRunnable.executeWithValue(Promise.scala:68)
        at scala.concurrent.impl.Promise$DefaultPromise.$anonfun$tryComplete$1(Promise.scala:284)
Caused by: java.lang.IllegalArgumentException: requirement failed: contentLength must be positive (use `HttpEntity.empty(contentType)` for empty entities)
        at scala.Predef$.require(Predef.scala:277)
        at akka.http.scaladsl.model.HttpEntity$Default.<init>(HttpEntity.scala:374)
        at play.core.server.akkahttp.AkkaModelConversion.convertResultBody(AkkaModelConversion.scala:201)
        at play.core.server.akkahttp.AkkaModelConversion.$anonfun$convertResult$2(AkkaModelConversion.scala:155)
        at scala.util.Success.$anonfun$map$1(Try.scala:251)
        at scala.util.Success.map(Try.scala:209)
        at scala.concurrent.Future.$anonfun$map$1(Future.scala:287)
        at scala.concurrent.impl.Promise.liftedTree1$1(Promise.scala:29)
        at scala.concurrent.impl.Promise.$anonfun$transform$1(Promise.scala:29)
        at scala.concurrent.impl.CallbackRunnable.run(Promise.scala:60)


```

If you visit `/ent` you'll also see a minimal reproduction case without a file: we send an HttpEntity with content length 0 and get the same result.
