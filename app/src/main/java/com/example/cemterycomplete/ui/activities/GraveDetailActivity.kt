package com.example.cemterycomplete.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cemterycomplete.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GraveDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grave_detail)
    }
}