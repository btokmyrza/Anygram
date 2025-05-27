package kz.btokmyrza.anygram.feature.auth.common.domain.model

sealed interface AuthCodeInfoEntity {

    data class TelegramMessage(
        val length: Int,
    ) : AuthCodeInfoEntity {

        override fun toString(): String =
            "Telegram message"
    }

    data class SmsCode(
        val length: Int,
    ) : AuthCodeInfoEntity {

        override fun toString(): String =
            "Sms code"
    }

    data class SmsWord(
        val firstLetter: String,
    ) : AuthCodeInfoEntity {

        override fun toString(): String =
            "Sms word"
    }

    data class SmsPhrase(
        val firstWord: String,
    ) : AuthCodeInfoEntity {

        override fun toString(): String =
            "Sms phrase"
    }

    data class Call(
        val length: Int,
    ) : AuthCodeInfoEntity {

        override fun toString(): String =
            "Call"
    }

    data class FlashCall(
        val pattern: String,
    ) : AuthCodeInfoEntity {

        override fun toString(): String =
            "Flash call"
    }

    data class MissedCall(
        val phoneNumberPrefix: String,
        val length: Int,
    ) : AuthCodeInfoEntity {

        override fun toString(): String =
            "Missed call"
    }

    data class Fragment(
        val url: String,
        val length: Int,
    ) : AuthCodeInfoEntity {

        override fun toString(): String =
            "Fragment"
    }
}
