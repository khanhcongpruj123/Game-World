package com.icongkhanh.gameworld.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.icongkhanh.common.event.EventObserver
import com.icongkhanh.common.showOrHide
import com.icongkhanh.gameworld.adapter.ListSearchGameAdapter
import com.icongkhanh.gameworld.databinding.FragmentSearchBinding
import com.icongkhanh.gameworld.viewmodel.SearchFragmentViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {

    private lateinit var adapter: ListSearchGameAdapter
    private lateinit var binding: FragmentSearchBinding

    private val vm by viewModel<SearchFragmentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setupWithNavController(findNavController())

        adapter = ListSearchGameAdapter(requireContext())
        binding.list.apply {
            adapter = this@SearchFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        subscribeUi()
    }

    private fun subscribeUi() {

        vm.searchViewUiModel.observe(viewLifecycleOwner, Observer {
            binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d("AppLog", newText)
                    it.onTextChange(newText)
                    return true
                }
            })
        })

        vm.listSearchGame.observe(viewLifecycleOwner, Observer {
            Log.d("AppLog", "list: ${it.toString()}")
            binding.list.postDelayed({
                adapter.submitList(it)
            }, 200)
        })

        vm.navigateGameDetail.observe(viewLifecycleOwner, EventObserver {
            val action = SearchFragmentDirections.actionSearchFragmentToGameDetailFragment(it)
            findNavController().navigate(action)
        })

        vm.loadingViewVisiale.observe(viewLifecycleOwner, EventObserver {
            Log.d("AppLog", "show or hide: ${it}")
            binding.loadingView.showOrHide(it)
        })
    }


}
