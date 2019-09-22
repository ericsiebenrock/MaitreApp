package com.example.maitreapp

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.style.BackgroundColorSpan
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.maitreapp.ui.consult.ConsultFragment
import com.example.maitreapp.ui.mappa.MappaFragment
import kotlinx.android.synthetic.main.fragment_addfood.view.*
import kotlinx.android.synthetic.main.fragment_expose.view.*
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.MqttMessage


class MqttUtils constructor(var applicationContext: Context, var serverURI: String, var clientId: String) {

    var mqttAndroidClient = MqttAndroidClient(applicationContext, serverURI, clientId)
    lateinit var currentView:View


    fun setView(view: View){
        currentView=view
    }

    companion object{
        var tableViewAdapter = MyAdapter(ArrayList<String>())
        var pantryViewAdapter = MyAdapter(ArrayList<String>())
        var dishwasherViewAdapter = MyAdapter(ArrayList<String>())
        var rbrPosition = "home"
    }


    fun connect(){
        mqttAndroidClient = MqttAndroidClient(applicationContext, serverURI, clientId)
        mqttAndroidClient.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(reconnect: Boolean, serverURI: String) {

                if (reconnect) {
                    addToHistory("Reconnected to : $serverURI")
                    // Because Clean Session is true, we need to re-subscribe
                    //subscribeToTopic()
                } else {
                    addToHistory("Connected to: $serverURI")
                }
            }

            override fun connectionLost(cause: Throwable) {
                addToHistory("The Connection was lost.")
            }

            @Throws(Exception::class)
            override fun messageArrived(topic: String, message: MqttMessage) {
                var msg = String(message.payload)
                addToHistory("Incoming message: " + msg)
                val msgUtils = MsgUtils()
                if(msg.contains("addNOTDone")){
                    Toast.makeText(applicationContext, "Message from RBR: could not execute the add (food finished)", Toast.LENGTH_LONG).show()
                }
                else if(msg.contains("fridgeContent")){
                    if(currentView.id!=R.id.nav_expose) {
                        var foodNames = msgUtils.fridgeItems(msg)
                        var foodQtys = msgUtils.getFoodQuantities(msgUtils.getNumbers(msg))
                        var foodCodeList = msgUtils.getFoodCodes(msgUtils.getNumbers(msg))
                        var fridgeItems=ArrayList<String>()
                        var i=0
                        for(food in foodNames){
                            fridgeItems.add("$food                      ${foodQtys[i]}")
                            i++
                        }
                        val adapter = ArrayAdapter<String>(
                            applicationContext,
                            android.R.layout.simple_spinner_dropdown_item,
                            fridgeItems
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        currentView.spinner.adapter = adapter
                    }

                    else{
                        val viewAdapter : RecyclerView.Adapter<*>
                        val recycler:RecyclerView = currentView.recycler
                        var foodAnswerList = msgUtils.fridgeItems(msg)
                        var foodCodeList = msgUtils.getFoodCodes(msgUtils.getNumbers(msg))
                        var foodQtyList = msgUtils.getFoodQuantities(msgUtils.getNumbers(msg))
                        var exposeList = ArrayList<String>()
                        var i=0
                        for (food in foodAnswerList){
                        exposeList.add("$food                                ${foodCodeList[i]}                                     ${foodQtyList[i]} ")
                            i++
                        }
                        viewAdapter = MyAdapter(exposeList)
                        recycler.adapter=viewAdapter
                    }
                }
                else if(msg.contains("tableStateUpdate")){
                    println("TABLE STATE UPDATE:")
                    val viewAdapter : RecyclerView.Adapter<*>
                    //val recycler:RecyclerView = consult.view!!.table
                    val tableItems = msgUtils.tableItems(msg)
                    val tableQtys = msgUtils.getNumbers(msg)
                    var tableExpose = ArrayList<String>()
                    var i=0
                    for(food in tableItems){
                        tableExpose.add("$food                                     ${tableQtys[i]}")
                        i++
                    }
                    tableViewAdapter = MyAdapter(tableExpose)
                    //recycler.adapter=viewAdapter
                }
                else if(msg.contains("pantryStateUpdate")){
                    println("PANTRY STATE UPDATE:")
                    val viewAdapter : RecyclerView.Adapter<*>
                    //val recycler:RecyclerView = consult.view!!.pantry
                    val pantryItems = msgUtils.pantryItems(msg)
                    val pantryQtys = msgUtils.getNumbers(msg)
                    var pantryExpose = ArrayList<String>()
                    var i=0
                    for(food in pantryItems){
                        pantryExpose.add("$food                                    ${pantryQtys[i]}")
                        i++
                    }
                    println("PANTRY EXPOSE: $pantryExpose" )
                    pantryViewAdapter = MyAdapter(pantryExpose)
                    //recycler.adapter=viewAdapter
                }
                else if(msg.contains("dishwasherStateUpdate")){
                    println("DISHWASHER STATE UPDATE:")
                    val viewAdapter : RecyclerView.Adapter<*>
                    //val recycler:RecyclerView = currentView.dishwasher
                    val dishwasherItems = msgUtils.dishwasherItems(msg)
                    val dishwasherQtys = msgUtils.getNumbers(msg)
                    var dishwasherExpose = ArrayList<String>()
                    var i=0
                    for(food in dishwasherItems){
                        dishwasherExpose.add("$food                                    ${dishwasherQtys[i]}")
                        i++
                    }
                    dishwasherViewAdapter = MyAdapter(dishwasherExpose)
                    //recycler.adapter=viewAdapter
                }
                else if(msg.contains("addDone")){
                    Toast.makeText(applicationContext, "Message from RBR: Add Done", Toast.LENGTH_LONG).show()
                    rbrPosition="home"
                }
                else if(msg.contains("prepareDone")){
                    Toast.makeText(applicationContext, "Message from RBR: Prepare Done", Toast.LENGTH_LONG).show()
                    rbrPosition="home"
                }
                else if(msg.contains("clearDone")){
                    Toast.makeText(applicationContext, "Message from RBR: Clear Done", Toast.LENGTH_LONG).show()
                    rbrPosition="home"
                }
                else if(msg.contains("rbrPositionUpdate") && msg.contains("home")){
                    rbrPosition="home"
                }
                else if(msg.contains("rbrPositionUpdate") && msg.contains("fridge")){
                    rbrPosition="fridge"
                }
                else if(msg.contains("rbrPositionUpdate") && msg.contains("pantry")){
                    rbrPosition="pantry"
                }
                else if(msg.contains("rbrPositionUpdate") && msg.contains("table")){
                    rbrPosition="table"
                }
                else if(msg.contains("rbrPositionUpdate") && msg.contains("dishwasher")){
                    rbrPosition="dishwasher"
                }

            }

            override fun deliveryComplete(token: IMqttDeliveryToken) {

            }
        })

        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isAutomaticReconnect = true
        mqttConnectOptions.isCleanSession = false

