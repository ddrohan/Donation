package ie.wit.main

import android.app.Application
import android.util.Log
import ie.wit.models.DonationMemStore
import ie.wit.models.DonationStore

class DonationApp : Application() {

    lateinit var donationsStore: DonationStore

    override fun onCreate() {
        super.onCreate()
        donationsStore = DonationMemStore()
        Log.v("Donate","Donation App started")
    }
}