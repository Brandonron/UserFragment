package com.example.userfragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.userfragment.ui.mine.MineFragment
import com.example.userfragment.ui.user.UserFragment
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val fragments = arrayOf(UserFragment(), MineFragment())
    private val itemIds = arrayOf(R.id.navigation_user, R.id.navigation_mine)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Fresco.initialize(this)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        viewPager.adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount(): Int = fragments.size
            override fun createFragment(position: Int): Fragment = fragments[position]
        }

        viewPager.offscreenPageLimit = fragments.size

        //secondaryVP.isUserInputEnabled = false
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                navView.selectedItemId = itemIds[position]
            }
        })

        navView.setOnNavigationItemSelectedListener {
            viewPager.currentItem = itemIds.indexOf(it.itemId)
            true
        }
    }
}