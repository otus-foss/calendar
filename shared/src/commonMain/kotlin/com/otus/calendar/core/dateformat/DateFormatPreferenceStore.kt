package com.otus.calendar.core.dateformat

/** App-local preferences. Installed Mail and Calendar apps do not share this storage. */
interface DateFormatPreferenceStore {
    fun read(): DateFormatPreference

    fun write(preference: DateFormatPreference)
}
