package kz.btokmyrza.anygram.feature.phone.number.presentation.mapper

import android.content.Context
import kz.btokmyrza.anygram.feature.phone.number.R
import kz.btokmyrza.anygram.feature.phone.number.presentation.model.PhoneNumberUiError

internal fun mapToPhoneNumberUiError(context: Context, errorMessage: String): PhoneNumberUiError =
    when (errorMessage) {
        "PHONE_NUMBER_INVALID" -> {
            PhoneNumberUiError(
                title = context.getString(R.string.phone_number_error_title_invalid_number),
                message = context.getString(R.string.phone_number_error_message_invalid_number),
            )
        }
        "PHONE_NUMBER_BANNED" -> {
            PhoneNumberUiError(
                title = context.getString(R.string.phone_number_error_title_banned_number),
                message = context.getString(R.string.phone_number_error_message_banned_number),
            )
        }
        "TOO_MANY_REQUESTS" -> {
            PhoneNumberUiError(
                title = context.getString(R.string.phone_number_error_title_too_many_requests),
                message = context.getString(R.string.phone_number_error_message_too_many_requests),
            )
        }
        "API_ID_INVALID" -> {
            PhoneNumberUiError(
                title = context.getString(R.string.phone_number_error_title_api_invalid),
                message = context.getString(R.string.phone_number_error_message_api_invalid),
            )
        }
        else -> {
            PhoneNumberUiError(
                title = context.getString(R.string.phone_number_error_title_unknown),
                message = context.getString(R.string.phone_number_error_message_unknown),
            )
        }
    }
