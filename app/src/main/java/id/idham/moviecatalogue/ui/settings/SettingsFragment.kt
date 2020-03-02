package id.idham.moviecatalogue.ui.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.service.ReminderReceiver
import org.koin.android.ext.android.inject

/**
 * Created by idhammi on 3/1/2020.
 */

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private val reminderReceiver by inject<ReminderReceiver>()

    private lateinit var RELEASE: String
    private lateinit var DAILY: String
    private lateinit var LANG: String

    private lateinit var releasePref: SwitchPreference
    private lateinit var dailyPref: SwitchPreference
    private lateinit var langPref: Preference

    private lateinit var mContext: Context

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        RELEASE = getString(R.string.key_release)
        DAILY = getString(R.string.key_daily)
        LANG = getString(R.string.key_lang)

        releasePref = findPreference<SwitchPreference>(RELEASE) as SwitchPreference
        dailyPref = findPreference<SwitchPreference>(DAILY) as SwitchPreference
        langPref = findPreference<Preference>(LANG) as Preference

        mContext = context as Context

        langPref.setOnPreferenceClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
            true
        }

        setValue()
    }

    private fun setValue() {
        val sh = preferenceManager.sharedPreferences
        releasePref.isChecked = sh.getBoolean(RELEASE, false)
        dailyPref.isChecked = sh.getBoolean(DAILY, false)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == RELEASE) {
            releasePref.isChecked = sharedPreferences.getBoolean(RELEASE, false)
            if (releasePref.isChecked) {
                if (!isAlarmSet(ReminderReceiver.TYPE_RELEASE))
                    reminderReceiver.setReleaseAlarm(mContext, ReminderReceiver.TYPE_RELEASE)
            } else {
                if (isAlarmSet(ReminderReceiver.TYPE_RELEASE)) {
                    reminderReceiver.cancelAlarm(mContext, ReminderReceiver.TYPE_RELEASE)
                }
            }
        }
        if (key == DAILY) {
            dailyPref.isChecked = sharedPreferences.getBoolean(DAILY, false)
            if (dailyPref.isChecked) {
                if (!isAlarmSet(ReminderReceiver.TYPE_DAILY))
                    reminderReceiver.setDailyAlarm(mContext, ReminderReceiver.TYPE_DAILY)
            } else {
                if (isAlarmSet(ReminderReceiver.TYPE_DAILY)) {
                    reminderReceiver.cancelAlarm(mContext, ReminderReceiver.TYPE_DAILY)
                }
            }
        }
    }

    private fun isAlarmSet(type: String): Boolean {
        return reminderReceiver.isAlarmSet(mContext, type)
    }

}