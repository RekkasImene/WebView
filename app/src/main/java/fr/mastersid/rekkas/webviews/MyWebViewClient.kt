package fr.mastersid.rekkas.webviews

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MyWebViewClient : WebViewClient() {
    private val _loadInside: MutableLiveData<String> = MutableLiveData("http://mastersid.univ-rouen.fr")
    val loadInside : LiveData<String>
        get () = _loadInside

    private val _loadOutside : MutableLiveData<Uri> = MutableLiveData(Uri.EMPTY)
    val loadOutside : LiveData<Uri>
        get () = _loadOutside

    private val _pageTitle : MutableLiveData < String > = MutableLiveData ("")
    val pageTitle : LiveData < String >
        get () = _pageTitle


    override fun shouldOverrideUrlLoading (view : WebView, request : WebResourceRequest):
            Boolean {
        if ( request.url.host == "www.univ-rouen.fr" || request.url.host == "www.insa-rouen.fr") {
            return false // ouverture dans la WebView
        }
        _loadOutside . value = request .url // ouverture dans un navigateur externe
        return true
    }

    override fun onPageFinished ( view : WebView , url : String ) {
        super . onPageFinished (view , url )
        _pageTitle . value = view . title
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        if ( url != null) {
            _loadInside . value = url
        }
    }

}