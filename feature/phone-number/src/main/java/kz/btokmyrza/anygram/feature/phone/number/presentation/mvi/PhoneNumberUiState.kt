package kz.btokmyrza.anygram.feature.phone.number.presentation.mvi

import androidx.compose.runtime.Stable
import kz.btokmyrza.anygram.feature.phone.number.domain.model.CountryEntity

@Stable
data class PhoneNumberUiState(
    val selectedCountry: CountryEntity?,
    val activeField: ActiveField,
    val phoneCode: String,
    val phoneNumber: String,
    val phoneNumberMask: String,
    val phoneNumberMaxLength: Int?,
    val errorMessage: String?,
    val isLoading: Boolean,
)

enum class ActiveField {
    PHONE_CODE,
    PHONE_NUMBER;
}
