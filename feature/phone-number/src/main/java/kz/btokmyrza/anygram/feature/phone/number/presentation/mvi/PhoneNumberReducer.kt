package kz.btokmyrza.anygram.feature.phone.number.presentation.mvi

import kz.btokmyrza.anygram.feature.phone.number.domain.model.CountryEntity

object PhoneNumberReducer {

    fun getInitialState(): PhoneNumberUiState =
        PhoneNumberUiState(
            selectedCountry = null,
            activeField = ActiveField.PHONE_CODE,
            countryCode = "",
            phoneNumber = "",
            phoneNumberMask = "",
            phoneNumberMaxLength = null,
        )

    fun PhoneNumberUiState.countrySelected(country: CountryEntity?): PhoneNumberUiState =
        copy(selectedCountry = country)

    fun PhoneNumberUiState.activeFieldChanged(activeField: ActiveField): PhoneNumberUiState =
        copy(activeField = activeField)

    fun PhoneNumberUiState.phoneCodeChanged(countryCode: String): PhoneNumberUiState =
        copy(countryCode = countryCode)

    fun PhoneNumberUiState.phoneNumberChanged(phoneNumber: String): PhoneNumberUiState =
        copy(phoneNumber = phoneNumber)

    fun PhoneNumberUiState.phoneNumberMaskUpdated(phoneNumberMask: String): PhoneNumberUiState =
        copy(phoneNumberMask = phoneNumberMask)

    fun PhoneNumberUiState.phoneNumberMaxLengthUpdated(
        phoneNumberMaxLength: Int?,
    ): PhoneNumberUiState =
        copy(phoneNumberMaxLength = phoneNumberMaxLength)
}
