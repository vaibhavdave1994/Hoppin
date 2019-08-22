package com.hoppin.helper

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.ContactsContract
import com.hoppin.activity.hoopActivity.model.ContactModel


/**
 * Created by Ravi Birla on 17,July,2019
 */
class GetContact {
    val PERMISSIONS_REQUEST_READ_CONTACTS:Int = 100
    fun getContacts(ctx: Context): List<ContactModel> {
        val list = ArrayList<ContactModel>()
        val contentResolver = ctx.getContentResolver()
        val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        if (cursor!!.getCount() > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    val cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf<String>(id), null)
                    val inputStream = ContactsContract.Contacts.openContactPhotoInputStream(ctx.getContentResolver(),
                            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id.toLong()))

                    val person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id.toLong())
                    val pURI = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY)

                    var photo: Bitmap? = null
                    if (inputStream != null) {
                        photo = BitmapFactory.decodeStream(inputStream)
                    }
                    while (cursorInfo!!.moveToNext()) {
                        val info = ContactModel()
                        info.id = id
                        info.name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        info.mobileNumber = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        info.photoURI = pURI
                        list.add(info)
                    }

                    cursorInfo.close()
                }
            }
            cursor.close()
        }
        return list
    }
}