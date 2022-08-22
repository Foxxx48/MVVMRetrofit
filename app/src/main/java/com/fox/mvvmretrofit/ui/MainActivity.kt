package com.fox.mvvmretrofit.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fox.mvvmretrofit.R
import com.fox.mvvmretrofit.adapters.MoviesAdapter
import com.fox.mvvmretrofit.databinding.ActivityMainBinding
import com.fox.mvvmretrofit.repository.MoviesRepository
import com.fox.mvvmretrofit.utils.Resource
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: MoviesViewModel
    lateinit var moviesAdapter : MoviesAdapter
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = MoviesRepository()
        val provider = MoviesViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, provider).get(MoviesViewModel::class.java)

        setRecyclerView(this)

        viewModel.moviesPage.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        moviesAdapter.differ.submitList(newsResponse.items)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })


    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    fun setRecyclerView(context: Context){
        moviesAdapter = MoviesAdapter()
        rvMovies.apply {
            adapter=moviesAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
    }
}