package com.irfikri.githubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.irfikri.githubuser.R
import com.irfikri.githubuser.data.model.User
import com.irfikri.githubuser.databinding.ActivityMainBinding
import com.irfikri.githubuser.ui.detail.DetailUserActivity
import com.irfikri.githubuser.ui.favorite.FavoriteActivity
import com.irfikri.githubuser.ui.tema.TemaActivity
import com.irfikri.githubuser.ui.tema.TemaDatastore

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>{
        MainViewModel.Factory(TemaDatastore(this))
    }


    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getTema().observe(this){
            if(it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemCallback(object :UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity,DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID,data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL,data.avatar_url)
                    startActivity(it)
                }
            }
        })

        showLoading(true)
        viewModel.setSearchUser("Ahmad")
        viewModel.getSearchUsers().observe(this,{
            if(it!=null){
                adapter.setList(it)
                showLoading(false)
            }
        })
        binding.btnFavorite.setOnClickListener {
            Intent(this, FavoriteActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
            btnSearch.setOnClickListener {
                searchUser()
            }
            etQuery.setOnKeyListener { v, keyCode, event ->
                if(event.action == KeyEvent.ACTION_DOWN && keyCode==KeyEvent.KEYCODE_ENTER){
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
        viewModel.getSearchUsers().observe(this,{
            if(it!=null){
                adapter.setList(it)
                showLoading(false)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menus,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.opsi_tema -> {
                Intent(this,TemaActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchUser(){
        binding.apply {
            val query = etQuery.text.toString()
            if(query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUser(query)
        }
    }

    private fun showLoading(state:Boolean){
        if(state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}