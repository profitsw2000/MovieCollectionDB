package ru.profitsw2000.moviecollectiondb

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import ru.profitsw2000.moviecollectiondb.ui.favorite.FavoriteFragment
import ru.profitsw2000.moviecollectiondb.ui.main.MainFragment
import ru.profitsw2000.moviecollectiondb.ui.menu.SettingsFragment
import ru.profitsw2000.moviecollectiondb.ui.notes.NoteFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_settings-> {
                openFragment(SettingsFragment.newInstance())
                true
            }

            R.id.menu_favorite-> {
                openFragment(FavoriteFragment.newInstance())
                true
            }

            R.id.menu_history-> {
                true
            }

            R.id.menu_notes-> {
                openFragment(NoteFragment.newInstance())
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.apply {
            beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }
}