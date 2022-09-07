package ___PACKAGE___

import android.content.Intent
import android.net.Uri
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.qmobile.qmobileapi.utils.UTF8
import timber.log.Timber
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

@BindingAdapter("addressAction")
fun addressAction(view: TextView, addressAction: String?) {
    if (addressAction.isNullOrEmpty()) return
    view.text = addressAction
    view.setOnClickListener {

        val lonLatRegex =
            "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)\$"
                .toRegex()
        val uri = if (lonLatRegex.matches(addressAction)) {
            "geo:$addressAction"
        } else {
            try {
                val query = URLEncoder.encode(addressAction, UTF8)
                "geo:0,0?q=$query"
            } catch (e: UnsupportedEncodingException) {
                Timber.e(e.localizedMessage)
                Toast.makeText(view.context, "Could not encode given address for Maps", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
        }

        val gmmIntentUri = Uri.parse(uri)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        view.context.startActivity(mapIntent)
        mapIntent.resolveActivity(view.context.packageManager)?.let {
            view.context.startActivity(mapIntent)
        }
    }
}
