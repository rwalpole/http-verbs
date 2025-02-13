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

package uk.gov.hmrc.http

import play.api.libs.json.Writes

import scala.concurrent.{ExecutionContext, Future}

trait GetHttpTransport {
  def doGet(
    url: String,
    headers: Seq[(String, String)] = Seq.empty)(
      implicit hc: HeaderCarrier,
      ec: ExecutionContext): Future[HttpResponse]
}

trait DeleteHttpTransport {
  def doDelete(
    url: String,
    headers: Seq[(String, String)] = Seq.empty)(
      implicit hc: HeaderCarrier,
      ec: ExecutionContext): Future[HttpResponse]
}

trait PatchHttpTransport {
  def doPatch[A](
    url: String,
    body: A,
    headers: Seq[(String, String)] = Seq.empty)(
      implicit rds: Writes[A],
      hc: HeaderCarrier,
      ec: ExecutionContext): Future[HttpResponse]
}

trait PutHttpTransport {
  def doPut[A](
    url: String,
    body: A,
    headers: Seq[(String, String)] = Seq.empty)(
      implicit rds: Writes[A],
      hc: HeaderCarrier,
      ec: ExecutionContext): Future[HttpResponse]

  def doPutString(
    url: String,
    body: String,
    headers: Seq[(String, String)] = Seq.empty)(
      implicit hc: HeaderCarrier,
      ec: ExecutionContext): Future[HttpResponse]
}

trait PostHttpTransport {
  def doPost[A](
    url: String,
    body: A,
    headers: Seq[(String, String)] = Seq.empty)(
      implicit wts: Writes[A],
      hc: HeaderCarrier,
      ec: ExecutionContext): Future[HttpResponse]

  def doPostWithTimeout[A](
     url: String,
     body: A,
     headers: Seq[(String, String)] = Seq.empty,
     timeout: Int)(
        implicit wts: Writes[A],
        hc: HeaderCarrier,
        ec: ExecutionContext): Future[HttpResponse]

  def doPostString(
    url: String,
    body: String,
    headers: Seq[(String, String)] = Seq.empty)(
      implicit hc: HeaderCarrier,
      ec: ExecutionContext): Future[HttpResponse]

  def doEmptyPost[A](
    url: String,
    headers: Seq[(String, String)] = Seq.empty)(
      implicit hc: HeaderCarrier,
      ec: ExecutionContext): Future[HttpResponse]

  def doFormPost(
    url: String,
    body: Map[String, Seq[String]],
    headers: Seq[(String, String)] = Seq.empty)(
      implicit hc: HeaderCarrier,
      ec: ExecutionContext): Future[HttpResponse]
}

trait HttpTransport
    extends GetHttpTransport
    with DeleteHttpTransport
    with PatchHttpTransport
    with PutHttpTransport
    with PostHttpTransport {}

trait CoreGet {
  def GET[A](
    url: String,
    queryParams: Seq[(String, String)],
    headers: Seq[(String, String)])(
      implicit rds: HttpReads[A],
      hc: HeaderCarrier,
      ec: ExecutionContext): Future[A]

  def GET[A](
    url: String)(
      implicit rds: HttpReads[A],
      hc: HeaderCarrier,
      ec: ExecutionContext): Future[A] =
    GET(url, Seq.empty, Seq.empty)

  def GET[A](
    url: String,
    queryParams: Seq[(String, String)])(
      implicit rds: HttpReads[A],
      hc: HeaderCarrier,
      ec: ExecutionContext): Future[A] =
    GET(url, queryParams, Seq.empty)

}

trait CoreDelete {
  def DELETE[O](
    url: String,
    headers: Seq[(String, String)] = Seq.empty)(
      implicit rds: HttpReads[O],
      hc: HeaderCarrier,
      ec: ExecutionContext): Future[O]
}

trait CorePatch {
  def PATCH[I, O](
    url: String,
    body: I,
    headers: Seq[(String, String)] = Seq.empty)(
      implicit wts: Writes[I],
      rds: HttpReads[O],
      hc: HeaderCarrier,
      ec: ExecutionContext): Future[O]
}

trait CorePut {
  def PUT[I, O](
    url: String,
    body: I,
    headers: Seq[(String, String)] = Seq.empty)(
      implicit wts: Writes[I],
      rds: HttpReads[O],
      hc: HeaderCarrier,
      ec: ExecutionContext): Future[O]

  def PUTString[O](
    url: String,
    body: String,
    headers: Seq[(String, String)] = Seq.empty)(
      implicit rds: HttpReads[O],
      hc: HeaderCarrier,
      ec: ExecutionContext): Future[O]
}

trait CorePost {
  def POST[I, O](
    url: String,
    body: I,
    headers: Seq[(String, String)] = Seq.empty)(
      implicit wts: Writes[I],
      rds: HttpReads[O],
      hc: HeaderCarrier,
      ec: ExecutionContext): Future[O]

  def POSTString[O](
    url: String,
    body: String,
    headers: Seq[(String, String)] = Seq.empty)(
      implicit rds: HttpReads[O],
      hc: HeaderCarrier,
      ec: ExecutionContext): Future[O]

  def POSTForm[O](
    url: String,
    body: Map[String, Seq[String]],
    headers: Seq[(String, String)] = Seq.empty)(
      implicit rds: HttpReads[O],
      hc: HeaderCarrier,
      ec: ExecutionContext): Future[O]

  def POSTEmpty[O](
    url: String,
    headers: Seq[(String, String)] = Seq.empty)(
      implicit rds: HttpReads[O],
      hc: HeaderCarrier,
      ec: ExecutionContext): Future[O]
}
