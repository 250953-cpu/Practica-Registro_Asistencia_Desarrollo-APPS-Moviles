package com.example.interfaz

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // === ZONA 1: Variables globales de la clase (Paso 6.1) ===
    private lateinit var tvMensaje: TextView
    private lateinit var etxtNombre: EditText
    private lateinit var etxtMatricula: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var btnLimpiar: Button
    private lateinit var pbCarga: ProgressBar

    private lateinit var btnTemaJFHL: Button
    private var contadorAsistentes = 0 // Variable para el punto extra

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        // === ZONA 2: Inicialización de componentes (findViewById) ===
        tvMensaje = findViewById(R.id.tvMensaje)
        etxtNombre = findViewById(R.id.etxtNombre)
        etxtMatricula = findViewById(R.id.etxtMatricula)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnLimpiar = findViewById(R.id.btnLimpiar)
        pbCarga = findViewById(R.id.pbCarga)

        // Inicialización del nuevo botón de tema (Paso 6.2)
        btnTemaJFHL = findViewById(R.id.btnTema)

        // Evaluamos los recursos actuales de la pantalla para colocar el texto correcto al iniciar/recrear
        val modoOscuroActivo = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        if (modoOscuroActivo) {
            btnTemaJFHL.text = getString(R.string.btn_tema_claroJFHL)
        } else {
            btnTemaJFHL.text = getString(R.string.btn_tema_oscuroJFHL)
        }

        // Acción del botón Registrar con validaciones usando getString (Paso 7)
        btnRegistrar.setOnClickListener {
            val nombre = etxtNombre.text.toString().trim()
            val matricula = etxtMatricula.text.toString().trim()

            // Validación Paso 7.1
            if (nombre.isEmpty()) {
                Toast.makeText(this, getString(R.string.val_nombre_vacioJFHL), Toast.LENGTH_SHORT).show()
            } else if (nombre.length < 3) { // Validación de nombre corto
                Toast.makeText(this, getString(R.string.val_nombre_cortoJFHL), Toast.LENGTH_SHORT).show()
            } else if (matricula.isEmpty()) {
                Toast.makeText(this, getString(R.string.val_matricula_vaciaJFHL), Toast.LENGTH_SHORT).show()
            } else if (matricula.length != 7) { // Validación de matrícula de 7 dígitos
                Toast.makeText(this, getString(R.string.val_matricula_largoJFHL), Toast.LENGTH_SHORT).show()
            } else {
                contadorAsistentes++

                // PUNTO EXTRA: Se sacó la línea del contador a strings.xml usando los marcadores
                // de posición %1$s para el nombre, %2$s para la matrícula y %3$d para el número de asistente.
                tvMensaje.text = getString(R.string.msg_registradoJFHL, nombre, matricula, contadorAsistentes)

                // Cambia el texto del botón a "Registrar otro" (Paso 7.3)
                btnRegistrar.text = getString(R.string.btn_registrar_otroJFHL)
            }
        }

        // Acción del botón Limpiar (Paso 7.2)
        btnLimpiar.setOnClickListener {
            limpiar()
        }

        // Acción del botón Tema (Paso 6.2)
        btnTemaJFHL.setOnClickListener {
            cambiarTemaJFHL()
        }
    }

    // === ZONA 3: Funciones complementarias de la clase ===

    // Función limpiar usando getString (Paso 7.2)
    private fun limpiar() {
        etxtNombre.text.clear()
        etxtMatricula.text.clear()
        tvMensaje.text = getString(R.string.msg_inicialJFHL)
        btnRegistrar.text = getString(R.string.btn_registrarJFHL)
    }

    // Función que cambia el tema de la app en vivo de forma manual (Paso 6.3)
    private fun cambiarTemaJFHL() {
        // Le preguntamos directamente a la configuración de la pantalla si está en modo noche
        val modoOscuroActivo = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

        if (!modoOscuroActivo) {
            // Si la pantalla NO está oscura, forzamos el modo noche
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            // Si la pantalla YA está oscura, regresamos al modo día
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
