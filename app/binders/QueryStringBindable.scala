package binders

import play.api.i18n.Lang
import play.api.mvc.QueryStringBindable

/**
 * Created by stela on 31.05.18.
 */
object QueryStringBindable {

  implicit object LangQueryStringBinder extends QueryStringBindable[Lang] {
    override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, Lang]] = {
      val code = params.get(key).flatMap(_.headOption)
      code.map { c =>
        Lang.get(c).toRight(s"$c is not a valid language")
      }
    }

    override def unbind(key: String, value: Lang): String = value.code
  }
}
