
/**
 * Employee Info Screen
 *
 *
 */

package com.example.cmpt395aurora.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.cmpt395aurora.database.employees.EmployeeViewModel


// This was actually really hard to implement! please don't modify unless it works!
// needs more clean up not as nice as our other designed pages yet
@Composable
fun EmployeeInfoScreen(viewModel: EmployeeViewModel) {
    DataFields(viewModel)
}


fun displayUserInfo(viewModel: EmployeeViewModel): String { // Changed return type to String

    val firstName = viewModel.fname.value
    val lastName = viewModel.lname.value
    val nickName = viewModel.nname.value
    val email = viewModel.email.value
    val phoneNumber = viewModel.pnumber.value
    val isActive = viewModel.isActive.value
    val trainedForOpening = viewModel.opening.value
    val trainedForClosing = viewModel.closing.value

    val userInfo = StringBuilder()
    userInfo.append("First Name: $firstName\n")
    userInfo.append("Last Name: $lastName\n")
    userInfo.append("Nick Name: $nickName\n")
    userInfo.append("Email: $email\n")
    userInfo.append("Phone Number: $phoneNumber\n")
    userInfo.append("Is Active?: ${if (isActive) "Yes" else "No"}\n")
    userInfo.append("Trained for Opening?: ${if (trainedForOpening) "Yes" else "No"}\n")
    userInfo.append("Trained for Closing?: ${if (trainedForClosing) "Yes" else "No"}")

    // Convert StringBuilder to String before returning
    return userInfo.toString()
}

// this will move back inside AddEmployeeScreen later
@Composable
fun DataFields(viewModel: EmployeeViewModel) {
    val info = displayUserInfo(viewModel)
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = info,
            modifier = Modifier.align(Alignment.TopStart)
        )
    }

}



///**
// * Employee Info Screen
// *
// *
// */
//
//package com.example.cmpt395aurora.screens
//
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import com.example.cmpt395aurora.database.employees.EmployeeViewModel
//
//
//// This was actually really hard to implement! please don't modify unless it works!
//// needs more clean up not as nice as our other designed pages yet
//@Composable
//fun EmployeeInfoScreen(viewModel: EmployeeViewModel) {
//    DataFields(viewModel)
//}
//
//
//fun displayUserInfo(viewModel: EmployeeViewModel): StringBuilder {
//
//    val firstName = viewModel.fname
//    val lastName = viewModel.lname
//    val nickName = viewModel.nname
//    val email = viewModel.email
//    val phoneNumber = viewModel.pnumber
//    val isActive = viewModel.isActive
//    val trainedForOpening = viewModel.opening
//    val trainedForClosing = viewModel.closing
//
//    val userInfo = StringBuilder()
//    userInfo.append("First Name: $firstName\n")
//    userInfo.append("Last Name: $lastName\n")
//    userInfo.append("Nick Name: $nickName\n")
//    userInfo.append("Email: $email\n")
//    userInfo.append("Phone Number: $phoneNumber\n")
//    userInfo.append("Is Active?: ${if (isActive) "Yes" else "No"}\n")
//    userInfo.append("Trained for Opening?: ${if (trainedForOpening) "Yes" else "No"}\n")
//    userInfo.append("Trained for Closing?: ${if (trainedForClosing) "Yes" else "No"}")
//
//    // Display the information using a Toast or any other appropriate UI component
//    return userInfo
//}
//
//// this will move back inside AddEmployeeScreen later
//@Composable
//fun DataFields(viewModel: EmployeeViewModel) {
//    val info = displayUserInfo(viewModel)
//    Box(modifier = Modifier.fillMaxSize()) {
//        Text(
//            text = info,
//            modifier = Modifier.align(Alignment.L)
//        )
//    }
//
//}