package com.irfikri.githubuser.ui.tema

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.irfikri.githubuser.databinding.ActivityTemaBinding

class TemaActivity : AppCompatActivity() {
    private lateinit var binding :ActivityTemaBinding
    private val viewModel by viewModels<TemaViewModel>{
        TemaViewModel.Factory(TemaDatastore(this))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTemaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getTema().observe(this){
            if(it){
                binding.sTema.text = "Tema Gelap"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                binding.sTema.text = "Tema Terang"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            binding.sTema.isChecked = it
        }
        binding.sTema.setOnCheckedChangeListener { compoundButton, b ->
            viewModel.saveTema(b)
        }
    }
}