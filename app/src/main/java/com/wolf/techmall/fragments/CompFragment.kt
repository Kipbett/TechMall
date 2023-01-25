package com.wolf.techmall.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.wolf.techmall.R


class CompFragment : Fragment() {

    lateinit var welc:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_comp, container, false)

        welc = view.findViewById(R.id.welcome_txt)

        return view
    }


}