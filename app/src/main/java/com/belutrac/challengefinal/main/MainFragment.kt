package com.belutrac.challengefinal.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.belutrac.challengefinal.R
import com.belutrac.challengefinal.api.ApiResponseStatus
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

        viewModel.teamsList.observe(requireActivity(), {
            teamList ->
            adapter.submitList(teamList)
        })

        viewModel.statusLiveData.observe(requireActivity(), {
            if (it == ApiResponseStatus.LOADING) {
                binding.loadingWheel.visibility = View.VISIBLE
            } else {
                binding.loadingWheel.visibility = View.GONE
            }

            if (it == ApiResponseStatus.NO_INTERNET_CONNECTION) {
                Toast.makeText(requireActivity(), R.string.no_internet_connection,
                    Toast.LENGTH_SHORT).show()
            }
        })
        return rootView
    }
}