package com.example.cemterycomplete.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.cemterycomplete.R
import com.example.cemterycomplete.databinding.ActivityCemeteryListBinding
import com.example.cemterycomplete.ui.adapters.CemeteryListAdapter
import com.example.cemterycomplete.ui.adapters.CemeteryListener
import com.example.cemterycomplete.ui.viewmodels.CemeteryDetailViewModel
import com.example.cemterycomplete.ui.viewmodels.CemeteryListViewModel
import com.example.cemterycomplete.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CemeteryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCemeteryListBinding
    private val viewModel: CemeteryListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cemetery_list)
        binding.lifecycleOwner = this




        val cemeteryListAdapter = CemeteryListAdapter(CemeteryListener {
            startActivity(
                Intent(this, CemeteryDetailActivity::class.java).apply {
                    putExtra(CemeteryDetailViewModel.CEMETERY_SELECTED, it)
                }

            )
        })


        viewModel.cemeteryList.observe(this, Observer {
            it?.let {
                cemeteryListAdapter.submitList(it)
            }
        })

        binding.cemeteryListRecyclerView.adapter = cemeteryListAdapter

        viewModel.cemeteryResponse.observe(this, Observer {
            it?.let {
                when(it.status){
                    Status.SUCCESS -> Toast.makeText(this, "Successfully refreshed cemetery list from network", Toast.LENGTH_SHORT).show()

                    Status.ERROR -> Toast.makeText(this, "Check network connection", Toast.LENGTH_SHORT).show()

                    Status.LOADING -> {

                    }
                }
            }
        })

        binding.addCemeteryFAB.setOnClickListener {
            startActivity(
                Intent(this, CreateCemeteryActivity::class.java)
            )
        }

        viewModel.sendNewCemeteriesToNetwork()

    }
}