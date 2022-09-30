package moviedleapp.main.helpers.moviedleClassic

import moviedleapp.main.helpers.ResultType

data class ComparedAttributes(
    var type: ResultType = ResultType.UNKNOWN,
    var genre: ResultType = ResultType.UNKNOWN,
    var director: ResultType = ResultType.UNKNOWN,
    var rank: ResultType = ResultType.UNKNOWN,
    var releaseYear: ResultType = ResultType.UNKNOWN
)