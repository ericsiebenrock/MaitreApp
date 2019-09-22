package com.example.maitreapp.ui.consult

import android.content.Context
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
import kotlinx.android.synthetic.main.fragment_clear.view.*
import kotlinx.android.synthetic.main.fragment_consult.view.*

class ConsultFragment : Fragment() {

    lateinit private var globalContext: Context
    lateinit private var viewManagerTable: RecyclerView.LayoutManager
    lateinit private var viewManagerPantry: RecyclerView.LayoutManager
    lateinit private var viewManagerDishwasher: RecyclerView.LayoutManager
    lateinit private var table: RecyclerView
    lateinit private var pantry: RecyclerView
    lateinit private var dishwasher: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        globalContext=context
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreate(savedInstanceState)
        val view: View = inflater!!.inflate(R.layout.fragment_consult, container, false)

        table = view.table
        pantry = view.pantry
        dishwasher = view.dishwasher
        val p=view.pantry
        val t=view.table
        val d=view.dishwasher
        viewManagerTable= LinearLayoutManager(globalContext)
        viewManagerPantry= LinearLayoutManager(globalContext)
        viewManagerDishwasher= LinearLayoutManager(globalContext)
        table.setHasFixedSize(true)
        table.layoutManager=viewManagerTable
        pantry.setHasFixedSize(true)
        pantry.layoutManager=viewManagerPantry
        dishwasher.setHasFixedSize(true)
        dishwasher.layoutManager=viewManagerDishwasher


        view.button6.setOnClickListener { view ->
            Log.d("ConsultBtn", "Selected")
            p.visibility=View.VISIBLE
            t.visibility=View.VISIBLE
            d.visibility=View.VISIBLE

            table.adapter=MqttUtils.tableViewAdapter
            pantry.adapter=MqttUtils.pantryViewAdapter
            dishwasher.adapter= MqttUtils.dishwasherViewAdapter
        }

        // Return the fragment view/layout
        return view
    }
}