package com.hoppin.base

/**
 * Created by Ravi Birla on 27,June,2019
 */
import android.content.Context
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageDecoder.decodeBitmap
import android.net.ConnectivityManager
import android.net.Uri
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import java.io.*

class Constant {

    companion object{
        val  TAG : String = Constant::class.java.name
        val MY_PERMISSIONS_REQUEST_LOCATION = 101
        val MY_PERMISSIONS_REQUEST_CAMERA = 1
        val REQUESTPERMISSIONCODE = 2
        val REQUEST_VIDEO_CAPTURE = 300
        val CAMERA = 5
        val GALLERY = 6
        val PROGRESS_BAR_MAX = 1000
        val SELECT_VIDEO_REQUEST = 0
        val RECORD_AUDIO = 15
        val REQUEST_ID_MULTIPLE_PERMISSIONS = 13
        val LOG_VALUE = true
        val SECOND_ACTIVITY_REQUEST_CODE = 0
        var MY_UID = ""

        private val DEFAULT_MIN_WIDTH_QUALITY = 400        // min pixels
        private val DEFAULT_MIN_HEIGHT_QUALITY = 400
        private val minWidthQuality = DEFAULT_MIN_WIDTH_QUALITY
        private val minHeightQuality = DEFAULT_MIN_HEIGHT_QUALITY


        fun showCustomToast(context : Context, msg : String){
            Toast.makeText(context,""+msg,Toast.LENGTH_LONG).show()
        }


        //*****************check for network connection******************//
        fun isNetworkAvailable(context: Context, coordinatorLayout: View): Boolean {

            val connectivityManager = context.applicationContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo

            // Check for network connections
            if (activeNetworkInfo != null) {
                // if connected with internet
                return true

            } else {

                val snackbar = Snackbar
                        .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_SHORT)
                        .setAction("RETRY", View.OnClickListener { /*not used*/ })
                snackbar.setActionTextColor(Color.RED)
                val sbView = snackbar.getView()
                val textView = sbView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
                textView.setTextColor(Color.YELLOW)
                snackbar.show()
                return false
            }
            return false
        }


        //""""""""image Resize"""""""""/
        fun getImageResized(context: Context, selectedImage: Uri): Bitmap? {
            var bm: Bitmap?
            val sampleSizes = intArrayOf(5, 3, 2, 1)
            var i = 0
            do {
                bm = decodeBitmap(context, selectedImage, sampleSizes[i])
                i++
            } while (bm != null
                    && (bm.width < minWidthQuality || bm.height < minHeightQuality)
                    && i < sampleSizes.size
            )
            Log.i("FragmentFeed", "Final bitmap width = " + (bm?.width ?: "No final bitmap"))
            return bm
        }

        fun decodeBitmap(context: Context, theUri: Uri, sampleSize: Int): Bitmap? {
            var actuallyUsableBitmap: Bitmap? = null
            var fileDescriptor: AssetFileDescriptor? = null
            val options = BitmapFactory.Options()
            options.inSampleSize = sampleSize

            try {
                fileDescriptor = context.contentResolver.openAssetFileDescriptor(theUri, "r")
                actuallyUsableBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor!!.fileDescriptor, null, options)
                if (actuallyUsableBitmap != null) {
                    Log.i(
                            TAG, "Trying sample size " + options.inSampleSize + "\t\t"
                            + "Bitmap width: " + actuallyUsableBitmap.width
                            + "\theight: " + actuallyUsableBitmap.height
                    )
                }
                fileDescriptor.close()
            } catch (e: FileNotFoundException) {
                Log.d("ImageRotator", e.message)
            } catch (e: IOException) {
                Log.d(TAG, e.message)
            }

            return actuallyUsableBitmap
        }


        //"""""""""" create bitmap to file """"""""//
        fun savebitmap(mContext: Context, bitmap: Bitmap?, name: String): File? {
            val filesDir = mContext.applicationContext.filesDir
            val imageFile = File(filesDir, "$name.jpg")

            val os: OutputStream
            try {
                os = FileOutputStream(imageFile)
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, os)
                os.flush()
                os.close()
                return imageFile
            } catch (e: Exception) {
                Log.e(mContext.javaClass.simpleName, "Error writing bitmap", e)
            }

            return null
        }

        //**************hide keyboard********************//
        fun hideSoftKeyBoard(context: Context, view: View) {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }





}