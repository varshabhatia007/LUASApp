package com.example.luasapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luasapp.common.UIState
import com.example.luasapp.databinding.ActivityMainBinding
import com.example.luasapp.model.StopInfo
import com.example.luasapp.presentation.ForecastViewModel
import com.example.luasapp.presentation.StopAbvEnum
import com.example.luasapp.ui.TramInfoAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ForecastViewModel by viewModels()
    private val tramInfoAdapter: TramInfoAdapter = TramInfoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpObserver()
        setUpView()
    }

    private fun setUpView() {
        binding.swipeRefresh.setOnRefreshListener {
            fetchForeCastInfo()
        }

        binding.refreshButton.setOnClickListener {
            fetchForeCastInfo()
        }

        binding.tramInfoView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = tramInfoAdapter
        }
    }

    @SuppressLint("NewApi")
    private fun fetchForeCastInfo() {
        viewModel.fetchTramsForecastInfo()
    }

    @SuppressLint("NewApi")
    private fun setUpObserver() {
        viewModel.stopInfoState.observe(this) { state ->
            when (state) {
                is UIState.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
                is UIState.Success -> {
                    binding.progress.visibility = View.GONE
                    showSuccess(state)
                }
                is UIState.Error -> {
                    binding.progress.visibility = View.GONE
                    showError()
                }
            }
        }
        fetchForeCastInfo()
    }

    private fun showError() {
        binding.swipeRefresh.isRefreshing = false
        Toast.makeText(this, "ERROR..", Toast.LENGTH_LONG).show()
    }

    private fun showSuccess(state: UIState.Success<StopInfo>) {
        binding.swipeRefresh.isRefreshing = false
        updateViews(state.data)
    }

    @SuppressLint("NewApi", "SetTextI18n")
    private fun updateViews(info: StopInfo) {
        binding.stopName.text = info.stop
        binding.dateTime.text = """Current Date & Time : ${LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyy HH:MM:SS"))}"""
        binding.message.text = info.message
        tramInfoAdapter.apply {
            this.submit(
                when (info.stopAbv.equals(StopAbvEnum.MARLBOROUGH.abv, true)) {
                    true -> info.directionInfos.lastOrNull()?.tramInfos
                    else -> info.directionInfos.firstOrNull()?.tramInfos
                }
            )
        }
    }
}