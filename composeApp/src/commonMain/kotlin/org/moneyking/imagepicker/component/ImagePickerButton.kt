package org.moneyking.imagepicker.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.fontFamilyResource
import org.moneyking.imagepicker.MR
import org.moneyking.imagepicker.theme.Gray900

@Composable
fun ImagePickerButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(50.dp))
            .clickable(onClick = onClick)
            .background(color = Gray900),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = White,
            fontFamily = fontFamilyResource(MR.fonts.Pretendard.semiBold),
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal,
            fontSize = 18.sp,
            lineHeight = 27.sp,
        )
    }
}