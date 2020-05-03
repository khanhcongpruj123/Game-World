package com.icongkhanh.gameworld.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.icongkhanh.common.event.EventObserver
import com.icongkhanh.gameworld.R
import com.icongkhanh.gameworld.adapter.ListGameBookMarkAdapter
import com.icongkhanh.gameworld.databinding.FragmentBookmarkBinding
import com.icongkhanh.gameworld.viewmodel.BookmarkFragmentViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class BookmarkFragment : Fragment() {

    lateinit var adapter: ListGameBookMarkAdapter
    val vm by viewModel<BookmarkFragmentViewModel>()
    lateinit var binding: FragmentBookmarkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookmarkBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListGameBookMarkAdapter(requireContext())
        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@BookmarkFragment.adapter
        }

        vm.listGame.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        vm.navigateGameDetail.observe(viewLifecycleOwner, EventObserver {
            val action =
                TabContainerFragmentDirections.actionTabContainerFragmentToGameDetailFragment(it)
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                .navigate(action)
        })
    }

}
