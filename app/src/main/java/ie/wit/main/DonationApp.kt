package ie.wit.main

import android.app.Application
import ie.wit.api.DonationService
import ie.wit.models.DonationModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class DonationApp : Application(), AnkoLogger {

    lateinit var donationService: DonationService
    var donations = ArrayList<DonationModel>()

    override fun onCreate() {
        super.onCreate()
        info("Donation App started")
        donationService = DonationService.create()
        info("Donation Service Created")
    }
}

