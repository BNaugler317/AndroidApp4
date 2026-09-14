package com.example.androidapp4.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidapp4.R
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp4.data.ITunesApi
import com.example.androidapp4.data.Podcast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.launch
import retrofit2.create

class MainActivity : AppCompatActivity() {

    private lateinit var editTextSearch: EditText
    private lateinit var buttonSearch: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PodcastAdapter
    private lateinit var api: ITunesApi
    private var podcastList: MutableList<Podcast> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // init views
        editTextSearch = findViewById(R.id.editTextSearch)
        buttonSearch = findViewById(R.id.buttonSearch)
        recyclerView = findViewById(R.id.recyclerView)

        // setup RecyclerView
        adapter = PodcastAdapter(podcastList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // init Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(ITunesApi::class.java)

        buttonSearch.setOnClickListener {
            val query = editTextSearch.text.toString().trim()

            if (query.isNotEmpty()) {
                searchPodcasts(query)
            }

        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun searchPodcasts(query: String) {
        lifecycleScope.launch {
            try {
                val response = api.searchPodcast(query)

                // Filter results so only podcasts with titles of 3 or more words are displayed
                val filteredResults = response.results.filter { podcast ->
                    podcast.collectionName.trim().split("\\s+".toRegex()).size >= 3
                }
                podcastList.clear()
                podcastList.addAll(response.results)
                adapter.updateList(response.results)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}