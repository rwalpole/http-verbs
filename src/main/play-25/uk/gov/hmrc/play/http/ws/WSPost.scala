/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.play.http.ws

import java.util.concurrent.TimeUnit

import play.api.libs.json.{Json, Writes}
import play.api.mvc.Results
import uk.gov.hmrc.http.{CorePost, HeaderCarrier, HttpResponse, PostHttpTransport}

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Future, duration}


trait WSPost extends CorePost with PostHttpTransport with WSRequest with WSExecute {

  override def doPost[A](
    url: String,
    body: A,
    headers: Seq[(String, String)])(
      implicit rds: Writes[A],
      hc: HeaderCarrier,
      ec: ExecutionContext): Future[HttpResponse] =
    execute(buildRequest(url, headers).withBody(Json.toJson(body)), "POST")
      .map(new WSHttpResponse(_))

  override def doPostWithTimeout[A](
    url: String,
    body: A,
    headers: Seq[(String, String)],
    timeoutMillis: Int)(
      implicit rds: Writes[A],
      hc: HeaderCarrier,
      ec: ExecutionContext): Future[HttpResponse] =
    execute(buildRequest(url, headers).withBody(Json.toJson(body)).withRequestTimeout(Duration(timeoutMillis,TimeUnit.MILLISECONDS)), "POST")
      .map(new WSHttpResponse(_))

  override def doFormPost(
    url: String,
    body: Map[String, Seq[String]],
    headers: Seq[(String, String)])(
      implicit hc: HeaderCarrier,
      ec: ExecutionContext): Future[HttpResponse] =
    execute(buildRequest(url, headers).withBody(body), "POST")
      .map(new WSHttpResponse(_))

  override def doPostString(
    url: String,
    body: String,
    headers: Seq[(String, String)])(
      implicit hc: HeaderCarrier,
      ec: ExecutionContext): Future[HttpResponse] =
    execute(buildRequest(url, headers).withBody(body), "POST")
      .map(new WSHttpResponse(_))

  override def doEmptyPost[A](
    url: String,
    headers: Seq[(String, String)])(
      implicit hc: HeaderCarrier,
      ec: ExecutionContext): Future[HttpResponse] = {
    import play.api.http.Writeable._
    execute(buildRequest(url, headers).withBody(Results.EmptyContent()), "POST")
      .map(new WSHttpResponse(_))
  }
}
