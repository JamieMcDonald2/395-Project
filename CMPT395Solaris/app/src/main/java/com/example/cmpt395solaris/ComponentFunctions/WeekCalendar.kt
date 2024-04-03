/**
 * Weekly Calendar v1.03
 *
 * - Implement calendar based on figma design - no imports except for card pop-up (later)
 *
 * 1.01
 *  - https://kotlinlang.org/api/kotlinx-datetime/kotlinx-datetime/kotlinx.datetime/-local-date/
 *  - https://developer.android.com/reference/java/util/Calendar
 *
 * 1.02
 *  - Added ability to show correct dates
 *  - can select date, shows today's date
 *
 *  1.03
 *  - added clickable modifiers to DayNumber
 *  - added overlay logic for card (popup)
 *  - https://developer.android.com/jetpack/compose/dialogs
 *  - https://developer.android.com/jetpack/compose/modifier
 *  - https://developer.android.com/jetpack/compose/text
 */

package com.example.cmpt395solaris.ComponentFunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.relay.compose.RelayText
import java.util.Calendar

// older way to get date because of API 24 requirement
@Composable
fun ThisWeek() {
    val selectedDate = remember { mutableStateOf<Int?>(null) }
    val calendar = Calendar.getInstance()
    val today = calendar.get(Calendar.DAY_OF_MONTH)

    // Calculate the dates for the current week
    val datesOfWeek = remember {
        (0..6).map { i ->
            calendar.set(Calendar.DAY_OF_WEEK, i + 1)
            val date = calendar.get(Calendar.DAY_OF_MONTH)
            date
        }
    }

    Column {
        Text(
            text = "This Week",
            fontSize = 15.sp,
            letterSpacing = 0.15.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(100),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DayComposable(day = "S")
            DayComposable(day = "M")
            DayComposable(day = "T")
            DayComposable(day = "W")
            DayComposable(day = "T")
            DayComposable(day = "F")
            DayComposable(day = "S")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            datesOfWeek.forEach { date ->
                DayNumber(date, date == today, selectedDate)
            }
        }
    }
}

@Composable
fun DayComposable(day: String, modifier: Modifier = Modifier) {
    RelayText(
        content = day,
        fontSize = 15.sp,
        letterSpacing = 0.15.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight(100),
        modifier = modifier
            .wrapContentHeight(
                align = Alignment.CenterVertically,
                unbounded = true
            )
            .width(IntrinsicSize.Min)
    )
}

@Composable
fun DayNumber(day: Int, isToday: Boolean, selectedDate: MutableState<Int?>) {
    val isSelected = day == selectedDate.value
    val openDialog = remember { mutableStateOf(false) }

    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.surface
    }
    val contentColor = when {
        isSelected -> MaterialTheme.colorScheme.onPrimary
        isToday -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurface
    }
    val borderColor = if (isToday) MaterialTheme.colorScheme.primary else Color.Transparent

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color = backgroundColor)
            .border(2.dp, borderColor, CircleShape)
            .clickable(onClick = {
                openDialog.value = true
                selectedDate.value = day
            }),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.toString(),
            color = when {
                isToday && isSelected -> MaterialTheme.colorScheme.onPrimary
                isToday -> MaterialTheme.colorScheme.primary
                else -> contentColor
            },
            fontSize = 16.sp
        )
    }

/**
 * This is where the Dialog box will go eventually but not tonight !
 * - will need to pull data from database as well
 */
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text("Day Here") },
            text = { Text("This is an example dialog by Jamie.") },
            confirmButton = {
                Button(onClick = {
                    openDialog.value = false
                    selectedDate.value = null
                }) {
                    Text("Close Me.")
                }
            }
        )
    }
}












