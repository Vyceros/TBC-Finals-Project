package ge.fitness.core.domain.datastore

import ge.fitness.core.domain.model.PreferenceKey

object DataStoreKeys {

    object ThemePreference : PreferenceKey<String> {
        override val name = "Theme_Config"
        override val defaultValue = "System"
    }

    object LanguagePreference : PreferenceKey<String> {
        override val name = "Language_Config"
        override val defaultValue = "English"
    }

    object IsFirstTime : PreferenceKey<Boolean> {
        override val name = "IS_FIRST_TIME"
        override val defaultValue = true
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