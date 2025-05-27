package kz.btokmyrza.anygram.feature.phone.number.presentation.mvi

sealed interface PhoneNumberIntent {

    data object OnChooseCountryClicked : PhoneNumberIntent

    data class OnActiveFieldChanged(
        val activeField: ActiveField,
    ) : PhoneNumberIntent

    data class OnPhoneChanged(
        val phone: String,
    ) : PhoneNumberIntent

    data object OnPhoneDeleted : PhoneNumberIntent

    data object OnNextClicked : PhoneNumberIntent

    data object OnOkClicked : PhoneNumberIntent
}