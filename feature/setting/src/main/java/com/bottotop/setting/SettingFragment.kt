package com.bottotop.setting

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.viewModels
import com.bottotop.core.base.BaseFragment
import com.bottotop.setting.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


@AndroidEntryPoint
class SettingFragment :
    BaseFragment<FragmentSettingBinding, SettingViewModel>(R.layout.fragment_setting, "셋팅_프래그먼트") {

    private val vm by viewModels<SettingViewModel>()
    override val viewModel get() = vm

    override fun setBindings() {
        _binding?.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restartSetting()
        setDarkMode()
    }

    private fun restartSetting(){
        if (AppCompatDelegate.getDefaultNightMode()== MODE_NIGHT_YES){
            val tab: TabLayout.Tab? = binding.tabLayout.getTabAt(1)
            tab?.select()
        }
    }

    private fun setDarkMode(){
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                if(tab.text=="켜기"){
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                }else{
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }
}