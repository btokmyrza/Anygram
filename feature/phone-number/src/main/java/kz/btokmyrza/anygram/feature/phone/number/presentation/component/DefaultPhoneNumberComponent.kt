package kz.btokmyrza.anygram.feature.phone.number.presentation.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kz.btokmyrza.anygram.feature.auth.common.domain.model.AuthActionResult
import kz.btokmyrza.anygram.feature.country.chooser.domain.model.CountryChooserEntity
import kz.btokmyrza.anygram.feature.country.chooser.presentation.model.CountryChooserUiModel
import kz.btokmyrza.anygram.feature.phone.number.domain.model.CountryEntity
import kz.btokmyrza.anygram.feature.phone.number.domain.use.cases.FindCountryByPhoneCodeUseCase
import kz.btokmyrza.anygram.feature.phone.number.domain.use.cases.GetCountriesUseCase
import kz.btokmyrza.anygram.feature.phone.number.domain.use.cases.GetCurrentLocationCountryUseCase
import kz.btokmyrza.anygram.feature.phone.number.domain.use.cases.GetPhoneNumberMaskUseCase
import kz.btokmyrza.anygram.feature.phone.number.domain.use.cases.SendPhoneNumberUseCase
import kz.btokmyrza.anygram.feature.phone.number.presentation.mapper.CountryUiMapper
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.ActiveField
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberIntent
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberReducer
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberReducer.activeFieldChanged
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberReducer.countrySelected
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberReducer.error
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberReducer.errorDismissed
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberReducer.loaded
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberReducer.loading
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberReducer.phoneCodeChanged
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberReducer.phoneNumberChanged
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberReducer.phoneNumberCleared
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberReducer.phoneNumberMaskUpdated
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberReducer.phoneNumberMaxLengthUpdated
import kz.btokmyrza.anygram.feature.phone.number.presentation.mvi.PhoneNumberUiState
import kz.btokmyrza.library.core.presentation.base.navigation.BaseComponent
import kz.btokmyrza.library.core.presentation.extensions.updateUiState

