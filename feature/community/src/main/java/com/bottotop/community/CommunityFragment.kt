package com.bottotop.community

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.bottotop.community.databinding.FragmentCommunityBinding
import com.bottotop.core.global.ShowLoading
import com.bottotop.core.base.BaseFragment
import com.bottotop.core.ext.setOnSingleClickListener
import com.bottotop.core.navigation.NavigationFlow
import com.bottotop.core.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityFragment :
    BaseFragment<FragmentCommunityBinding, CommunityViewModel>(R.layout.fragment_community, "커뮤니티_프래그먼트") {

    private val vm by viewModels<CommunityViewModel>()
    override val viewModel get() = vm
    private val adapter : CommunityAdapter by lazy { CommunityAdapter(viewModel) }
    lateinit var bottomSheet : CreateBottomSheet
    override fun setBindings() {
        _binding?.viewModel = viewModel
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheet = CreateBottomSheet(viewModel)
        initCommunity()
        observeLoading()
        obersveCreateSuccess()
        setBtn()
    }

    private fun observeLoading(){
        viewModel.isLoading.observe(viewLifecycleOwner, {
            Log.e(TAG, "observeLoading: ${it}", )
            (requireActivity() as ShowLoading).showLoading(it)
        })
    }

    private fun initCommunity(){
        binding.recyclerView.adapter = adapter
        viewModel.communityList.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })
    }

    fun setBtn(){
        binding.apply {
            communityBtnCreate.setOnClickListener {
                bottomSheet.show(childFragmentManager, bottomSheet.tag)
            }
        }
    }

    fun obersveCreateSuccess(){
        viewModel.success.observe(viewLifecycleOwner,{
            if(it){ bottomSheet.dismiss() }
        })
    }
}