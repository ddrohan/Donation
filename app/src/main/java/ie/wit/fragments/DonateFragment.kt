package ie.wit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog

import ie.wit.R
import ie.wit.api.DonationWrapper
import ie.wit.main.DonationApp
import ie.wit.models.DonationModel
import ie.wit.utils.*
import kotlinx.android.synthetic.main.fragment_donate.*
import kotlinx.android.synthetic.main.fragment_donate.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.String.format


class DonateFragment : Fragment(), AnkoLogger, Callback<List<DonationModel>> {

    lateinit var app: DonationApp
    var totalDonated = 0
    lateinit var loader : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as DonationApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_donate, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.action_donate)

        root.progressBar.max = 10000
        root.amountPicker.minValue = 1
        root.amountPicker.maxValue = 1000

        root.amountPicker.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
            root.paymentAmount.setText("$newVal")
        }
        setButtonListener(root)
        return root;
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DonateFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun setButtonListener( layout: View) {
        layout.donateButton.setOnClickListener {
            val amount = if (layout.paymentAmount.text.isNotEmpty())
                layout.paymentAmount.text.toString().toInt() else layout.amountPicker.value
            if(totalDonated >= layout.progressBar.max)
                activity?.toast("Donate Amount Exceeded!")
            else {
                val paymentmethod = if(layout.paymentMethod.checkedRadioButtonId == R.id.Direct) "Direct" else "Paypal"
                addDonation(DonationModel(paymenttype = paymentmethod,amount = amount))
            }
        }
    }

    fun addDonation(donation : DonationModel) {
        showLoader(loader, "Adding Donation to Server...")
        var callAdd = app.donationService.post(donation)
        callAdd.enqueue(object : Callback<DonationWrapper> {
            override fun onFailure(call: Call<DonationWrapper>, t: Throwable) {
                info("Retrofit Error : $t.message")
                serviceUnavailableMessage(activity!!)
                hideLoader(loader)
            }

            override fun onResponse(call: Call<DonationWrapper>,
                                    response: Response<DonationWrapper>) {
                val donationWrapper = response.body()
                info("Retrofit Wrapper : $donationWrapper")
                getAllDonations()
                updateUI()
                hideLoader(loader)
            }
        })
    }
    override fun onResume() {
        super.onResume()
        getAllDonations()
    }

    fun getAllDonations() {
        showLoader(loader, "Downloading Donations List")
        var callGetAll = app.donationService.getall()
        callGetAll.enqueue(this)
    }

    override fun onResponse(call: Call<List<DonationModel>>,
                            response: Response<List<DonationModel>>) {
        // donationServiceAvailable = true
        serviceAvailableMessage(activity!!)
        info("Retrofit JSON = ${response.body()}")
        app.donations = response.body() as ArrayList<DonationModel>
        updateUI()
        hideLoader(loader)
    }

    override fun onFailure(call: Call<List<DonationModel>>, t: Throwable) {
        // donationServiceAvailable = false
        info("Retrofit Error : $t.message")
        serviceUnavailableMessage(activity!!)
        hideLoader(loader)
    }

    fun updateUI() {
        totalDonated = app.donations.sumBy { it.amount }
        progressBar.progress = totalDonated
        totalSoFar.text = format("$ $totalDonated")
    }
}
