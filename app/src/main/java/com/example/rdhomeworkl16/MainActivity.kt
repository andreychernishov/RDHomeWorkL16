package com.example.rdhomeworkl16

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.Collections

class MainActivity: AppCompatActivity(),RecyclerViewAdapter.Listener {
    var detailsFragment = supportFragmentManager.findFragmentById(R.id.frame_fragm) as? DetailsFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val rcView: RecyclerView = findViewById(R.id.main_rc_view)

        val api = ApiClient.client.create(ApiInterface::class.java)
        val adapter = RecyclerViewAdapter(callBack = {}, listener = this)
        rcView.adapter = adapter
        rcView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                //dragFlag = 0 якщо елементи не рухаємо
                return makeMovementFlags(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.END
                )
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromIndex = viewHolder.adapterPosition
                val toIndex = target.adapterPosition
                Collections.swap(adapter.items, fromIndex, toIndex)
                adapter.notifyItemMoved(fromIndex, toIndex)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.END) {
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
                if (it.isNotEmpty()) {
                    val item = it
                    adapter.items = item
                    adapter.notifyDataSetChanged()
                }
            }, {
                println("$it")
            })

    }
    override fun onClick(itemClicked: JsData) {

        detailsFragment?.arguments?.putString("TAG", itemClicked.images.md)

        if(detailsFragment != null){
//            val image = detailsFragment!!.view?.findViewById<ImageView>(R.id.image)
//            Glide.with(image!!)
//                .load(itemClicked.images.md)
//                .into(image)
//            detailsFragment!!.name = itemClicked.name
//            detailsFragment!!.gender = itemClicked.appearance.gender
//            detailsFragment!!.hairColor = itemClicked.appearance.hairColor
//            detailsFragment!!.eyeColor = itemClicked.appearance.eyeColor
//            detailsFragment!!.placeBirth = itemClicked.biography.placeOfBirth
//            detailsFragment!!.height = itemClicked.appearance.height.toString()
//            detailsFragment!!.weight = itemClicked.appearance.weight.toString()

        }else{
            val detailsFragment = DetailsFragment()
//            detailsFragment.arguments?.putString("TAG", itemClicked.images.md)
            detailsFragment.arguments = bundleOf("TAG" to itemClicked.images.md)
            detailsFragment.name = itemClicked.name
            detailsFragment.gender = itemClicked.appearance.gender
            detailsFragment.hairColor = itemClicked.appearance.hairColor
            detailsFragment.eyeColor = itemClicked.appearance.eyeColor
            detailsFragment.placeBirth = itemClicked.biography.placeOfBirth
            detailsFragment.height = itemClicked.appearance.height.toString()
            detailsFragment.weight = itemClicked.appearance.weight.toString()
            supportFragmentManager.beginTransaction()
                .add(R.id.main_cont,detailsFragment)
                .addToBackStack("details_fragment")
                .commit()
        }
    }
}


