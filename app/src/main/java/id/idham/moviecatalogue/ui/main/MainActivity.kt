package id.idham.moviecatalogue.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.ui.base.BaseActivity
import id.idham.moviecatalogue.ui.main.favorite.FavoriteFragment
import id.idham.moviecatalogue.ui.main.movie.MovieFragment
import id.idham.moviecatalogue.ui.main.tvshow.TvShowFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setupBottomNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupBottomNavigation() {
        bn_main.setOnNavigationItemSelectedListener(this)
        loadFragment(MovieFragment())
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
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
                    fragment = MovieFragment()
                }
            }
            R.id.menu_tv_show -> {
                if (currentFragment !is TvShowFragment) {
                    setTitle(R.string.title_tv_shows)
                    fragment = TvShowFragment()
                }
            }
            R.id.menu_favorite -> {
                if (currentFragment !is FavoriteFragment) {
                    setTitle(R.string.title_favorite)
                    fragment = FavoriteFragment()
                    supportActionBar?.elevation = 0f
                }
            }
        }
        return loadFragment(fragment)
    }

}
