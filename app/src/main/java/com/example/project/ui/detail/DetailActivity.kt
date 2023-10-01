package com.example.project.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.project.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailActivityVM
    private lateinit var id: String
    private lateinit var name: String

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[DetailActivityVM::class.java]
        setContentView(binding.root)

        id = intent.getStringExtra("id").toString()
        name = intent.getStringExtra("name").toString()

        binding.name.text = name

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            viewModel.getDetail(this@DetailActivity)
        }

        viewModel.getDetail(this@DetailActivity)
        viewModel.getPosition(this@DetailActivity)

        viewModel.satelliteDetailFlow.observe(this) { data ->
            val model = data.find { it.id.toString() == id }
            binding.cost.text = model?.costPerLaunch.toString()
            binding.date.text = model?.firstFlight
            binding.height.text = model?.height.toString()
            binding.swipeRefresh.isRefreshing = false
        }

        viewModel.positionFlow.observe(this) { data ->
            if (data != null) {
                viewModel.positionList = data.list
                val listPosition = viewModel.positionList.find { it.id == id }

                if (listPosition != null) {
                    binding.lastPosition.text =
                        "(" + listPosition.positions.last().posX.toString() + "-" + listPosition.positions.last().posY.toString() + ")"

                }
            }
        }
    }

}