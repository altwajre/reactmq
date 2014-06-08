package com.reactmq

import akka.stream.actor.ActorProducer
import akka.actor.ActorRef
import akka.stream.actor.ActorProducer._
import com.reactmq.queue.{ReceiveMessages, MessageData}

class ReceiveFromQueueProducer(queueActor: ActorRef) extends ActorProducer[MessageData] {

  override def receive = {
    case Request(elements) => if (isActive && totalDemand > 0) {
      queueActor ! ReceiveMessages(elements)
    }
    case Cancel => // TODO: propagate to queue actor
    case msgs: List[MessageData] => if (isActive) {
      msgs.foreach(onNext)
    } else {
      // TODO: return messages
    }
  }
}