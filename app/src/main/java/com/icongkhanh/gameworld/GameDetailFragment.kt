package com.icongkhanh.gameworld

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.icongkhanh.gameworld.databinding.FragmentGameDetailBinding
import com.icongkhanh.gameworld.domain.model.Game

/**
 * A simple [Fragment] subclass.
 */
class GameDetailFragment : Fragment() {

    lateinit var binding: FragmentGameDetailBinding
    val args by navArgs<GameDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setupWithNavController(findNavController())

        val game = args.gameItem
    }

}
