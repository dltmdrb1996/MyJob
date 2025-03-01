package com.bottotop.register.register.alba

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bottotop.core.ext.isInvisible
import com.bottotop.core.ext.isVisible
import com.bottotop.core.ext.showToast
import com.bottotop.core.model.EventObserver
import com.bottotop.core.navigation.NavigationFlow
import com.bottotop.core.navigation.ToFlowNavigation
import com.bottotop.register.R
import com.bottotop.register.databinding.AlbaBottomSheetBinding
import com.bottotop.register.register.RegisterViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult


class AlbaBottomSheet(private val viewModel: RegisterViewModel) : BottomSheetDialogFragment() {

    private var _binding: AlbaBottomSheetBinding? = null
    private val binding get() = _binding!!

//    private val viewModel : RegisterViewModel by viewModels(
//        ownerProducer = { requireParentFragment() }
//    )


    override fun onStart() {
        super.onStart()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.alba_bottom_sheet, container, false)
        _binding?.apply {
            lifecycleOwner = this@AlbaBottomSheet
            viewModel = this@AlbaBottomSheet.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        completeRegister()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObserver(){
        viewModel.isLoading.observe(viewLifecycleOwner,{
            showLoading(it)
        })
        viewModel.startTime.observe(viewLifecycleOwner,{
            if(it.isNullOrEmpty()) {
                binding.tvStartNotice.isInvisible()
                return@observe
            }
            if(it.toInt()>=25) binding.tvStartNotice.isVisible()
            else binding.tvStartNotice.isInvisible()
        })
        viewModel.endTime.observe(viewLifecycleOwner,{
            if(it.isNullOrEmpty()){
                binding.tvEndNotice.isInvisible()
                return@observe
            }
            if(it.toInt()>=25) binding.tvEndNotice.isVisible()
            else binding.tvEndNotice.isInvisible()
        })
    }


    private fun showLoading(isLoading: Boolean) {
        if(isLoading) binding.nestedScrollView.alpha = 0.5f
        else binding.nestedScrollView.alpha = 1f
        binding.nestedScrollView.isClickable = isLoading
        binding.loadingView.isInProgress = isLoading
    }

    private fun completeRegister(){
        viewModel.albaComplete.observe(viewLifecycleOwner, EventObserver{
            if(it) (requireActivity() as ToFlowNavigation).navigateToFlow(NavigationFlow.HomeFlow("home"))
            else showToast("코드가 올바르지 않습니다.")
            this.dismiss()

        })
    }
}