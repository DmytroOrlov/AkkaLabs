package com.luxoft.akkalabs.day1.wikipedia2.actors

import akka.actor.Actor
import com.luxoft.akkalabs.clients.wikipedia.WikipediaPage
import com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics._

/**
 * Created by dorlov on 28/4/15.
 */
case class Register(listener: WikipediaListener)

case class Unregister(id: String)

case class Deliver(page: WikipediaPage)

class ScalaConnectionsActor extends Actor {
  var wikiListeners = Map.empty[String, WikipediaListener]

  override def receive: Receive = {
    case Deliver(p) =>
      for ((i, l) <- wikiListeners) try {
        l.deliver(p)
      } catch {
        case _: NotDeliveredException => wikiListeners -= i
      }
    case Register(l) =>
      wikiListeners += (l.getStreamId -> l)
    case Unregister(id) =>
      wikiListeners -= id
  }
}
