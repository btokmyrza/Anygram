package kz.btokmyrza.anygram.feature.phone.number.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.btokmyrza.anygram.feature.phone.number.domain.model.CountryEntity
import kz.btokmyrza.anygram.feature.phone.number.domain.model.PhoneNumberEntity

interface CountryRepository {

    suspend fun getCountries(): List<CountryEntity>

    fun getCurrentLocationCountryLetterCode(): Flow<String>

    fun getPhoneNumberInfoByCode(phoneCode: String): Flow<PhoneNumberEntity>
}
