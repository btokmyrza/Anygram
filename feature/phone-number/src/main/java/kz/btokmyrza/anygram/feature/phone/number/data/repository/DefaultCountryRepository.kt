package kz.btokmyrza.anygram.feature.phone.number.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kz.btokmyrza.anygram.feature.phone.number.data.mapper.CountryMapper
import kz.btokmyrza.anygram.feature.phone.number.data.mapper.PhoneMapper
import kz.btokmyrza.anygram.feature.phone.number.domain.model.CountryEntity
import kz.btokmyrza.anygram.feature.phone.number.domain.model.PhoneNumberEntity
import kz.btokmyrza.anygram.feature.phone.number.domain.repository.CountryRepository
import kz.btokmyrza.library.core.data.datasource.TdlibDataSource
import org.drinkless.tdlib.TdApi

class DefaultCountryRepository(
    private val tdlibDataSource: TdlibDataSource,
    private val countryMapper: CountryMapper,
    private val phoneMapper: PhoneMapper,
) : CountryRepository {

    private val countriesCache = mutableListOf<CountryEntity>()

    override suspend fun getCountries(): List<CountryEntity> {
        if (countriesCache.isEmpty()) {
            countriesCache.addAll(fetchCountries())
        }

        return countriesCache
    }

    override fun getCurrentLocationCountryLetterCode(): Flow<String> =
        tdlibDataSource.sendAsync(TdApi.GetCountryCode())
            .filterIsInstance<TdApi.Text>()
            .map { countryCodeText -> countryCodeText.text }

    override fun getPhoneNumberInfoByCode(phoneCode: String): Flow<PhoneNumberEntity> =
        tdlibDataSource.sendAsync(TdApi.GetPhoneNumberInfo(phoneCode))
            .filterIsInstance<TdApi.PhoneNumberInfo>()
            .map(phoneMapper::toPhoneNumberEntity)

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun fetchCountries(): List<CountryEntity> =
        getCountryInfos()
            .flatMapConcat { countryInfos: Array<TdApi.CountryInfo> ->
                countryInfos.asFlow()
            }
            .flatMapMerge { countryInfo: TdApi.CountryInfo ->
                getCountryEmoji(countryInfo.countryCode).map { flagEmoji: String ->
                    countryMapper.toCountryEntity(
                        flagEmoji = flagEmoji,
                        countryInfo = countryInfo,
                    )
                }
            }
            .toList()

    private fun getCountryInfos(): Flow<Array<TdApi.CountryInfo>> =
        tdlibDataSource.sendAsync(TdApi.GetCountries())
            .filterIsInstance<TdApi.Countries>()
            .map { countries -> countries.countries }

    private fun getCountryEmoji(countryCode: String): Flow<String> =
        tdlibDataSource.sendAsync(TdApi.GetCountryFlagEmoji(countryCode))
            .filterIsInstance<TdApi.Text>()
            .map { countryEmojiText -> countryEmojiText.text }
}
