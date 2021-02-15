package sen.com.core.preferences

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import sen.com.core.PrefKey


/**
 * Created by korneliussendy on 2019-06-29,
 * at 19:26.
 * Snap
 */
class SnapPreference private constructor() {
    private lateinit var pref: SharedPreferences

    init {
        Log.d("INIT", "SnapPreference ${hashCode()}")
    }

    companion object {
        val instance by lazy { SnapPreference() }
    }

    fun init(pref: SharedPreferences) {
        this.pref = pref
    }

    fun putData(key: String, objects: Class<out Any>) {
        pref.edit().putString(key, Gson().toJson(objects)).apply()
    }

    fun putData(key: String, objects: Any) {
        pref.edit().putString(key, Gson().toJson(objects)).apply()
    }

    fun putData(key: String, data: Int) {
        pref.edit().putInt(key, data).apply()
    }

    fun putData(key: String, data: String) {
        pref.edit().putString(key, data).apply()
    }

    fun putData(key: String, data: Long) {
        pref.edit().putLong(key, data).apply()
    }

    fun putData(key: String, data: Boolean) {
        pref.edit().putBoolean(key, data).apply()
    }

    fun getInt(key: String): Int {
        return pref.getInt(key, 0)
    }

    fun getBoolean(key: String): Boolean {
        return pref.getBoolean(key, false)
    }

    fun clear() {
        pref.edit().clear().apply()
    }

}