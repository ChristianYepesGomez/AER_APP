package com.example.aer_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.aer_app.databinding.ActivityAuthBinding
import com.example.aer_app.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class HomeActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        chargeDrawer()


    }

    fun chargeDrawer() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        //Get the user_name textview and set the text to the current user name
        val userMailTextView: TextView =
            navView.getHeaderView(0).findViewById(R.id.user_email) as TextView
        val user = firebaseAuth.currentUser
        userMailTextView.text = user!!.email

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> Toast.makeText(applicationContext, "Home", Toast.LENGTH_SHORT)
                    .show()
                R.id.nav_logout -> {
                    firebaseAuth.signOut()
                    val intent = Intent(this, LogInActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_settings -> Toast.makeText(
                    applicationContext,
                    "Settings",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_share -> Toast.makeText(applicationContext, "Share", Toast.LENGTH_SHORT)
                    .show()
            }

            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}