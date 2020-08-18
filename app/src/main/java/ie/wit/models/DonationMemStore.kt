package ie.wit.models

import android.util.Log

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class DonationMemStore : DonationStore {

    var donations = ArrayList<DonationModel>()

        override fun findAll(): List<DonationModel> {
            return donations
        }

        override fun findById(id:String) : DonationModel? {
            val foundDonation: DonationModel? = donations.find { it._id == id }
            return foundDonation
        }

        override fun create(donation: DonationModel) {
            //donation._id = getId()
            donations.add(donation)
            logAll()
        }

        override fun update(donation: DonationModel) {

        }

        override fun delete(donation: DonationModel) {
            donations.remove(donation)
            logAll()
        }

        fun logAll() {
            Log.v("Donate","** Donations List **")
            donations.forEach { Log.v("Donate","${it}") }
        }
    }
