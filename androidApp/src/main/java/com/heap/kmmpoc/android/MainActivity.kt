package com.heap.kmmpoc.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.heap.kmmpoc.shared.HeapSDK
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlin.random.Random
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.heap.kmmpoc.shared.cache.DatabaseDriverFactory
import com.heap.kmmpoc.shared.entity.Event
import kotlinx.coroutines.cancel




class MainActivity : AppCompatActivity() {
    private val mainScope = MainScope()
    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var progressBarView: FrameLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val sdk = HeapSDK(DatabaseDriverFactory(this))
private val rand = Random(42)

    private val eventsRvAdapter = EventsRvAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title="POC"
        setContentView(R.layout.activity_main)
        mainScope.launch {
            kotlin.runCatching {
                sdk.initSDK("Android")
            }.onSuccess {
                displayEvents()
            }.onFailure {
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
                Log.d("HEAP", it.localizedMessage.toString())
            }
        }
        // Views
        eventsRecyclerView = findViewById<RecyclerView>(R.id.Event_List)
        eventsRecyclerView.adapter = eventsRvAdapter
        eventsRecyclerView.layoutManager = LinearLayoutManager(this)

        progressBarView = findViewById(R.id.progressBar)
        // Buttons
        val seb: Button = findViewById(R.id.Store_Event)
        seb.setOnClickListener{

            mainScope.launch {
                kotlin.runCatching {
                    Log.d("HEAP", "eventId")
                    val eventId = rand.nextInt().toString()
                    Log.d("HEAP", eventId)
                    sdk.sendEvent(eventId)

                }.onSuccess {
                    Log.d("HEAP", "successful write")


                }.onFailure {
                    Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                    Log.d("HEAP", "successful failed ")

                }
            }
            displayEvents()

        }
    }
    private fun displayEvents() {
         progressBarView.isVisible = false
        mainScope.launch {
            kotlin.runCatching {
                sdk.getEvents()
            }.onSuccess {
                //it.map { event ->  Log.d("HEAP", event.ID.toString()) }
                if(it.isEmpty()){
                    progressBarView.isVisible = false
                } else {

                    eventsRvAdapter.events = it
                    eventsRvAdapter.notifyDataSetChanged()
                }
            }.onFailure {
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            progressBarView.isVisible = false
        }
    }
}
