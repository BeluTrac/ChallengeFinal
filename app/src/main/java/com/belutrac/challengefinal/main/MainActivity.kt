package com.belutrac.challengefinal.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.belutrac.challengefinal.R
import com.belutrac.challengefinal.databinding.ActivityMainBinding
import com.belutrac.challengefinal.login.LoginActivity
import com.belutrac.challengefinal.login.LoginViewModel
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(){
    private lateinit var loginViewModel : LoginViewModel
    private lateinit var mainMenu : Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tab = binding.tabLayout
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                var navController = findNavController(R.id.main_navigation_container)
                val tabItem = tab.position

                if(tabItem == 0){
                    navController.popBackStack(R.id.mainFragment,false)
                }

              if(tabItem == 1){
                 navController.popBackStack(R.id.mainFragment,false)
                  navController.navigate(MainFragmentDirections.actionMainFragmentToFavFragment2())

              }

                if(tabItem == 2){
                    navController.popBackStack(R.id.mainFragment,false)
                    findNavController(R.id.main_navigation_container).navigate(MainFragmentDirections.actionMainFragmentToMapsFragment())
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean{
        if(item.itemId == R.id.log_out_item){
            loginViewModel.logout()
            startActivity(Intent(this,LoginActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}