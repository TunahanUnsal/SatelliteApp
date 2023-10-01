package com.example.project.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
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
    private val updateIntervalMillis = 3000L
    private var currentPositionIndex = 0

    private val handler = Handler()

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
            binding.swipeRefresh.isRefreshing = false
            //viewModel.getDetail(this@DetailActivity, id.toInt())
        }

        binding.back.setOnClickListener {
            finish()
        }

        viewModel.getDetail(this@DetailActivity, id.toInt())

        viewModel.satelliteDetailFlow.observe(this) { data ->
            binding.cost.text = data?.costPerLaunch.toString()
            binding.date.text = data?.firstFlight
            binding.height.text = data?.height.toString()
            binding.swipeRefresh.isRefreshing = false
        }

        viewModel.getPosition(this@DetailActivity, id)
        updatePosition()
    }

    @SuppressLint("SetTextI18n")
    private fun updatePosition() {
        viewModel.positionFlow.observe(this) { data ->
            if (data != null && currentPositionIndex < data.positions.size) {
                val currentPosition = data.positions[currentPositionIndex]
                binding.lastPosition.text =
                    "(" + currentPosition.posX.toString() + "-" + currentPosition.posY.toString() + ")"
                currentPositionIndex++
                if (currentPositionIndex >= data.positions.size) {
                    currentPositionIndex = 0
                }
                handler.postDelayed({
                    updatePosition()
                }, updateIntervalMillis)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
