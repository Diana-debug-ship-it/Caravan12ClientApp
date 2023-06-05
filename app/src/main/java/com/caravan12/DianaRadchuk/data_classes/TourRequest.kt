package com.caravan12.DianaRadchuk.data_classes

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

data class TourRequest(

    var email: String? = "",
    var from: String? = "",
    var destination: String? = "",
    var dateOfDeparture: String? = "",
    var nights: String? = "",
    var adults: String? = "",
    var comments: String? = "",
    var children: String? = "",
    var meals: String? = "",
    var rating: String? = "",
    var id: String? = null
)
