package com.luxoft.akkalabs.day1.futures

import akka.actor.ActorSystem
import com.luxoft.akkalabs.clients.twitter.{QueueTwitterClient, TwitterClients}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContextExecutor, Future}

/**
 * Created by dorlov on 27/4/15.
 */
object ScalaAppleVsGoogle extends App {
  val system: ActorSystem = ActorSystem.create("ScalaAppleVsGoogle")
  val repeat = 10

  def tweets(word: String, n: Int) = {
    val client: QueueTwitterClient = TwitterClients.start(system, word)
    val tweetObjects = 1 to n map (_ => client.next)
    client.stop()
    tweetObjects
  }

  implicit val dispatcher: ExecutionContextExecutor = system.dispatcher

  val fs = List("Apple", "Google") map { w =>
    Future(w -> (tweets(w, repeat) map (t => t.getLanguage)).
      foldLeft(Map.empty[String, Int])((m, l) => m + (l -> (m.getOrElse(l, 0) + 1))))
  }
  val sequence = Future.sequence(fs)

  Await.result(sequence, 60 seconds) foreach println

  system.shutdown()
}
