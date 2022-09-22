package moviedleapp.main.helpers.moviedleClassic

import moviedleapp.main.helpers.ResultType

class GuessResultHelper {
    companion object {
        fun areAllAttributesCorrect(attributes: ComparedAttributes): Boolean {
            if (isAttributeCorrect(attributes.director)) {
                if (isAttributeCorrect(attributes.genre)) {
                    if (isAttributeCorrect(attributes.rank)) {
                        if (isAttributeCorrect(attributes.type)) {
                            if (isAttributeCorrect(attributes.releaseYear)) {
                                return true
                            }
                        }
                    }
                }
            }
            return false
        }

        private fun isAttributeCorrect(attribute: ResultType): Boolean {
            if (attribute == ResultType.CORRECT) {
                return true
            }
            return false
        }
    }
}