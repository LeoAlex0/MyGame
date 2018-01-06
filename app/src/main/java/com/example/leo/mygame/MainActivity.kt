package com.example.leo.mygame

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), GameModule.GameControlListener {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val action = fragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_home -> {
                action.show(fragmentShareModule)
                        .hide(fragmentGameModule)
                        .hide(fragmentHistoryModule)
                        .commit()
                true
            }
            R.id.navigation_dashboard -> {
                action.show(fragmentGameModule)
                        .hide(fragmentShareModule)
                        .hide(fragmentHistoryModule)
                        .commit()
                true
            }
            R.id.navigation_notifications -> {
                action.show(fragmentHistoryModule)
                        .hide(fragmentGameModule)
                        .hide(fragmentShareModule)
                        .commit()
                true
            }
            else -> false
        }
    }

    private val fragmentGameModule = GameModule.newInstance(ByteArray(16) { 0 })
    //TODO: 用其他的模块替代
    private val fragmentShareModule = GameModule.newInstance(ByteArray(16) { 0 })
    private val fragmentHistoryModule = GameModule.newInstance(ByteArray(16) { 0 })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_dashboard

        fragmentManager.beginTransaction().add(R.id.fragment_container, fragmentShareModule)
                .add(R.id.fragment_container, fragmentHistoryModule)
                .add(R.id.fragment_container, fragmentGameModule).commit()
    }

    override fun onGameOverListener() {
        navigation.visibility = View.VISIBLE
    }

    override fun onGameStartListener() {
        navigation.visibility = View.INVISIBLE
    }
}