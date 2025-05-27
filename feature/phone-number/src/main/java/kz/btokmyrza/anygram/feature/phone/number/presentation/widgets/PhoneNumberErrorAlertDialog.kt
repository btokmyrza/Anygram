package kz.btokmyrza.anygram.feature.phone.number.presentation.widgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kz.btokmyrza.anygram.feature.phone.number.presentation.mapper.mapToPhoneNumberUiError

@Composable
internal fun PhoneNumberErrorAlertDialog(
    errorMessage: String,
    onDismissRequest: () -> Unit,
) {
    val context = LocalContext.current
    val error = mapToPhoneNumberUiError(context, errorMessage)

    AlertDialog(
        title = {
            Text(text = error.title)
        },
        text = {
            Text(text = error.message)
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("OK")
            }
        },
        onDismissRequest = onDismissRequest,
    )
}

@Preview(showBackground = true)
@Composable
private fun PhoneNumberErrorAlertDialogPreview() {
    PhoneNumberErrorAlertDialog(
        errorMessage = "PHONE_NUMBER_INVALID",
        onDismissRequest = {},
    )
}
