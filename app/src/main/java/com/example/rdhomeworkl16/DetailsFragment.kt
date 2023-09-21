package com.example.rdhomeworkl16

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class DetailsFragment: Fragment() {
    private var heroName: TextView? = null
    private var image: ImageView? = null
    private var heroGender: TextView? = null
    private var heroRace: TextView? = null
    private var heroHeight: TextView? = null
    private var heroPowerStats: TextView? = null
    private var heroWeight: TextView? = null
    private var heroPlaceBirth: TextView? = null
    private var heroEyeColor: TextView? = null
    private var heroHairColor: TextView? = null
    var name = ""
    var gender = ""
    var race = ""
    var height = ""
    var weight = ""
    var eyeColor = ""
    var hairColor =""
    var placeBirth = ""
    var powerStats = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image = view.findViewById(R.id.image)
        heroName = view.findViewById(R.id.fragment_tv_name)
        heroGender = view.findViewById(R.id.fragment_tv_gender)
        heroRace = view.findViewById(R.id.fragment_tv_race)
        heroHeight = view.findViewById(R.id.fragment_tv_height)
        heroWeight = view.findViewById(R.id.fragment_tv_weight)
        heroPlaceBirth = view.findViewById(R.id.fragment_tv_place_birth)
        heroPowerStats = view.findViewById(R.id.fragment_tv_powerstats)
        heroEyeColor = view.findViewById(R.id.fragment_tv_eye_color)
        heroHairColor = view.findViewById(R.id.fragment_tv_hair_color)

        show()
    }
    fun show(){
        heroName?.text = name
        heroGender?.text = gender
        heroRace?.text = race
        heroHeight?.text = height
        heroWeight?.text = weight
        heroPlaceBirth?.text = placeBirth
        heroPowerStats?.text = powerStats
        heroEyeColor?.text = eyeColor
        heroHairColor?.text = hairColor
        image?.let {
            Glide.with(it)
                .load(arguments?.getString("TAG"))
                .into(image!!)
        }
    }
}