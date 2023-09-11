import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
public fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
) {
    ElevatedButton(
        onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        ),
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
        elevation = ButtonDefaults.buttonElevation(8.dp)
    ) {
        Text(
            text,
            style = TextStyle.Default.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold),
        )
    }
}