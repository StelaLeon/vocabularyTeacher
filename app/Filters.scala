import javax.inject.Inject
import filters.ScoreFilter
import play.api.http.HttpFilters
import play.filters.gzip.GzipFilter
import play.filters.headers.SecurityHeadersFilter

/**
 * Created by stela on 01.06.18.
 */

class Filters @Inject() (
    gzip: GzipFilter,
    score: ScoreFilter) extends HttpFilters {

  val filters = Seq(gzip, SecurityHeadersFilter(), score)
}