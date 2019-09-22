package com.example.maitreapp.ui.prepare

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.maitreapp.MqttWrapper
import com.example.maitreapp.R
import kotlinx.android.synthetic.main.fragment_prepare.view.*



class PrepareFragment : Fragment() {

    lateinit private var globalContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        globalContext=context
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreate(savedInstanceState)
        val view: View = inflater!!.inflate(R.layout.fragment_prepare, container, false)
        MqttWrapper.wrapperCreate(globalContext.applicationContext)
        MqttWrapper.wrapperSetView(view)
        MqttWrapper.wrapperConnect()
        val switch=view.stopSwitch


        view.prepareBtn.setOnClickListener { view ->
            Log.d("PrepareBtn", "Selected")
            MqttWrapper.wrapperPublish("msg(prepare,dispatch,appAndroid,butlermind,prepare(go),1)", "unibo/qak/butlermind")
        }
        view.stopSwitch.setOnCheckedChangeListener { view, isChecked ->
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