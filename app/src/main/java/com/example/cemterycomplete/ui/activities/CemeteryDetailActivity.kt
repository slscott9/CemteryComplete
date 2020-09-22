package com.example.cemterycomplete.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.cemterycomplete.R
import com.example.cemterycomplete.databinding.ActivityCemeteryDetailBinding
import com.example.cemterycomplete.ui.adapters.GraveListAdapter
import com.example.cemterycomplete.ui.adapters.GraveListListener
import com.example.cemterycomplete.ui.viewmodels.CemeteryDetailViewModel
import com.example.cemterycomplete.ui.viewmodels.GraveDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CemeteryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCemeteryDetailBinding
    private val viewModel : CemeteryDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cemetery_detail)
        binding.lifecycleOwner = this
        binding.cemeteryDetailViewModel = viewModel

        val graveListAdapter = GraveListAdapter(GraveListListener {
            startActivity(
                Intent(this, GraveDetailActivity::class.java).apply {
                    putExtra(GraveDetailViewModel.GRAVE_SELECTED, it)
                }
            )
        })

        viewModel.graveList.observe(this, Observer {
            it?.let {
                graveListAdapter.submitList(it)
            }
        })


        binding.graveRecyclerView.adapter = graveListAdapter
    }
}