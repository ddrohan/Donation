package ie.wit.main

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import ie.wit.api.DonationService
import ie.wit.models.DonationModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class DonationApp : Application(), AnkoLogger {

    lateinit var donationService: DonationService
    var donations = ArrayList<DonationModel>()

    // [START declare_auth]
    lateinit var auth: FirebaseAuth
    // [END declare_auth]

    override fun onCreate() {
        super.onCreate()
        info("Donation App started")
        donationService = DonationService.create()
        info("Donation Service Created")
    }
}

