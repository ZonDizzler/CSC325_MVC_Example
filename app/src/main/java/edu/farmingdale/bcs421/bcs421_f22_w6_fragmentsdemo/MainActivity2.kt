package edu.farmingdale.bcs421.bcs421_f22_w6_fragmentsdemo

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    lateinit var mBtn1:Button
    lateinit var mBtn2:Button
    lateinit var mTV:TextView
    lateinit var sharedPref: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)




        mBtn1 = findViewById(R.id.btn1);
        mBtn2 = findViewById(R.id.btn2);
        mTV = findViewById(R.id.tv1)
        var frgmnt01 = Fragment01()
        var frgmnt02 = Fragment02()

        val editText01 = findViewById<EditText>(R.id.editText01)
        val seekBarChangeSize = findViewById<SeekBar>(R.id.seekBarChangeSize)

        val minValue = 0
        val maxValue = 64
        var currentValue = 0 // Initial value within the range

// Set the maximum value of the SeekBar
        seekBarChangeSize.max = maxValue - minValue

// Set the progress by subtracting the minValue from the initialValue
        seekBarChangeSize.progress = currentValue - minValue
// Add an OnSeekBarChangeListener to handle changes
        seekBarChangeSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Calculate the actual progress value including the minValue
                currentValue = (progress + minValue)


                // Update the EditText's text and text size based on actualProgress
                editText01.setText(currentValue.toString())
                mTV.textSize = currentValue.toFloat()


                // Handle progress changes here with the actualProgress value
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Handle when the user starts tracking touch
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //save textSize to shared pref
                saveTextSize(currentValue.toFloat())
            }
        })



        mBtn1.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.framelayout1, frgmnt01)
                commit()
            }
        }
        mBtn2.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.framelayout1, frgmnt02)
                commit()
            }
            readFromSharedPref()
        }
    }
    fun saveTextSize(textSize: Float) {
        val editor = sharedPref.edit()
        editor.putFloat("text_size", textSize)
        editor.apply()
    }

    private fun readFromSharedPref(){
        var sharedPref= getSharedPreferences(MainActivity().SHAREDPREF_FILENAME, MODE_PRIVATE)
        mTV.setText(sharedPref.getString("KEY", "not forund"))
    }
}