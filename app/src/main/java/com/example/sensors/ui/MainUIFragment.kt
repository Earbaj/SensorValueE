package com.example.sensors.ui

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.sensors.R
import com.example.sensors.data.SensorApplication
import com.example.sensors.viewModel.SensorsViewModel
import com.example.sensors.viewModel.SensorsViewModelFactory
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.util.Timer
import java.util.TimerTask


class MainUIFragment : Fragment(),SensorEventListener {

    //Initialize view model and factory along with application so that the entiar fragment access the view model
    private val viewModel: SensorsViewModel by activityViewModels {
        SensorsViewModelFactory(
            (activity?.application as SensorApplication).database
                .sensorDao()
        )
    }


    private lateinit var sensorManager: SensorManager
    private var brightness: Sensor? = null
    private lateinit var text: TextView
    private lateinit var pb: CircularProgressBar

    private var result: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main_u_i, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pb = view.findViewById(R.id.circularProgressBar)
        text = view.findViewById(R.id.tv_text)
        setUpSensorStuff()

        //Timmer for save data in every 5 min
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                viewModel.addNewItem(result)
                mainHandler.postDelayed(this, 300000)
            }
        })

    }


    // Code for sensor initiate and getting value from it
    private fun setUpSensorStuff() {
        //sensorManager = getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        sensorManager = requireActivity().getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager

        brightness = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    private fun brightness(brightness: Float): String {

        return when (brightness.toInt()) {
            0 -> "Pitch black"
            in 1..10 -> "Dark"
            in 11..50 -> "Grey"
            in 51..5000 -> "Normal"
            in 5001..25000 -> "Incredibly bright"
            else -> "This light will blind you"
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val light1 = event.values[0]

            text.text = "Sensor: $light1\n${brightness(light1)}"
            result = light1.toDouble()
            pb.setProgressWithAnimation(light1)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }


    override fun onResume() {
        super.onResume()
        // Register a listener for the sensor.
        sensorManager.registerListener(this, brightness, SensorManager.SENSOR_DELAY_NORMAL)
    }


    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}