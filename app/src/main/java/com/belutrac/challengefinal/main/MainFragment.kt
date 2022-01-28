package com.belutrac.challengefinal.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.belutrac.challengefinal.R
import com.belutrac.challengefinal.Team
import com.belutrac.challengefinal.api.ApiResponseStatus
import com.belutrac.challengefinal.databinding.FragmentMainBinding
import com.belutrac.challengefinal.detail.DetailActivity

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        val rootView = binding.root
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = TeamAdapter(requireActivity())
        recyclerView.adapter = adapter

        adapter.onItemClickListener = {
            startActivityDetail(it)
        }

        adapter.onIcnFavClickListener = {
            viewModel.updateFavorite(it)
        }

        viewModel.teamsList.observe(requireActivity(), { teamList ->
            adapter.submitList(teamList)
        })

        viewModel.statusLiveData.observe(requireActivity(), {
           when(it){
               ApiResponseStatus.LOADING -> {
                   binding.loadingWheel.visibility = View.VISIBLE
               }
               ApiResponseStatus.DONE -> {
                   binding.loadingWheel.visibility = View.GONE
               }
               ApiResponseStatus.NOT_INTERNET_CONNECTION-> {
                   binding.loadingWheel.visibility = View.GONE
               }
               null -> {

               }
           }

        })
        viewModel.reloadTeamsFromDatabase()
        setHasOptionsMenu(true)
        return rootView
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val menuItem = menu.findItem(R.id.search_view)
        val sm = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_view)?.actionView as SearchView

        searchView.setSearchableInfo(
            sm.getSearchableInfo(requireActivity().componentName)
        )

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank())
                    searchTeams(newText)
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })

        menuItem.setOnActionExpandListener(object :
            MenuItem.OnActionExpandListener {

            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                searchTeams("")
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                setTeamsInitialState()
                return true
            }
        })

    }

    private fun setTeamsInitialState() {
        viewModel.reloadTeams()
    }

    private fun searchTeams(newText: String) {
        viewModel.searchTeamsByName(newText)
    }


    private fun startActivityDetail(team: Team) {
        val intent = Intent(this.context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.TEAM_KEY, team)
        startActivity(intent)
    }
}