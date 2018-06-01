package controllers

import javax.inject.Inject

import actors.QuizActor
import models.Vocabulary
import play.api.mvc.{ Action, Controller, Result, WebSocket }
import play.api.i18n.Lang
import services.VocabularyService
import play.api.Play.current

import scala.concurrent.Future

/**
 * Created by stela on 31.05.18.
 */
class Quiz @Inject() (vocabulary: VocabularyService) extends Controller {

  def quizEndpoint(sourceLang: Lang, targetLang: Lang) =
    WebSocket.acceptWithActor[String, String] {
      request =>
        out =>
          QuizActor.props(out, sourceLang, targetLang, vocabulary)
    }

  def quiz(sourceLanguage: Lang,
    targetLanguage: Lang) = Action { request =>
    val q = vocabulary.findRandomVocabulary(sourceLanguage, targetLanguage)

    q match {
      case None => NotFound
      case Some(v) => Ok.apply(v.word)
    }
  }

  def check(sourceLanguage: Lang,
    word: String,
    targetLanguage: Lang,
    translation: String) = Action { request =>
    val q = vocabulary.verify(sourceLanguage, word, targetLanguage, translation)

    val correctScore = request.session.get("correct").map(_.toInt).getOrElse(0)
    val wrongScore = request.session.get("wrong").map(_.toInt).getOrElse(0)

    q match {
      case false => NotAcceptable.withSession(
        "correct" -> correctScore.toString,
        "wrong" -> (wrongScore + 1).toString
      )
      case true => Ok.withSession(
        "correct" -> (correctScore + 1).toString, "wrong" -> wrongScore.toString)
    }

  }

  def quizLangFromHeader(sourceLanguage: Lang) = Action { request =>
    val targetLanFromHeader = request.headers.get("X-Target-Language")
    targetLanFromHeader match {
      case None => NotFound
      case Some(v) => {
        val q = vocabulary.findRandomVocabulary(sourceLanguage, Lang.get(v).get)
        q match {
          case None => NotFound
          case Some(v) => Ok.apply(v.word)
        }
      }
    }

  }
}
