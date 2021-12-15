package ___PACKAGE___

import android.content.Intent
import android.net.Uri
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("addressAction")
fun addressAction(view: TextView, addressAction: String?) {
    if (addressAction.isNullOrEmpty()) return

    view.setOnClickListener {
        val gmmIntentUri = Uri.parse("geo:$addressAction")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        view.context.startActivity(mapIntent)
        mapIntent.resolveActivity(view.context.packageManager)?.let {
            view.context.startActivity(mapIntent)
        }
    }
}
