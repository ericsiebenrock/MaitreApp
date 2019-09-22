package com.example.maitreapp.ui.clear

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.maitreapp.MqttWrapper
import com.example.maitreapp.R
import kotlinx.android.synthetic.main.fragment_clear.view.*

class ClearFragment : Fragment() {

    lateinit private var globalContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        globalContext=context
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreate(savedInstanceState)
        val view: View = inflater!!.inflate(R.layout.fragment_clear, container, false)
        MqttWrapper.wrapperCreate(globalContext.applicationContext)
        MqttWrapper.wrapperSetView(view)
        MqttWrapper.wrapperConnect()
        val switch= view.switch2

        view.button3.setOnClickListener { view ->
            Log.d("ClearBtn", "Selected")
            MqttWrapper.wrapperPublish("msg(clear,dispatch,appAndroid,butlermind,clear(go),1)", "unibo/qak/butlermind")
        }

        view.switch2.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                MqttWrapper.wrapperPublish("msg(stopAppl,event,js,none,stopAppl(go),1)", "unibo/qak/events")
                switch.text="REACTIVATE"
            } else {
                MqttWrapper.wrapperPublish("msg(reactivateAppl,event,appAndroid,none,reactivateAppl(go),1)", "unibo/qak/events")
                switch.text="STOP"
            }
        }

        // Return the fragment view/layout
        return view
    }
}