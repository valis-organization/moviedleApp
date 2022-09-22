package moviedleapp.main.helpers.moviedleClassic

import moviedleapp.main.helpers.ResultType

fun areAllAttributesCorrect(attributes: ComparedAttributes): Boolean {
    return isAttributeCorrect(attributes.director) &&
            isAttributeCorrect(attributes.genre) &&
            isAttributeCorrect(attributes.rank) &&
            isAttributeCorrect(attributes.type) &&
            isAttributeCorrect(attributes.releaseYear)
}

private fun isAttributeCorrect(attribute: ResultType) = attribute == ResultType.CORRECT
