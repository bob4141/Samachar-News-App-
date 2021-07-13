package com.example.samachar

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SamacharItemClicked {

    private lateinit var mAdapter: SamacharListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = SamacharListAdapter(this)
        recyclerview.adapter = mAdapter
    }
    private fun fetchData(){
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/business/in.json"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            {
                val samacharJsonArray = it.getJSONArray("articles")
                val samacharArray = ArrayList<Samachar>()
                for(i in 0 until samacharJsonArray.length()){
                    val samacharJsonObject = samacharJsonArray.getJSONObject(i)
                    val samachar = Samachar(
                        samacharJsonObject.getString("title"),
                        samacharJsonObject.getString("author"),
                        samacharJsonObject.getString("url"),
                        samacharJsonObject.getString("urlToImage"),
                        samacharJsonObject.getString("publishedAt")

                    )
                    samacharArray.add(samachar)
                }
                mAdapter.updateSamachar(samacharArray)
            },
            {}
        )

        // Access the RequestQueue through the singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: Samachar) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}