package com.example.maitreapp

import android.content.Context
import android.view.View
import java.security.AccessControlContext

class MqttWrapper{

    companion object {
        internal var created=false
        internal var connected=false
        lateinit internal var mqttUtils: MqttUtils;

        fun wrapperCreate(globalContext: Context) {
            if(!created) {
                mqttUtils = MqttUtils(
                    globalContext.applicationContext,
                    "tcp://10.201.16.230",
                    "maitreApp2"
                )
                created=true
            }
        }

        fun wrapperConnect() {
            if(!connected){
                mqttUtils.connect()
                connected=true
            }
        }

        fun wrapperPublish(msg: String, topic: String) {
            mqttUtils.publishMessage(msg, topic)
        }

        fun wrapperSetView(view: View){
            mqttUtils.setView(view)
        }
    }

}