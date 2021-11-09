package fr.mastersid.rekkas.webviews

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import fr.mastersid.rekkas.webviews.databinding.ActivityMainBinding




class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    // var webViewModel: WebViewModel
    private val webViewModel : WebViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Appel de la webView
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.webView. settings . javaScriptEnabled = true



        // Set a click listener for back button
        binding.btnprev.setOnClickListener {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            }
        }

        // Set a click listener for Forward button
        binding.btnnext.setOnClickListener {
            if (binding.webView.canGoForward()) {
                binding.webView.goForward()
            }
        }

        /**binding.webView.loadUrl("http://mastersid.univ-rouen.fr")**/

        val webViewClient = MyWebViewClient ()
        binding . webView . webViewClient = webViewClient

        //getting url from viewModel
        binding.webView.loadUrl(webViewModel._loadInside.value!!)
        webViewClient . loadInside . observe ( this ) { url ->
            if ( url.isNotEmpty() ) {
                webViewModel._loadInside.value = url
            }
        }

        webViewClient . loadOutside . observe ( this ) { url ->
            if ( url != Uri . EMPTY ) {
                val intent = Intent ( Intent . ACTION_VIEW , url )
                startActivity ( intent )
            }
        }
        //pour titre et enable des button
        webViewClient . pageTitle . observe ( this ) { value ->
            title = value
            binding.btnprev.isEnabled = binding.webView.canGoBack()
            // Check web view forward history availability
            binding.btnnext.isEnabled = binding.webView.canGoForward()
        }
    }

    override fun onBackPressed () {
        if ( binding . webView . canGoBack ()) {
            binding . webView . goBack ()
        } else {
            super . onBackPressed ()
        }
    }
}