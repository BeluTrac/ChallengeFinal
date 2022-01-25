package com.belutrac.challengefinal.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.belutrac.challengefinal.R
import com.belutrac.challengefinal.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMainBinding.inflate(inflater, container,false)
        val rootView = binding.root
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

       val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = TeamAdapter(requireActivity())
        recyclerView.adapter = adapter
        Log.d("LALA","Entre a onCreateView")
        viewModel.teamsList.observe(requireActivity(), {
            teamList ->
            Log.d("LALA", teamList.size.toString())
            adapter.submitList(teamList)
        })
        viewModel.reloadTeams()
        return rootView
    }
}