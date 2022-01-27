package com.belutrac.challengefinal.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.belutrac.challengefinal.R
import com.belutrac.challengefinal.Team
import com.belutrac.challengefinal.api.ApiResponseStatus
import com.belutrac.challengefinal.databinding.FragmentMainBinding
import com.belutrac.challengefinal.detail.DetailActivity

class MainFragment : Fragment() {

    private lateinit var viewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater, container,false)
        val rootView = binding.root
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
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
        setHasOptionsMenu(true)
        return rootView
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val menuItem = menu.findItem(R.id.search_view)
        val sm = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search_view)?.actionView as SearchView

        searchView.setSearchableInfo(
            sm.getSearchableInfo(requireActivity().componentName)
        )

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?) : Boolean {
                if(!newText.isNullOrBlank())
                    searchTeams(newText)
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })

        menuItem.setOnActionExpandListener(object :
        MenuItem.OnActionExpandListener{

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


    private fun startActivityDetail (team : Team) {
        val intent =  Intent(this.context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.TEAM_KEY,team)
        startActivity(intent)
    }
}