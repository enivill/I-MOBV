package sk.mobv.androidprojectschool.data

import android.content.Context
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import sk.mobv.androidprojectschool.R
import sk.mobv.androidprojectschool.model.Business
import java.io.IOException

class DataSource_class {

    fun loadBusinesses(context: Context): MutableList<Business> {
        val businesses: MutableList<Business> = mutableListOf<Business>()
        // As we have JSON object, so we are getting the object
        //Here we are calling a Method which is returning the JSON object
        val obj = JSONObject(getJsonDataFromFile(context)!!)
        // fetch JSONArray named elements by using getJSONArray
        val elementsArray = obj.getJSONArray("elements")


        // Get the users data using for loop i.e. id, name, email and so on
        for (i in 0 until elementsArray.length()) {
            // Create a JSONObject for fetching single User's Data
            val element = elementsArray.getJSONObject(i)

            // create a object for getting tag data from JSONObject
            val tag = element.getJSONObject("tags")
            // Fetch id store it in variable
            val id = element.getLong("id")
            val name = getValue(tag, "name")
            val latitude = element.getDouble("lat").toFloat()
            val longitude = element.getDouble("lon").toFloat()
            val website = getValue(tag, "website")

            // Now add all the variables to the data model class and the data model class to the array list.
            val business =
                Business(id, name, latitude, longitude, website)

            // add the details in the list
            businesses.add(business)
        }
        return businesses
    }

    private fun getValue(obj: JSONObject, tagName: String): String {
        return try {
            obj.getString(tagName)
        } catch (e: JSONException) {
            ""
        }
    }

    private fun getJsonDataFromFile(context: Context): String? {
        val jsonString: String
        try {
            jsonString =
                context.resources.openRawResource(R.raw.pubs).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}