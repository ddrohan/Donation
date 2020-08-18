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
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.serviceUnavailableMessage
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_edit.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditFragment : Fragment(), Callback<DonationWrapper>, AnkoLogger {

    lateinit var app: DonationApp
    lateinit var loader : AlertDialog
    lateinit var root: View
    var editDonation: DonationModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as DonationApp

        arguments?.let {
            editDonation = it.getParcelable("editdonation")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit, container, false)
        activity?.title = getString(R.string.action_edit)
        loader = createLoader(activity!!)

        root.editAmount.setText(editDonation!!.amount.toString())
        root.editPaymenttype.setText(editDonation!!.paymenttype)
        root.editMessage.setText(editDonation!!.message)
        root.editUpvotes.setText(editDonation!!.upvotes.toString())

        root.editUpdateButton.setOnClickListener {
            showLoader(loader, "Updating Donation on Server...")
            updateDonationData()
            var callUpdate = app.donationService.put(app.auth.currentUser?.email,
                (editDonation as DonationModel)._id ,editDonation as DonationModel)
            callUpdate.enqueue(this)
        }

        return root
    }


    companion object {
        @JvmStatic
        fun newInstance(donation: DonationModel) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("editdonation",donation)
                }
            }
    }

    override fun onFailure(call: Call<DonationWrapper>, t: Throwable) {
        info("Retrofit Error : $t.message")
        serviceUnavailableMessage(activity!!)
        hideLoader(loader)
    }

    override fun onResponse(call: Call<DonationWrapper>, response: Response<DonationWrapper>) {
        hideLoader(loader)
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, ReportFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun updateDonationData() {
        editDonation!!.amount = root.editAmount.text.toString().toInt()
        editDonation!!.message = root.editMessage.text.toString()
        editDonation!!.upvotes = root.editUpvotes.text.toString().toInt()
    }
}
