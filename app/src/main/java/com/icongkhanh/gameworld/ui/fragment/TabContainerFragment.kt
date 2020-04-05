package com.icongkhanh.gameworld.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController

import com.icongkhanh.gameworld.R
import com.icongkhanh.gameworld.databinding.FragmentTabContainerBinding

/**
 * A simple [Fragment] subclass.
 */
class TabContainerFragment : Fragment() {

    lateinit var binding: FragmentTabContainerBinding

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
    }

    private fun setupBottomNav() {
        val navController = Navigation.findNavController(requireActivity(), R.id.tab_fragment_container)
        binding.bottomNav.setupWithNavController(navController)
    }
}
