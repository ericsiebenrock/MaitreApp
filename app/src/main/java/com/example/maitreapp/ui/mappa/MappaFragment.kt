package com.example.maitreapp.ui.mappa

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maitreapp.MqttUtils
import com.example.maitreapp.MqttWrapper
import com.example.maitreapp.R
import kotlinx.android.synthetic.main.fragment_addfood.view.*
import kotlinx.android.synthetic.main.fragment_expose.view.*
import kotlinx.android.synthetic.main.fragment_map.view.*
import kotlinx.android.synthetic.main.fragment_prepare.view.*

class MappaFragment : Fragment() {

    lateinit private var globalContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        globalContext=context
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreate(savedInstanceState)
        val view: View = inflater!!.inflate(R.layout.fragment_map, container, false)
        val home=view.home
        val table=view.table
        val fridge=view.fridge
        val pantry=view.pantry
        val dishwasher=view.dishwasher
        view.button.setOnClickListener { view ->
            Log.d("AggiornaBtn", "Selected")
            if(MqttUtils.rbrPosition=="home"){
                home.setBackgroundColor(Color.RED)
                table.setBackgroundColor(Color.parseColor("#303030"))
                pantry.setBackgroundColor(Color.parseColor("#303030"))
                dishwasher.setBackgroundColor(Color.parseColor("#303030"))
                fridge.setBackgroundColor(Color.parseColor("#303030"))
            }
            if(MqttUtils.rbrPosition=="fridge"){
                home.setBackgroundColor(Color.parseColor("#303030"))
                table.setBackgroundColor(Color.parseColor("#303030"))
                pantry.setBackgroundColor(Color.parseColor("#303030"))
                dishwasher.setBackgroundColor(Color.parseColor("#303030"))
                fridge.setBackgroundColor(Color.RED)
            }
            if(MqttUtils.rbrPosition=="pantry"){
                home.setBackgroundColor(Color.parseColor("#303030"))
                table.setBackgroundColor(Color.parseColor("#303030"))
                pantry.setBackgroundColor(Color.RED)
                dishwasher.setBackgroundColor(Color.parseColor("#303030"))
                fridge.setBackgroundColor(Color.parseColor("#303030"))
            }
            if(MqttUtils.rbrPosition=="table"){
                home.setBackgroundColor(Color.parseColor("#303030"))
                table.setBackgroundColor(Color.RED)
                pantry.setBackgroundColor(Color.parseColor("#303030"))
                dishwasher.setBackgroundColor(Color.parseColor("#303030"))
                fridge.setBackgroundColor(Color.parseColor("#303030"))
            }
            if(MqttUtils.rbrPosition=="dishwasher"){
                home.setBackgroundColor(Color.parseColor("#303030"))
                table.setBackgroundColor(Color.parseColor("#303030"))
                pantry.setBackgroundColor(Color.parseColor("#303030"))
                dishwasher.setBackgroundColor(Color.RED)
                fridge.setBackgroundColor(Color.parseColor("#303030"))
            }
        }

        return view
    }
}