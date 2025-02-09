package com.irfikri.githubuser.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.irfikri.githubuser.data.local.FavoriteUser
import com.irfikri.githubuser.data.model.User
import com.irfikri.githubuser.databinding.ActivityFavoriteBinding
import com.irfikri.githubuser.ui.detail.DetailUserActivity
import com.irfikri.githubuser.ui.main.UserAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        adapter.setOnItemCallback(object :UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID,data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL,data.avatar_url)
                    startActivity(it)
                }
            }
        })
        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = adapter
        }
        viewModel.getFavoriteUser()?.observe(this,{
            if(it!=null){
                val list = mapList(it)
                adapter.setList(list)
            }
        })
    }
    private fun mapList(users: List<FavoriteUser>):ArrayList<User>{
        val listUsers = ArrayList<User>()
        for(user in users){
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}