package com.example.beyondcopy

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.beyondcopy.database.DataBaseViewModel
import com.example.beyondcopy.databinding.ActivityMainBinding
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)

        val appBarConfiguration = AppBarConfiguration(navHostFragment.navController.graph)
        binding.toolbar.setupWithNavController(navHostFragment.navController, appBarConfiguration)

        val dataViewModel: DataBaseViewModel by viewModels()

        dataViewModel.toolbarMenu.observe(this){
            binding.toolbar.let{ toolbar ->
                toolbar.menu.clear()
                if(!it.clear){
                    toolbar.inflateMenu(it.menuId)
                    for(i in toolbar.menu.children)
                        i.icon?.setTint(
                            MaterialColors.getColor(
                                this,
                                androidx.appcompat.R.attr.colorAccent,
                                resources.getColor(R.color.defaultColor, theme)
                            )
                        )
                    toolbar.setOnMenuItemClickListener(it.onMenuItemClickListener)
                }
            }
        }
    }
}