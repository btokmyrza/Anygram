package kz.btokmyrza.anygram.feature.phone.number.domain.model

data class PhoneNumberEntity(
    val phoneCode: String,
    val phoneNumberMask: String,
    val isAnonymous: Boolean,
)
