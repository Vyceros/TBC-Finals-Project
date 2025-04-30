package ge.fitness.workout.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.fitness.core.domain.datastore.DataStoreHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreHelper: DataStoreHelper
) : ViewModel() {


    fun onAction(action: SettingsAction) {
        when (action) {

            is SettingsAction.Logout -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dataStoreHelper.clearAllPreferences()
                }
            }
        }
    }
}

