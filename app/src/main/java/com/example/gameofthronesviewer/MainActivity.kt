package com.example.gameofthronesviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
 private lateinit var characterAdapter: CharacterAdapter
 private val characterList = mutableListOf<Pair<String, Triple<String, String, String>>>()

 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setContentView(R.layout.activity_main)

  val recyclerView: RecyclerView = findViewById(R.id.recyclerViewCharacters)
  recyclerView.layoutManager = LinearLayoutManager(this)
  characterAdapter = CharacterAdapter(this, characterList)
  recyclerView.adapter = characterAdapter

  fetchCharacterList()
 }

 private fun fetchCharacterList() {
  val client = AsyncHttpClient()
  client["https://thronesapi.com/api/v2/Characters", object : JsonHttpResponseHandler() {
   override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
    val jsonArray = json.jsonArray
    val newCharacterList = mutableListOf<Pair<String, Triple<String, String, String>>>()
    for (i in 0 until jsonArray.length()) {
     val character = jsonArray.getJSONObject(i)
     val imageUrl = character.getString("imageUrl")
     val fullName = character.getString("fullName")
     val title = character.getString("title")
     val family = character.getString("family")
     newCharacterList.add(Pair(imageUrl, Triple(fullName, title, family)))
    }
    Log.d("Character List", "Successfully fetched character list.")
    characterList.addAll(newCharacterList)
    characterAdapter.notifyItemRangeInserted(characterList.size - newCharacterList.size, newCharacterList.size)

    // Preload images using Glide
    for ((imageUrl, _) in newCharacterList) {
     Glide.with(this@MainActivity)
      .load(imageUrl)
      .fitCenter()
      .preload() // Preload images to improve scrolling performance
    }
   }

   override fun onFailure(
    statusCode: Int,
    headers: Headers?,
    errorResponse: String,
    throwable: Throwable?
   ) {
    Log.e("Character List Error", "Failed to fetch character list: $errorResponse")
   }
  }]
 }

}