        try {
            //addToHistory("Connecting to " + serverUri);
            mqttAndroidClient.connect(mqttConnectOptions, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    val disconnectedBufferOptions = DisconnectedBufferOptions()
                    disconnectedBufferOptions.isBufferEnabled = true
                    disconnectedBufferOptions.bufferSize = 100
                    disconnectedBufferOptions.isPersistBuffer = false
                    disconnectedBufferOptions.isDeleteOldestMessages = false
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions)
                    subscribeToTopic("unibo/qak/maitre")
                    //publishMessage()

                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    addToHistory("Failed to connect to: $serverURI")
                }
            })


        } catch (ex: MqttException) {
            ex.printStackTrace()
        }
    }

    private fun addToHistory(mainText: String) {
        println("LOG: $mainText")
        //mAdapter.add(mainText)
        //Snackbar.make(findViewById<T>(android.R.id.content), mainText, Snackbar.LENGTH_LONG)
        //   .setAction("Action", null).show()

    }

    fun subscribeToTopic(topic: String) {
        try {
            mqttAndroidClient.subscribe(topic, 0, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    addToHistory("Subscribed!")
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    addToHistory("Failed to subscribe")
                }
            })

        } catch (ex: MqttException) {
            System.err.println("Exception whilst subscribing")
            ex.printStackTrace()
        }

    }

    fun publishMessage(msg: String, topic: String) {

        try {
            val message = MqttMessage()
            val publishMessage = msg //"msg(prepare,dispatch,appAndroid,butlermind,prepare(go),1)"
            message.payload = publishMessage.toByteArray()
            mqttAndroidClient.publish(topic, message)
            addToHistory("Message Published")
            if (!mqttAndroidClient.isConnected()) {
                addToHistory(" messages in buffer.")
            }
        } catch (e: MqttException) {
            System.err.println("Error Publishing: " + e.message)
            e.printStackTrace()
        }

    }
}