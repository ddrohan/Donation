package ie.wit.main

import android.app.Application
import android.util.Log
import android.widget.Toast
import ie.wit.models.DonationMemStore
import ie.wit.api.DonationService
import ie.wit.models.DonationModel
import ie.wit.models.DonationStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonationApp : Application(), AnkoLogger {

    //lateinit var donationsStore: DonationMemStore
    lateinit var donationService: DonationService
    var donations = ArrayList<DonationModel>()

    override fun onCreate() {
        super.onCreate()
        info("Donation App started")
        donationService = DonationService.create()
        info("Donation Service Created")
    }
}