class DefaultPhoneNumberComponent(
    componentContext: ComponentContext,
    override val onNavigateToCountryChooser: (List<CountryChooserEntity>) -> Unit,
    override val onNavigateToOtp: () -> Unit,
    private val countryUiMapper: CountryUiMapper,
    private val getCurrentLocationCountryUseCase: GetCurrentLocationCountryUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getPhoneNumberMaskUseCase: GetPhoneNumberMaskUseCase,
    private val findCountryByPhoneCodeUseCase: FindCountryByPhoneCodeUseCase,
    private val sendPhoneNumberUseCase: SendPhoneNumberUseCase,
) : BaseComponent(componentContext), PhoneNumberComponent {

    private val _uiState = MutableStateFlow(PhoneNumberReducer.getInitialState())
    override val uiState: StateFlow<PhoneNumberUiState> = _uiState

    init {
        getCurrentLocationCountry()
    }

    override fun onIntent(intent: PhoneNumberIntent) {
        when (intent) {
            PhoneNumberIntent.OnChooseCountryClicked -> {
                onChooseCountryClicked()
            }
            is PhoneNumberIntent.OnActiveFieldChanged -> {
                onActiveFieldChanged(intent.activeField)
            }
            is PhoneNumberIntent.OnPhoneCodeChanged -> {
                onCountryCodeChanged(intent.countryCode)
            }
            PhoneNumberIntent.OnPhoneCodeDeleted -> {
                onPhoneCodeDeleted()
            }
            is PhoneNumberIntent.OnPhoneNumberChanged -> {
                onPhoneNumberChanged(intent.phoneNumber)
            }
            PhoneNumberIntent.OnPhoneNumberDeleted -> {
                onPhoneNumberDeleted()
            }
            PhoneNumberIntent.OnNextClicked -> {
                onNextClicked()
            }
            PhoneNumberIntent.OnOkClicked -> {
                onOkClicked()
            }
        }
    }

    override fun onCountryChosen(countryChooserUiModel: CountryChooserUiModel.Country?) {
        val country = countryChooserUiModel?.let(countryUiMapper::toCountryEntity) ?: return
        val phoneCode = country.callingCodes.firstOrNull() ?: return

        updateCountryData(country = country, phoneCode = phoneCode)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getCurrentLocationCountry() {
        getCurrentLocationCountryUseCase()
            .mapNotNull { country ->
                val phoneCode = country.callingCodes.firstOrNull() ?: return@mapNotNull null
                updateCountryData(country = country, phoneCode = phoneCode)
                phoneCode
            }
            .flatMapLatest { phoneCode ->
                getPhoneNumberMaskUseCase(phoneCode)
            }
            .onEach(::updatePhoneNumberData)
            .launchIn(this)
    }

    private fun updateCountryData(country: CountryEntity, phoneCode: String) {
        val uiState = _uiState.value

        if (uiState.selectedCountry == country) {
            return
        }

        _uiState.updateUiState { countrySelected(country) }
        _uiState.updateUiState { phoneCodeChanged(phoneCode) }
        _uiState.updateUiState { phoneNumberCleared() }
        _uiState.updateUiState { activeFieldChanged(ActiveField.PHONE_NUMBER) }

        updatePhoneNumberMask(phoneCode)
    }

    private fun onChooseCountryClicked() {
        launch {
            val countries = getCountriesUseCase().let(countryUiMapper::toCountryChooserEntityList)
            onNavigateToCountryChooser(countries)
        }
    }

    private fun onActiveFieldChanged(activeField: ActiveField) {
        _uiState.updateUiState { activeFieldChanged(activeField) }
    }

    private fun onCountryCodeChanged(countryCode: String) {
        val newCountryCode = _uiState.value.phoneCode + countryCode

        if (newCountryCode.length > MAX_COUNTRY_CODE_LENGTH) {
            _uiState.updateUiState { activeFieldChanged(ActiveField.PHONE_NUMBER) }
        } else {
            _uiState.updateUiState { phoneCodeChanged(newCountryCode) }
        }

        updateSelectedCountry(newCountryCode)
        updatePhoneNumberMask(newCountryCode)
    }

    private fun onPhoneCodeDeleted() {
        deletePhoneCodeLastDigit()
    }

    private fun onPhoneNumberChanged(phoneNumber: String) {
        val uiState = _uiState.value
        val phoneNumberMaxLength = uiState.phoneNumberMaxLength ?: Int.MAX_VALUE
        val newPhoneNumber = (uiState.phoneNumber + phoneNumber).take(phoneNumberMaxLength)
        _uiState.updateUiState { phoneNumberChanged(newPhoneNumber) }
    }

    private fun onPhoneNumberDeleted() {
        val newPhoneNumber = _uiState.value.phoneNumber.dropLast(1)
        _uiState.updateUiState { phoneNumberChanged(newPhoneNumber) }

        if (newPhoneNumber.isBlank()) {
            _uiState.updateUiState { activeFieldChanged(ActiveField.PHONE_CODE) }
            deletePhoneCodeLastDigit()
        }
    }

    private fun deletePhoneCodeLastDigit() {
        val newCountryCode = _uiState.value.phoneCode.dropLast(1)
        _uiState.updateUiState { phoneCodeChanged(newCountryCode) }

        updateSelectedCountry(newCountryCode)
        updatePhoneNumberMask(newCountryCode)
    }

    private fun updateSelectedCountry(phoneCode: String) {
        launch {
            val country = findCountryByPhoneCodeUseCase(phoneCode)
            _uiState.updateUiState { countrySelected(country) }

            if (country != null) {
                _uiState.updateUiState { activeFieldChanged(ActiveField.PHONE_NUMBER) }
            }
        }
    }

    private fun updatePhoneNumberMask(phoneCode: String) {
        getPhoneNumberMaskUseCase(phoneCode)
            .onEach(::updatePhoneNumberData)
            .launchIn(this)
    }

    private fun updatePhoneNumberData(data: Pair<String, Int?>) {
        _uiState.updateUiState { phoneNumberMaskUpdated(data.first) }
        _uiState.updateUiState { phoneNumberMaxLengthUpdated(data.second) }
    }

    private fun onNextClicked() {
        val uiState = _uiState.value

        sendPhoneNumberUseCase(uiState.phoneCode + uiState.phoneNumber)
            .onStart {
                _uiState.updateUiState { loading() }
            }
            .onEach { result ->
                when (result) {
                    AuthActionResult.Success -> {
                        onNavigateToOtp()
                    }
                    is AuthActionResult.Error -> {
                        _uiState.updateUiState { error(result.message) }
                    }
                }
            }
            .onCompletion {
                _uiState.updateUiState { loaded() }
            }
            .launchIn(this)
    }

    private fun onOkClicked() {
        _uiState.updateUiState { errorDismissed() }
    }

    private companion object {

        const val MAX_COUNTRY_CODE_LENGTH = 4
    }
}
