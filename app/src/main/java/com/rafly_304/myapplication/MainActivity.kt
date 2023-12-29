package com.rafly_304.myapplication

import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder.MultiPermissionListener
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.rafly_304.myapplication.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    lateinit var receiver :AirPlaneModeChange
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        receiver=AirPlaneModeChange()
        binding.btnIntent.setOnClickListener {
            val intent=Intent(this@MainActivity,IntnetActivity::class.java)
            intent.putExtra("text",R.string.text)
            startActivity(intent)
        }
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver,it)
        }
       binding.permission.setOnClickListener {
            getPermission()
       }
        binding.gmaps.setOnClickListener {
            val latitude = -7.7926
            val longitude = 110.3676

            val gmmIntentUri = Uri.parse("google.streetview:cbll=$latitude,$longitude")

            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            startActivity(mapIntent)


        }
        binding.website.setOnClickListener {
            val websiteUrl = "https://developer.android.com/"
            openWebsiteInBrowser(websiteUrl)
        }
        binding.btnEmail.setOnClickListener {
            val emailSubject = R.id.subject.toString()
            val emailMessage = R.id.emailMessage.toString()
            val recipientEmail = R.id.email.toString()
            sendEmail(emailSubject, emailMessage, recipientEmail)

        }

    }
    fun getPermission(){
        Dexter.withContext(this)
            .withPermissions(android.Manifest.permission.CAMERA,android.Manifest.permission.READ_CONTACTS)
            .withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report.let{
                        if (report!!.areAllPermissionsGranted()){
                            Toast.makeText(this@MainActivity,"Permission Granted",Toast.LENGTH_SHORT).show()

                        }else{
                            Toast.makeText(this@MainActivity,"Please Grant Permission",Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }


            }).withErrorListener {
                Toast.makeText(this,it.name,Toast.LENGTH_SHORT).show()
            }.check()
    }
    fun Call(v: View?) {

        val e = findViewById<View>(R.id.editText) as EditText

        Toast.makeText(this, "clicked", Toast.LENGTH_LONG)
            .show()

        val u = Uri.parse("tel:" + e.text.toString())

        val i = Intent(Intent.ACTION_DIAL, u)
        try {

            startActivity(i)
        } catch (s: SecurityException) {

            Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG)
                .show()
        }
    }
    fun sendSMS(v :View?)
    {
        val noHp= binding.hpSms.text.toString()
        val sms = binding.textSms.text.toString()
        val uri = Uri.parse("smsto:$noHp")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra("sms_body", sms)
        startActivity(intent)
    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
    fun openWebsiteInBrowser(url: String) {
        val webpage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)

        startActivity(intent)
    }
    fun sendEmail(subject: String, message: String, recipientEmail: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Tidak ada aplikasi email yang tersedia", Toast.LENGTH_SHORT).show()
        }
    }

}