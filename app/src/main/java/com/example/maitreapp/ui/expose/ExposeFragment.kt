package com.example.maitreapp.ui.expose

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
import com.example.maitreapp.MqttWrapper
import com.example.maitreapp.R
import kotlinx.android.synthetic.main.fragment_addfood.view.*
import kotlinx.android.synthetic.main.fragment_expose.view.*

class ExposeFragment : Fragment() {

    lateinit private var globalContext: Context
    lateinit private var viewManager:RecyclerView.LayoutManager
    lateinit private var recycler:RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        globalContext=context
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreate(savedInstanceState)
        val view: View = inflater!!.inflate(R.layout.fragment_expose, container, false)
        view.id=R.id.nav_expose
        MqttWrapper.wrapperCreate(globalContext.applicationContext)
        MqttWrapper.wrapperSetView(view)
        MqttWrapper.wrapperConnect()
        val send = view.button2
        recycler = view.recycler

        view.button5.setOnClickListener { view ->
            Log.d("ExposeBtn", "Selected")
            MqttWrapper.wrapperPublish("msg(expose,dispatch,appAndroid,fridgemind,expose(go),1)", "unibo/qak/fridgemind")
        }

        viewManager=LinearLayoutManager(globalContext)
        recycler.setHasFixedSize(true)
        recycler.layoutManager=viewManager
        // Return the fragment view/layout
        return view
    }
}