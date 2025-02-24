package mx.itson.cheems

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



    class MainActivity : AppCompatActivity(), View.OnClickListener {

    var gameOverCard = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        start()
    }

    fun start() {
        for(i in 1..6){
            val btnCard = findViewById<View>(
                resources.getIdentifier("carta$i", "id", this.packageName)
            ) as ImageButton
            btnCard.setOnClickListener(this)
            btnCard.setBackgroundResource(R.drawable.icon_pregunta)
        }
        gameOverCard = (1 .. 6).random()

        Log.d("El valor de la carta", "La carta perdedora es ${gameOverCard.toString()}")
    }

    fun flip(card : Int){
        if(card == gameOverCard){
            // Ya perdió

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                val vibrattorAdmin = applicationContext.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                val vibrator = vibrattorAdmin.defaultVibrator

                vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))

            } else {
                val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(1000)
            }

            Toast.makeText(this, getString(R.string.text_game_over), Toast.LENGTH_LONG).show()

            for(i in 1..6){
                val btnCard = findViewById<View>(
                    resources.getIdentifier("carta$i", "id", this.packageName)
                ) as ImageButton
                if(i == card) {
                    btnCard.setBackgroundResource(R.drawable.icon_chempe)
                }else {
                    btnCard.setBackgroundResource(R.drawable.icon_cheems)
                }
            }
        } else {
            //Comtinúa en el juego
            val btnCard = findViewById<View>(
                resources.getIdentifier("carta$card", "id", this.packageName)
            ) as ImageButton
            btnCard.setBackgroundResource(R.drawable.icon_cheems)
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.carta1 -> { flip(1)}
            R.id.carta2 -> { flip(2)}
            R.id.carta3 -> { flip(3)}
            R.id.carta4 -> { flip(4)}
            R.id.carta5 -> { flip(5)}
            R.id.carta6 -> { flip(6)}
        }
    }
}