package com.example.rdhomeworkl16

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.internal.notify
import java.util.Collections

class MainActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val rcView: RecyclerView = findViewById(R.id.main_rc_view)

        val api = ApiClient.client.create(ApiInterface::class.java)
        val adapter = RecyclerViewAdapter(callBack = {})
        rcView.adapter = adapter
        rcView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        rcView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))


        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback(){
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                //dragFlag = 0 якщо елементи не рухаємо
                return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.END)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromIndex = viewHolder.adapterPosition
                val toIndex = target.adapterPosition
                Collections.swap(adapter.items, fromIndex,toIndex)
                adapter.notifyItemMoved(fromIndex,toIndex)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.END){
                    adapter.items.removeAt(viewHolder.adapterPosition)
                    adapter.notifyItemRemoved(viewHolder.adapterPosition)
                }
            }

        })

        itemTouchHelper.attachToRecyclerView(rcView)

        api.getMemes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                       if (it.isNotEmpty()){
                           val item = it
//                           adapter.updateItemList(item)
                           adapter.items = item
                           adapter.notifyDataSetChanged()
                       }
            },{
                println("$it")
            })


    }
}

//data class MemesResponce(val data: Data)
//data class Data(val memes: List<Meme>)
//data class Meme(val name: String, val url: String)

