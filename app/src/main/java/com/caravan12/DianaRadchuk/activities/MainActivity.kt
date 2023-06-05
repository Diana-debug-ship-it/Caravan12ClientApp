package com.caravan12.DianaRadchuk.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.caravan12.DianaRadchuk.R
import com.caravan12.DianaRadchuk.databinding.ActivityMainBinding
import com.caravan12.DianaRadchuk.databinding.HeaderLayoutBinding
import com.caravan12.DianaRadchuk.fragments.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingHeader: HeaderLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("run", "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingHeader = HeaderLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myToolBar: androidx.appcompat.widget.Toolbar = binding.toolbarMainMenu
        setSupportActionBar(myToolBar)
        binding.apply {

            val headerView = navigationMenu.getHeaderView(0)
            val userName : TextView = headerView.findViewById(R.id.textViewUser)
            val email = intent.getStringExtra("Email")
            userName.text = email

            navigationMenu.setNavigationItemSelectedListener{
                when(it.itemId) {
                    R.id.searchHotels -> {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://karavan-12.ru/")))
                    }
                    R.id.tripSelection -> {
                        showFragment(FillTheApplicationFragment())
                        myDrawerLayout.close()
                    }
                    R.id.info -> {
                        showFragment(InformationFragment())
                        myDrawerLayout.close()
                    }

                    R.id.myAccount -> {
                        showFragment(MyProfileFragment())
                        myDrawerLayout.close()
                    }
                    R.id.homePage -> {
                        showFragment(HomeFragment())
                        myDrawerLayout.close()
                    }
                    R.id.events -> {
                        showFragment(EventsFragment())
                        myDrawerLayout.close()
                    }
                }
                true

            }
        }

        val myToggle = ActionBarDrawerToggle(this, binding.myDrawerLayout, myToolBar, R.string.empty, R.string.empty)
        binding.myDrawerLayout.addDrawerListener(myToggle)
        myToggle.syncState()

    }

    override fun onStart() {
        super.onStart()
        showFragment(HomeFragment())
    }

    private fun showFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeHolder, fragment)
            .commit()
    }

}