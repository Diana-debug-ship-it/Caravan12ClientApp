package com.caravan12.DianaRadchuk.data_classes

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

data class TourRequest(
    var uid: String? = "",
    var applicantEmail: String? = "",
    var from: String? = "",
    var where: String? = "",
    var dateOfDeparture: String? = "",
    var nights: String? = "",
    var howManyPeople: String? = "",
    var comments: String? = ""
)
