package id.idham.moviecatalogue.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.service.ReminderReceiver
import id.idham.moviecatalogue.ui.base.BaseActivity
import id.idham.moviecatalogue.ui.main.favorite.FavoriteFragment
import id.idham.moviecatalogue.ui.main.movie.MovieFragment
import id.idham.moviecatalogue.ui.main.tvshow.TvShowFragment
import id.idham.moviecatalogue.ui.search.SearchActivity
import id.idham.moviecatalogue.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val reminderReceiver by inject<ReminderReceiver>()

    companion object {
        private const val STATE_MENU = "state_menu"
    }

    private var isFavorite = false

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 0f
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        bn_main.setOnNavigationItemSelectedListener(this)
        if (savedInstanceState == null) loadFragment(MovieFragment())
        else {
            val stateMenu = savedInstanceState.getBoolean(STATE_MENU)
            isFavorite = stateMenu
            invalidateOptionsMenu()
        }

        setupReminder()
    }

    private fun setupReminder() {
        val sh = PreferenceManager.getDefaultSharedPreferences(this)
        val isReleaseActive = sh.getBoolean(getString(R.string.key_release), false)
        val isDailyActive = sh.getBoolean(getString(R.string.key_daily), false)

        if (isReleaseActive) {
            if (!reminderReceiver.isAlarmSet(this, ReminderReceiver.TYPE_RELEASE))
                reminderReceiver.setReleaseAlarm(this, ReminderReceiver.TYPE_RELEASE)
        }

        if (isDailyActive) {
            if (!reminderReceiver.isAlarmSet(this, ReminderReceiver.TYPE_DAILY))
                reminderReceiver.setDailyAlarm(this, ReminderReceiver.TYPE_DAILY)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_MENU, isFavorite)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (isFavorite) menu?.findItem(R.id.action_search)?.isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.action_search -> {
                val id = bn_main.menu.findItem(bn_main.selectedItemId)
                val intent = Intent(this, SearchActivity::class.java)
                when (id.itemId) {
                    R.id.menu_movie -> intent.putExtra(
                        SearchActivity.IntentKey.TYPE, SearchActivity.Type.MOVIE
                    )
                    R.id.menu_tv_show -> intent.putExtra(
                        SearchActivity.IntentKey.TYPE, SearchActivity.Type.TVSHOW
                    )
                }
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            invalidateOptionsMenu()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit()
            return true
        }
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        val currentFragment = supportFragmentManager.findFragmentById(R.id.frameLayout)
        when (item.itemId) {
            R.id.menu_movie -> {
                if (currentFragment !is MovieFragment) {
                    setTitle(R.string.title_movies)
                    isFavorite = false
                    fragment = MovieFragment()
                }
            }
            R.id.menu_tv_show -> {
                if (currentFragment !is TvShowFragment) {
                    setTitle(R.string.title_tv_shows)
                    isFavorite = false
                    fragment = TvShowFragment()
                }
            }
            R.id.menu_favorite -> {
                if (currentFragment !is FavoriteFragment) {
                    setTitle(R.string.title_favorite)
                    isFavorite = true
                    fragment = FavoriteFragment()
                }
            }
        }
        return loadFragment(fragment)
    }

}
