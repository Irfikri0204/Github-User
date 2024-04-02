package com.irfikri.githubuser.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.irfikri.githubuser.R
import com.irfikri.githubuser.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel:DetailUserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME,username)
        showLoading(true)
        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)
        viewModel.setUserDetail(username.toString())
        viewModel.getUserDetail().observe(this,{
            if(it!=null){
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    btnOpen.setOnClickListener {
                            val link:Uri = Uri.parse("https://github.com/${username}")
                            val intent = Intent(Intent.ACTION_VIEW,link)
                            startActivity(intent)
                    }
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(ivProfile)
                }
                showLoading(false)
            }
        })
        var _isChecked = false
        fun changeIcon(state: Boolean) {
            if (state == true) {
                binding.idToggleFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.idToggleFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkedUser(id)
            withContext(Dispatchers.Main){
                if(count!=null){
                    if(count>0){
                        _isChecked=true
                    } else{
                        _isChecked=false
                    }
                    changeIcon(_isChecked)
                }
            }
        }
        binding.idToggleFavorite.setOnClickListener {
            _isChecked= !_isChecked
            if(_isChecked){
                viewModel.addToFavorite(username.toString(),id,avatarUrl.toString())
            } else {
                viewModel.removeFromFavorite(id)
            }
            changeIcon(_isChecked)
        }

        val sectionPagerAdapter = SectionPagerAdapter(this,supportFragmentManager,bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
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