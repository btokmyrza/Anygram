package kz.btokmyrza.anygram.feature.phone.number.domain.use.cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kz.btokmyrza.anygram.feature.phone.number.domain.repository.CountryRepository

class GetPhoneNumberMaskUseCase(
    private val countryRepository: CountryRepository,
) {

    operator fun invoke(phoneCode: String): Flow<Pair<String, Int?>> =
        countryRepository.getPhoneNumberInfoByCode(phoneCode)
            .map { phoneNumberEntity ->
                val phoneNumberMask = phoneNumberEntity.phoneNumberMask.replace(
                    regex = nonSpaceCharRegex,
                    replacement = DEFAULT_PHONE_NUMBER_MASK_CHAR,
                )
                val phoneNumberMaxLength = phoneNumberMask.replace(
                    regex = spaceCharRegex,
                    replacement = NO_SPACES_CHAR,
                ).length

                phoneNumberMask to phoneNumberMaxLength.takeIf { it > EMPTY_PHONE_NUMBER_LENGTH }
            }

    private companion object {

        val nonSpaceCharRegex = "[^ ]".toRegex()
        val spaceCharRegex = "\\s".toRegex()
        const val DEFAULT_PHONE_NUMBER_MASK_CHAR = "#"
        const val NO_SPACES_CHAR = ""
        const val EMPTY_PHONE_NUMBER_LENGTH = 0
    }
}
