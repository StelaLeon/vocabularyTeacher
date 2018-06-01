package models

import play.api.i18n.Lang
/**
 * Created by stela on 31.05.18.
 */
case class Vocabulary(sourceLanguage: Lang,
  targetLanguage: Lang,
  word: String,
  translation: String)
