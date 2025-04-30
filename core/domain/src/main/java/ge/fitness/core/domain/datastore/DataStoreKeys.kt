package ge.fitness.core.domain.datastore

import ge.fitness.core.domain.model.PreferenceKey

object DataStoreKeys {

    object LanguagePreference : PreferenceKey<String> {
        override val name = "Language_Config"
        override val defaultValue = "English"
    }

    object IsLoggedIn : PreferenceKey<Boolean> {
        override val name = "IS_LOGGED_IN"
        override val defaultValue = false
    }

    object RememberMe : PreferenceKey<Boolean> {
        override val name = "REMEMBER_ME"
        override val defaultValue = false
    }
}