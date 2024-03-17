package com.timur.vk_customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.timur.vk_customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val fragList = listOf(Fragment1.newInstance(), Fragment2.newInstance(), Fragment3.newInstance())
    val fragListTitles = listOf("Small clock", "Mid Clock", "Big Clock")
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = VpAdapter(this, fragList)
        binding.vp2.adapter = adapter
        TabLayoutMediator(binding.TabLayout, binding.vp2){
            tab, pos -> tab.text = fragListTitles[pos]
        }.attach()
    }
}