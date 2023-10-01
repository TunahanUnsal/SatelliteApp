package com.example.project.ui.list

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.project.databinding.ActivityListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var viewModel: ListActivityVM


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ListActivityVM::class.java]
        setContentView(binding.root)

        viewModel.setList(binding.satelliteListView, this@ListActivity)
        viewModel.setupUI(binding.viewGeneral, this@ListActivity)
        viewModel.search(binding.searchEditText, binding.swipeRefresh)


        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getList(this@ListActivity, binding.swipeRefresh)
        }

        binding.swipeRefresh.setOnRefreshListener {
            binding.searchEditText.text.clear()
            binding.swipeRefresh.isRefreshing = false
            //viewModel.getData(this,binding.swipeRefresh)
        }

    }
}