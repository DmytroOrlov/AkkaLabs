package com.luxoft.akkalabs.day1.wikipedia2.actors

import akka.actor.{Actor, ActorRef, Props}
import com.luxoft.akkalabs.clients.twitter.TweetObject

import scala.collection.JavaConversions.collectionAsScalaIterable

/**
 * Created by dorlov on 27/4/15.
 */
class ScalaTweetLinksActor(linksActor: ActorRef) extends Actor {
  def receive: Receive = {
    case t: TweetObject => t.getUrls.foreach(linksActor ! _)
    case _ =>
  }
}

object ScalaTweetLinksActor {
  def props(linksActor: ActorRef): Props = Props(new ScalaTweetLinksActor(linksActor))
}
