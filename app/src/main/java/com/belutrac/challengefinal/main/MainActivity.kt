package com.belutrac.challengefinal.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import com.belutrac.challengefinal.R
import com.belutrac.challengefinal.databinding.ActivityMainBinding
import com.belutrac.challengefinal.favorites.FavFragmentDirections
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tab = binding.tabLayout
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tabItem = tab.position

                if(tabItem == 0){
                    findNavController(R.id.main_navigation_container).navigate(FavFragmentDirections.actionFavFragment2ToMainFragment())
                }

              if(tabItem == 1){
                  findNavController(R.id.main_navigation_container).navigate(MainFragmentDirections.actionMainFragmentToFavFragment2())
              }

                if(tabItem == 2){

                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

}