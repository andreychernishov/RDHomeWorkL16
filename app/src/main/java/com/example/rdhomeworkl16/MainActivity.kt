package com.example.rdhomeworkl16

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val rcView: RecyclerView = findViewById(R.id.main_rc_view)

        val api = ApiClient.client.create(ApiInterface::class.java)

        api.getMemes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                       if (it.isNotEmpty()){
                           val item = it
                           val adapter = RecyclerViewAdapter(item, {
                           })
                           rcView.adapter = adapter
                       }
            },{
                println("$it")
            })

        rcView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        rcView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
    }
}

//data class MemesResponce(val data: Data)
//data class Data(val memes: List<Meme>)
//data class Meme(val name: String, val url: String)

