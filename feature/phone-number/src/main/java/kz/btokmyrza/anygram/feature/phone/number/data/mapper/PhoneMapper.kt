package kz.btokmyrza.anygram.feature.phone.number.data.mapper

import kz.btokmyrza.anygram.feature.phone.number.domain.model.PhoneNumberEntity
import org.drinkless.tdlib.TdApi

class PhoneMapper {

    fun toPhoneNumberEntity(phoneNumberInfo: TdApi.PhoneNumberInfo): PhoneNumberEntity =
        PhoneNumberEntity(
            phoneCode = phoneNumberInfo.countryCallingCode,
            phoneNumberMask = phoneNumberInfo.formattedPhoneNumber,
            isAnonymous = phoneNumberInfo.isAnonymous,
        )
}
