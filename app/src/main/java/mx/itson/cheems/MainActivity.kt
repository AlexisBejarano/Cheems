package mx.itson.cheems

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



    class MainActivity : AppCompatActivity(), View.OnClickListener {

    var gameOverCard = 0
        var conteoCards = 0
        var inicio = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnRestart = findViewById<Button>(R.id.button_restart)
        btnRestart.setOnClickListener {
            start() // Reinicia el juego usando la función start()
        }

        val btnViewCard = findViewById<Button>(R.id.button_volear)
        btnViewCard.setOnClickListener {
            viewCard()
        }

        start() // Inicia el juego al abrir la app

    }

        fun viewCard() {
            Log.d("Se voltearon las cartas", "La carta perdedora es $gameOverCard")

            for (i in 1..12) {
                val btnCard = findViewById<ImageButton>(
                    resources.getIdentifier("carta$i", "id", packageName)
                )

                if (i == gameOverCard) {
                    btnCard.setBackgroundResource(R.drawable.icon_chempe) // Carta perdedora
                } else {
                    btnCard.setBackgroundResource(R.drawable.icon_cheems) // Cartas normales
                }
            }
        }
    fun start() {
        for (i in 1..12) {
            val btnCard = findViewById<ImageButton>(
                resources.getIdentifier("carta$i", "id", packageName)
            )
            btnCard.isEnabled = true
            btnCard.setBackgroundResource(R.drawable.icon_pregunta)
            btnCard.setOnClickListener(this)
        }

        conteoCards = 0
        gameOverCard = (1..12).random()

        if(inicio == 0){
            Toast.makeText(this, "HOLA PAPUSHO, BIENVENIDO", Toast.LENGTH_SHORT).show()
            inicio = 1
        } else {
            Toast.makeText(this, "Juego reiniciado", Toast.LENGTH_SHORT).show()

        }

        Log.d("El valor de la carta", "La carta perdedora es $gameOverCard")
    }

    fun flip(card : Int){
        var btnCard = findViewById<View>(
            resources.getIdentifier("carta$card", "id", this.packageName)
        ) as ImageButton

        if(btnCard.isEnabled) { //Agregue esto para que se ejecute si esta TRUE.

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

                for(i in 1..12){
                    val btn = findViewById<View>(
                        resources.getIdentifier("carta$i", "id", this.packageName)
                    ) as ImageButton
                    if(i == card) {
                        btn.setBackgroundResource(R.drawable.icon_chempe)
                    }else {
                        btn.setBackgroundResource(R.drawable.icon_cheems)
                    }
                    btn.isEnabled = false // por si pierdes ya no puedas dar clics
                }
            } else {
                //Comtinúa en el juego
                btnCard.setBackgroundResource(R.drawable.icon_cheems)
                btnCard.isEnabled = false //Cambia de estado por si le das clic de nuevo a una carta ya presionada

                conteoCards++ //Incrementamos uno siempre y cuando el juego continue xd

                if (conteoCards == 11){
                    Toast.makeText(this, getString(R.string.text_win), Toast.LENGTH_LONG).show()

                    for(i in 1..12){
                        val btn = findViewById<View>(
                            resources.getIdentifier("carta$i", "id", this.packageName)
                        ) as ImageButton
                        if(i == gameOverCard) {
                            btn.setBackgroundResource(R.drawable.icon_chempe)
                        }
                    }

                }
            }
    }}

    override fun onClick(v: View) {
        when(v.id){
            R.id.carta1 -> { flip(1)}
            R.id.carta2 -> { flip(2)}
            R.id.carta3 -> { flip(3)}
            R.id.carta4 -> { flip(4)}
            R.id.carta5 -> { flip(5)}
            R.id.carta6 -> { flip(6)}
            R.id.carta7 -> { flip(7)}
            R.id.carta8 -> { flip(8)}
            R.id.carta9 -> { flip(9)}
            R.id.carta10 -> { flip(10)}
            R.id.carta11 -> { flip(11)}
            R.id.carta12 -> { flip(12)}
        }
    }
}