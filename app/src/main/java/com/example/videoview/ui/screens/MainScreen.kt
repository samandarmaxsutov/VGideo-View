package com.example.videoview.ui.screens

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.videoview.R
import com.example.videoview.databinding.ScreenMainBinding
import com.example.videoview.ui.adapters.VideoViewAdapter
import com.example.videoview.utils.checkForInternet
import com.example.videoview.utils.massage
import com.example.videoview.viewmodel.MainScreenViewModel
import com.example.videoview.viewmodel.impl.MainScreenViewModelImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainScreen : Fragment(R.layout.screen_main) {
    private val adapter=VideoViewAdapter()
    private val viewModel:MainScreenViewModel by viewModels<MainScreenViewModelImpl>()
    private val binding:ScreenMainBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewPager2.adapter=adapter
        val progress = ProgressDialog(requireContext())
        viewModel.progressLiveData.observe(viewLifecycleOwner){
            if(it){
                progress.setMessage("Ma'lumotlar yuklanmoqda...");
                progress.show();
            }else{
                progress.dismiss()
            }
        }
        viewModel.getAllLiveData.observe(viewLifecycleOwner){
            adapter.submitItems(it)
        }
        viewModel.massageLiveData.observe(viewLifecycleOwner){
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
        binding.swipeLayout.setOnRefreshListener {
            if (checkForInternet(requireContext())) {
                lifecycleScope.launch {
                    delay(2000)
                    binding.swipeLayout.isRefreshing = false
                }

            } else {
                massage(requireActivity(), "Internetga ulanmagan")
                binding.swipeLayout.isRefreshing = false
            }
        }
    }
}