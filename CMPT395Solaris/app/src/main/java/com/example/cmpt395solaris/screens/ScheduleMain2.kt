/**
 *   Schedule Main page
 *   v1.02
 *
 *   v1.02
 *   Added top bar text field logic
 *
 *   v1.01
 *   Added navigation to the dates
 *
 */

package com.example.cmpt395solaris.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cmpt395solaris.database.TopBarViewModel
import com.example.cmpt395solaris.ui.theme.Purple80
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.ceil


@ExperimentalMaterial3Api
@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun ScheduleMain2(navController: NavController, topBarViewModel: TopBarViewModel) {

    topBarViewModel.updateTopBarText("Scheduling")

    val selectedDate = remember { mutableStateOf(LocalDate.now()) }

    // Populate this list with your data
    val datesWithIndicators: List<LocalDate> = listOf(
//        LocalDate.of(2024, 4, 15),  // testing
//        LocalDate.of(2024, 4, 20),
//        LocalDate.of(2024, 4, 25)
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 0.dp)
        ) {
            item {
                CustomDatePicker(
                    month = remember { mutableStateOf(YearMonth.now()) }, // Or any other month
                    selectedDate = selectedDate,
                    datesWithIndicators = datesWithIndicators,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            selectedDate.value?.let { localDate ->
                                val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
                                if (isWeekend2(date)) {
                                    val dateString = dateFormat(date)
                                    navController.navigate("schedule3/$dateString")
                                } else {
                                    val dateString = dateFormat(date)
                                    navController.navigate("schedule2/$dateString")
                                }
                            }
                        },
                        enabled = selectedDate.value != null,
                        modifier = Modifier
                            .width(150.dp)
                            .height(50.dp)
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

fun dateFormat(date: Date?): String? {
    if (date == null) return null
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(date)
}

fun isWeekend2(date: Date?): Boolean {
    if (date == null) return false
    val calendar = Calendar.getInstance()
    calendar.time = date
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomDatePicker(
    month: MutableState<YearMonth>,  // Change month to a MutableState
    selectedDate: MutableState<LocalDate>,
    datesWithIndicators: List<LocalDate>,
    modifier: Modifier
) {
    val daysInMonth = month.value.lengthOfMonth()
    val firstDayOfWeek = (month.value.atDay(1).dayOfWeek.ordinal + 1) % 7
    val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")

    // Add variables for the dropdown menu
    var showDropdown by remember { mutableStateOf(false) }
    val months = Month.entries

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {  // Add padding to the sides
        // Replace the Text composable with a Box containing a Text and an IconButton
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(text = "${month.value.month.name.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }} ${month.value.year}", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(bottom = 16.dp))  // Add the year to the label
            IconButton(onClick = { showDropdown = true }) {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Month dropdown")
            }
            DropdownMenu(showDropdown, onDismissRequest = { showDropdown = false }) {
                months.forEach { dropdownMonth ->
// DropdownMenuItem(onClick = {
//     month.value = YearMonth.of(month.value.year, dropdownMonth)
//     showDropdown = false
// }) {
                    Log.d("DropdownMenuItem", "Rendering Text composable for dropdownMonth: $dropdownMonth")
                    Text(text = dropdownMonth.name.lowercase(Locale.getDefault())
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
// }

                }
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            daysOfWeek.forEach { dayOfWeek ->
                Text(text = dayOfWeek, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            }
        }
        val numWeeks = ceil((firstDayOfWeek + daysInMonth) / 7f).toInt()
        Column {
            repeat(numWeeks) { week ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    repeat(7) { dayOfWeek ->
                        val dayOfMonth = week * 7 + dayOfWeek - firstDayOfWeek + 1
                        if (dayOfMonth in 1..daysInMonth) {
                            val date = month.value.atDay(dayOfMonth)
                            Box(  // Use a Box to center the text within the smaller box
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .background(if (date in datesWithIndicators) Purple80 else MaterialTheme.colorScheme.surface)
                                    .clickable { if (date in datesWithIndicators) selectedDate.value = date }
                                    .padding(vertical = 12.dp, horizontal = 8.dp)  // Adjust the padding as needed
                                    .weight(1f)  // Make all boxes the same size
                                    .fillMaxWidth()  // Fill the width of the parent
                            ) {
                                Text(
                                    text = dayOfMonth.toString(),
                                    fontSize = 16.sp,  // Make the numerical dates larger
                                    maxLines = 1,
                                    color = if (date in datesWithIndicators) Color.White else Color.Black,
                                    textAlign = TextAlign.Center  // Center the text
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}




