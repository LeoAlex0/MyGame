package com.example.leo.mygame

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val action = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_home -> {
                action.show(fragmentShareModule)
                        .hide(fragmentGameModule)
                        .hide(fragmentHistoryModule)
            }
            R.id.navigation_dashboard -> {
                action.show(fragmentGameModule)
                        .hide(fragmentShareModule)
                        .hide(fragmentHistoryModule)
            }
            else -> {
                action.show(fragmentHistoryModule)
                        .hide(fragmentGameModule)
                        .hide(fragmentShareModule)
            }
        }.commit()
        true
    }

    private val fragmentGameModule = GameModule.newInstance()
    private val fragmentShareModule = HomePageModule()
    private val fragmentHistoryModule = HistoryModule.newInstance(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_dashboard

        time_hint = getString(R.string.time_hint)
        score_hint = getString(R.string.score_hint)

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragmentShareModule)
                .add(R.id.fragment_container, fragmentHistoryModule)
                .add(R.id.fragment_container, fragmentGameModule).commit()
    }
}