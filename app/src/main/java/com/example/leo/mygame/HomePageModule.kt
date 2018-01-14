package com.example.leo.mygame

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_homepage_module.*

class HomePageModule : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_homepage_module, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_homepage.setOnClickListener {
            val intent = Intent(ACTION_VIEW, Uri.parse("https://github.com/LeoAlex0/MyGame"))
            startActivity(intent)
        }
    }
}
