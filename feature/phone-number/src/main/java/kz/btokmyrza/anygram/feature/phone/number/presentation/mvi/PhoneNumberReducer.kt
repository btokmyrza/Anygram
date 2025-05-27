package kz.btokmyrza.anygram.feature.phone.number.presentation.mvi

import kz.btokmyrza.anygram.feature.phone.number.domain.model.CountryEntity

object PhoneNumberReducer {

    fun getInitialState(): PhoneNumberUiState =
        PhoneNumberUiState(
            selectedCountry = null,
            activeField = ActiveField.PHONE_CODE,
            phoneCode = "",
            phoneNumber = "",
            phoneNumberMask = "",
            phoneNumberMaxLength = null,
            errorMessage = null,
            isLoading = false,
        )

    fun PhoneNumberUiState.countrySelected(country: CountryEntity?): PhoneNumberUiState =
        copy(selectedCountry = country)

    fun PhoneNumberUiState.activeFieldChanged(activeField: ActiveField): PhoneNumberUiState =
        copy(activeField = activeField)

    fun PhoneNumberUiState.phoneCodeChanged(countryCode: String): PhoneNumberUiState =
        copy(phoneCode = countryCode)

    fun PhoneNumberUiState.phoneNumberChanged(phoneNumber: String): PhoneNumberUiState =
        copy(phoneNumber = phoneNumber)

    fun PhoneNumberUiState.phoneNumberCleared(): PhoneNumberUiState =
        copy(phoneNumber = "")

    fun PhoneNumberUiState.phoneNumberMaskUpdated(phoneNumberMask: String): PhoneNumberUiState =
        copy(phoneNumberMask = phoneNumberMask)

    fun PhoneNumberUiState.phoneNumberMaxLengthUpdated(
        phoneNumberMaxLength: Int?,
    ): PhoneNumberUiState =
        copy(phoneNumberMaxLength = phoneNumberMaxLength)

    fun PhoneNumberUiState.error(message: String): PhoneNumberUiState =
        copy(errorMessage = message)

    fun PhoneNumberUiState.errorDismissed(): PhoneNumberUiState =
        copy(errorMessage = null)

    fun PhoneNumberUiState.loading(): PhoneNumberUiState =
        copy(isLoading = true)

    fun PhoneNumberUiState.loaded(): PhoneNumberUiState =
        copy(isLoading = false)
}
