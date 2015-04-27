package com.luxoft.akkalabs.day1.wikipedia2.web

import javax.servlet.annotation.WebListener
import javax.servlet.{ServletContextEvent, ServletContextListener}

import akka.actor.{ActorSystem, Props}
import com.luxoft.akkalabs.clients.twitter.TwitterClients
import com.luxoft.akkalabs.day1.wikipedia2.actors.{ConnectionsActor, ScalaTweetLinksActor, WikipediaActor2}

/**
 * Created by dorlov on 27/4/15.
 */
@WebListener
class InitContext extends ServletContextListener {
  val systemName: String = "scalaActorSystem"

  override def contextInitialized(servletContextEvent: ServletContextEvent): Unit = {
    val system = ActorSystem.create(systemName)
    system.actorOf(Props[ConnectionsActor], "connections")
    val wikiActor = system.actorOf(Props[WikipediaActor2])
    val tweetLinksActor = system.actorOf(ScalaTweetLinksActor.props(wikiActor))
    TwitterClients.start(system, tweetLinksActor, "wikipedia")
  }

  override def contextDestroyed(sce: ServletContextEvent): Unit = {
    val system: ActorSystem = sce.getServletContext.getAttribute(systemName).asInstanceOf[ActorSystem]
    system.shutdown()
  }
}
