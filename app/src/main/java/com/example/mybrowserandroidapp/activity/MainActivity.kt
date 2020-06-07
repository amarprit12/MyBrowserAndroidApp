package com.example.mybrowserandroidapp.activity

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.mybrowserandroidapp.R
import com.example.mybrowserandroidapp.showToast
import com.example.mybrowserandroidapp.utility.Utility
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val url = "https://google.com"
    private val requestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView.loadUrl(url)
        initWebSettings()
        webView.clearHistory()
        webView.clearCache(true)

        webView.webChromeClient = WebChromeClient()
        setWebViewClient()
        setWebViewDownloadListener()
    }

    private fun initWebSettings() {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.builtInZoomControls = true
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webSettings.displayZoomControls = false
        webSettings.setSupportZoom(true)
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
    }

    private fun setWebViewClient() {
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                Log.d(TAG, "onPageStarted: ")
                progressBar.visibility = View.VISIBLE
                if (url.contains("tel:") || url.contains("sms:") || url.contains("mailto:")) {
                    webView.stopLoading()
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(
                view: WebView, url: String
            ) {
                Log.d(TAG, "onPageFinished: ")
                showToast("Page loaded: ${view.title}")
                progressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }

    }

    private fun setWebViewDownloadListener() {
        webView.setDownloadListener { url, userAgent, contentDisposition, mimeType, _ ->
            // check for permissions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    downloadRequestDialog(url, userAgent, contentDisposition, mimeType)
                } else {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        requestCode
                    )
                }
            } else {
                downloadRequestDialog(url, userAgent, contentDisposition, mimeType)
            }
        }
    }

    private fun downloadRequestDialog(
        url: String?, userAgent: String?, contentDisposition: String?, mimeType: String?
    ) {
        val fileName = URLUtil.guessFileName(url, contentDisposition, mimeType)
        Utility.showAlertDialog(
            this,
            "Downloading....",
            "Do you want to Download $fileName ",
            DialogInterface.OnClickListener { _, _ ->
                val request =
                    DownloadManager.Request(Uri.parse(url))
                val cookie =
                    CookieManager.getInstance().getCookie(url)
                request.addRequestHeader("Cookie", cookie)
                request.addRequestHeader("User-Agent", userAgent)

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    fileName
                )
                manager.enqueue(request)
            },
            DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() },
            null,
            "Yes",
            "NO",
            true
        )


    }

    override fun onBackPressed() {
        // If web view has back history, then go back to it
        if (webView!!.canGoBack()) {
            webView?.goBack()
        } else {
            // Ask the user to exit the app or stay in here
            showAppExitDialog()
        }
    }

    private fun showAppExitDialog() {
        Utility.showAlertDialog(
            this,
            "Confirm exit...",
            "Do you want to exit the app, no back history is available ? ",
            DialogInterface.OnClickListener { _, _ ->
                super@MainActivity.onBackPressed()
            },
            DialogInterface.OnClickListener { dialog, _ ->
                showToast("Thank you", Toast.LENGTH_LONG)
                dialog.cancel()
            },
            null,
            "Yes",
            "No",
            true
        )
    }


    companion object {
        private const val TAG = "MainActivity"
    }

}
