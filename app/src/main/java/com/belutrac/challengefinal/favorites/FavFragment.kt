package com.belutrac.challengefinal.favorites

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.belutrac.challengefinal.R
import com.belutrac.challengefinal.Team
import com.belutrac.challengefinal.databinding.FragmentFavBinding
import com.belutrac.challengefinal.detail.DetailActivity

class FavFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val adapter = FavAdapter(requireActivity())
        val recyclerView = binding.recyclerView
        val viewModel = ViewModelProvider(this)[FavViewModel::class.java]
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapter

        viewModel.favList.observe(requireActivity(), { list ->
            adapter.submitList(list)
        })

        adapter.onItemClickListener = {
            startActivityDetail(it)
        }

        setHasOptionsMenu(true)

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.removeItem(R.id.search_view)
    }

    private fun startActivityDetail(team: Team) {
        val intent = Intent(this.context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.TEAM_KEY, team)
        startActivity(intent)
    }

}