package com.belutrac.challengefinal.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.belutrac.challengefinal.R
import com.belutrac.challengefinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = TeamAdapter(this)
        recyclerView.adapter = adapter

        viewModel.teamsList.observe(this, {
            teamList ->
            adapter.submitList(teamList)
        })

        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.ic_baseline_home_24))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.ic_baseline_favorite_24))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.ic_baseline_location_on_24))
        setContentView(binding.root)
        viewModel.reloadTeams()
    }
}