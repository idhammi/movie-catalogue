package id.idham.moviecatalogue.ui.settings

import android.os.Bundle
import android.view.MenuItem
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.ui.base.BaseActivity

class SettingsActivity : BaseActivity() {

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_settings)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction().add(R.id.setting_holder, SettingsFragment())
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
