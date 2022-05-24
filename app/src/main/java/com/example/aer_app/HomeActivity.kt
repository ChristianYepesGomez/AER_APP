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
import androidx.fragment.app.Fragment
import com.example.aer_app.databinding.ActivityAuthBinding
import com.example.aer_app.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class HomeActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        chargeDrawer()


    }

    //function to charge the Drawer menu and the items inside the menu
    fun chargeDrawer() {
        drawerLayout = binding.drawerLayout
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

            it.isChecked = true

            when (it.itemId) {
                //Home
                R.id.nav_home -> replaceFragment(HomeFragment(), it.title.toString())
                //Users
                R.id.nav_usuarios -> replaceFragment(UserFragment(), it.title.toString())
                //Problems
                R.id.nav_problemas -> replaceFragment(ProblemFragment(), it.title.toString())
                //Institutions
                R.id.nav_instituciones -> replaceFragment(
                    InstitutionFragment(),
                    it.title.toString()
                )
                //Setting
                R.id.nav_settings -> Toast.makeText(
                    applicationContext,
                    "Settings",
                    Toast.LENGTH_SHORT
                ).show()
                //Share
                R.id.nav_share -> Toast.makeText(applicationContext, "Share", Toast.LENGTH_SHORT)
                    .show()
                //Logout
                R.id.nav_logout -> {
                    firebaseAuth.signOut()
                    val intent = Intent(this, LogInActivity::class.java)
                    startActivity(intent)
                }
            }

            true
        }
    }

    //Function to replace view fragment with the clicked one
    private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}