package com.example.beyondcopy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.beyondcopy.databinding.ActivityMainBinding
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

//        val dataViewModel: DataBaseViewModel by viewModels()
//
//
//        dataViewModel.deleteAll()
//        dataViewModel.insertCharacterWeapon(1L, "Палица", 2, "1d8", "дробящий")
//
//        lifecycleScope.launch(Dispatchers.IO) {
//            val characters = dataViewModel.getAllCharacters()
//            for(i in characters){
//                Log.d("MyLog", i.id.toString())
//            }
//            val characterWithWeapons = dataViewModel.getCharacterWithWeapons(characters[0].id)
//            val weapons = characterWithWeapons.weapons
//            for (i in weapons){
//                Log.d("MyLog", i.toString())
//            }
//        }
    }
}