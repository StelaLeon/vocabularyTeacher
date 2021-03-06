package actors

import akka.actor.Actor.Receive
import akka.actor.{ Actor, ActorRef, Props }
import play.api.i18n.Lang
import services.VocabularyService

/**
 * Created by stela on 01.06.18.
 */

object QuizActor {
  def props(out: ActorRef,
    sourceLang: Lang,
    targetLang: Lang,
    vocabulary: VocabularyService): Props =
    Props(classOf[QuizActor], out, sourceLang, targetLang, vocabulary)
}

class QuizActor(out: ActorRef,
    sourceLang: Lang,
    targetLang: Lang,
    vocabulary: VocabularyService) extends Actor {

  private var word = ""

  override def preStart(): Unit = sendWord()

  override def receive: Receive = {
    case translation: String if vocabulary.verify(sourceLang, word, targetLang, translation) =>
      out ! "Correct"
      sendWord()
    case _ => out ! "Incorrect try again!"
  }

  def sendWord() = {
    vocabulary.findRandomVocabulary(sourceLang, targetLang).map(v => {
      out ! s"Please translate: ${v.word}"
      word = v.word
    }).getOrElse {
      out ! s"I don't know any word for ${sourceLang.code} and ${targetLang.code}"
    }
  }
}
