package com.icongkhanh.gameworld.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.icongkhanh.common.hideOrShow

import com.icongkhanh.gameworld.R
import com.icongkhanh.gameworld.databinding.FragmentTabContainerBinding
import com.icongkhanh.gameworld.viewmodel.TabContainerViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class TabContainerFragment : Fragment() {

    lateinit var binding: FragmentTabContainerBinding
    val vm by sharedViewModel<TabContainerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTabContainerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBottomNav()
        vm.isLoading.observe(viewLifecycleOwner, Observer {
            binding.progressBar.hideOrShow(it)
        })
    }

    private fun setupBottomNav() {
        val navController = Navigation.findNavController(requireActivity(), R.id.tab_fragment_container)
        binding.bottomNav.setupWithNavController(navController)
    }
}
