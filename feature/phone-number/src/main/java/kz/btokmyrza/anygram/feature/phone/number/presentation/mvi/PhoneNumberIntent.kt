package kz.btokmyrza.anygram.feature.phone.number.presentation.mvi

sealed interface PhoneNumberIntent {

    data object OnChooseCountryClicked : PhoneNumberIntent

    data class OnActiveFieldChanged(
        val activeField: ActiveField,
    ) : PhoneNumberIntent

    data class OnPhoneCodeChanged(
        val countryCode: String,
    ) : PhoneNumberIntent

    data class OnPhoneNumberChanged(
        val phoneNumber: String,
    ) : PhoneNumberIntent

    data object OnPhoneCodeDeleted : PhoneNumberIntent

    data object OnPhoneNumberDeleted : PhoneNumberIntent

    data object OnNextClicked : PhoneNumberIntent
}