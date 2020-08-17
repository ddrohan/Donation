package ie.wit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.R
import ie.wit.adapters.DonationAdapter
import ie.wit.main.DonationApp
import kotlinx.android.synthetic.main.activity_report.*

class Report : AppCompatActivity() {

    lateinit var app: DonationApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        app = this.application as DonationApp

        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.adapter = DonationAdapter(app.donationsStore.findAll())
        //recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_report, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_donate -> { startActivity(Intent(this, Donate::class.java))
                                    true
                                    }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
