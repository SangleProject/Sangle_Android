package org.three.minutes.detail.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.three.minutes.R
import org.three.minutes.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private val mBinding : ActivityDetailBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_detail)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.apply {
            lifecycleOwner = this@DetailActivity
        }
        setSupportActionBar(mBinding.detailToolbar)

        setToolbarIcon()

    }

    private fun setToolbarIcon() {
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_detail_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            // go back
            android.R.id.home -> {
                finish()
            }
            // click share
            R.id.action_share -> {
                Toast.makeText(this, "Share",Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}