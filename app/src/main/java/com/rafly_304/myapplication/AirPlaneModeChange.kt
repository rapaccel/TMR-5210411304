package com.rafly_304.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AirPlaneModeChange:BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirplaneEnabled=intent?.getBooleanExtra("state",false)?:return
        if (isAirplaneEnabled){
            Toast.makeText(context,"AirPlane Mode Enabled",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"AirPlane Mode Disabled",Toast.LENGTH_SHORT).show()
        }

    }
}