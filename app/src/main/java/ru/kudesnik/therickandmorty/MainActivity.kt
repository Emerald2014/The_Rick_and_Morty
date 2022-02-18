package ru.kudesnik.therickandmorty

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ru.kudesnik.therickandmorty.ui.main.MainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

//        val nav: BottomNavigationView = findViewById(R.id.bottom_navigation)
//        nav.setOnItemSelectedListener {
//          object:  NavigationBarView.OnItemSelectedListener {
//                override fun onNavigationItemSelected(item: MenuItem): Boolean {
//                    return false
//                            return when (item.itemId) {
//                                R.id.btn_menu_prev_page -> {
//                                    Toast.makeText(
//                                        requireContext(),
//                                        "Жмакаю назад",
//                                        Toast.LENGTH_SHORT
//                                    )
//                                        .show()
//                                    arguments?.getParcelable<Character>(BUNDLE_CHARACTER)?.let {
//                                        viewModel.loadCharacter(it.id - 1)
//                                    }
//                                    true
//                                }
//                                else -> {
//                                    false
//                                }
//                            }
//                }
//            }
//        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_character_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_menu_prev_page -> {
                true
            }
            R.id.btn_menu_episode_character_list -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}