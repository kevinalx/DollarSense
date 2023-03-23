package com.droidnation.dollarsense.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.droidnation.dollarsense.R
import com.droidnation.dollarsense.core.ViewBindingUtil.bindView
import com.droidnation.dollarsense.databinding.ActivityIntroBinding
import com.droidnation.dollarsense.ui.main.MainActivity
import com.google.android.material.tabs.TabLayout

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    private var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindView()
        setContentView(binding.root)

        // hacer que la actividad ocupe toda la pantalla
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // comprobar si la actividad se ha iniciado antes o no
        if (restorePrefData()) {
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }

        // ocultar la barra de acción
        supportActionBar?.hide()

        // llenar lista de pantallas
        val mList: MutableList<ScreenItem> = ArrayList()
        mList.add(
            ScreenItem(
                "Comida fresca",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",
                R.drawable.img1
            )
        )
        mList.add(
            ScreenItem(
                "Entrega rápida",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",
                R.drawable.img2
            )
        )
        mList.add(
            ScreenItem(
                "Pago fácil",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",
                R.drawable.img3
            )
        )

        // configurar ViewPager
        introViewPagerAdapter = IntroViewPagerAdapter(this, mList)
        binding.screenViewpager.adapter = introViewPagerAdapter

        // configurar TabLayout con ViewPager
        binding.tabIndicator.setupWithViewPager(screenPager)

        // Listener para el botón siguiente
        binding.btnNext.setOnClickListener {
            position = screenPager.currentItem
            if (position < mList.size) {
                position++
                screenPager.currentItem = position
            }

            if (position == mList.size - 1) {
                // Mostrar el botón GETSTARTED y ocultar el indicador y el botón siguiente
                loaddLastScreen()
            }
        }

        // Listener para el cambio de pestaña en el TabLayout
        binding.tabIndicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == mList.size - 1) {
                    loadLastScreen()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            private fun restorePrefData(): Boolean {
                val pref = applicationContext.getSharedPreferences("myPrefs", MODE_PRIVATE)
                return pref.getBoolean("isIntroOpnend", false)
            }

            // skip button click listener


        }
    }
}