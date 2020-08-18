package ie.wit.models

import android.util.Log

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class DonationMemStore : DonationStore {

        val donations = ArrayList<DonationModel>()

        override fun findAll(): List<DonationModel> {
            return donations
        }

        override fun findById(id:Long) : DonationModel? {
            val foundDonation: DonationModel? = donations.find { it.id == id }
            return foundDonation
        }

        override fun create(donation: DonationModel) {
            donation.id = getId()
            donations.add(donation)
            logAll()
        }

        fun logAll() {
            Log.v("Donate","** Donations List **")
            donations.forEach { Log.v("Donate","${it}") }
        }
    }
