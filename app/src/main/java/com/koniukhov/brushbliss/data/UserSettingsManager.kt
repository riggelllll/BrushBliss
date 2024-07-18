package com.koniukhov.brushbliss.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.koniukhov.brushbliss.model.UserSettings
import com.koniukhov.brushbliss.util.DEFAULT_ALPHA
import com.koniukhov.brushbliss.util.DEFAULT_COLOR
import com.koniukhov.brushbliss.util.DEFAULT_STROKE_WIDTH
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val DATASTORE_NAME = "user_settings_datastore"
class UserSettingsManager(private val dataStore: DataStore<Preferences>) {

    val userSettingsFlow: Flow<UserSettings> = dataStore.data.map {
        val color = it[Keys.COLOR] ?: DEFAULT_COLOR
        val alpha = it[Keys.ALPHA] ?: DEFAULT_ALPHA
        val strokeWidth = it[Keys.STROKE_WIDTH] ?: DEFAULT_STROKE_WIDTH

        UserSettings(color, alpha, strokeWidth)
    }

    suspend fun updateColor(color: Int){
        dataStore.edit {
            it[Keys.COLOR] = color
        }
    }

    suspend fun updateAlpha(alpha: Int){
        dataStore.edit {
            it[Keys.ALPHA] = alpha
        }
    }

    suspend fun updateStrokeWidth(strokeWidth: Float){
        dataStore.edit {
            it[Keys.STROKE_WIDTH] = strokeWidth
        }
    }

    companion object{
        val Context.datastore by preferencesDataStore(DATASTORE_NAME)
    }


    private object Keys{
        val COLOR = intPreferencesKey("color")
        val ALPHA = intPreferencesKey("alpha")
        val STROKE_WIDTH = floatPreferencesKey("stroke_width")
    }
}