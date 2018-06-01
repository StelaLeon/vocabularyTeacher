package controllers
import javax.inject.Inject

import models.Vocabulary
import play.api.mvc._
import play.api.i18n.Lang
import services.VocabularyService

/**
 * Created by stela on 31.05.18.
 */

class Import @Inject() (vocabulary: VocabularyService) extends Controller {
  def importWord(
    sourceLanguage: Lang,
    targetLanguage: Lang,
    word: String,
    translation: String) = Action { request =>
    val added = vocabulary.addVocabulary(
      Vocabulary(sourceLanguage, targetLanguage, word, translation)
    )
    if (added)
      Ok
    else
      Conflict
  }

}