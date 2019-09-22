package com.example.maitreapp.ui.addfood

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.maitreapp.MqttWrapper
import com.example.maitreapp.MsgUtils
import com.example.maitreapp.R
import kotlinx.android.synthetic.main.fragment_addfood.view.*


class addFoodFragment : Fragment() {

    lateinit private var globalContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        globalContext=context
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        super.onCreate(savedInstanceState)
        val view: View = inflater!!.inflate(R.layout.fragment_addfood, container, false)
        MqttWrapper.wrapperCreate(globalContext.applicationContext)
        MqttWrapper.wrapperSetView(view)
        MqttWrapper.wrapperConnect()
        val send = view.button2
        val switch=view.switch1
        val spinner = view.spinner

        view.add.setOnClickListener { view ->
            Log.d("AddBtn", "Selected")
            MqttWrapper.wrapperPublish("msg(expose,dispatch,appAndroid,fridgemind,expose(go),1)", "unibo/qak/fridgemind")
            send.visibility=View.VISIBLE
            spinner.visibility=View.VISIBLE
        }

        switch.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                MqttWrapper.wrapperPublish("msg(stopAppl,event,js,none,stopAppl(go),1)", "unibo/qak/events")
                switch.text="REACTIVATE"
            } else {
                switch.text="STOP"
                MqttWrapper.wrapperPublish("msg(reactivateAppl,event,appAndroid,none,reactivateAppl(go),1)", "unibo/qak/events")
            }
        }


        send.setOnClickListener{ view->
            println("selectedItemPosition: "+spinner.selectedItemPosition)
            var codeToSend = MsgUtils.lastFoodCodes[spinner.selectedItemPosition]
            println("code to send: $codeToSend")
            MqttWrapper.wrapperPublish("msg(add,event,appAndroid,none,add($codeToSend),1)", "unibo/qak/butlermind")
        }

        // Return the fragment view/layout
        return view
    }
}