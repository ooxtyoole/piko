package dev.piko.ui.screens.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.runtime.Composable
import dev.piko.data.auth.ClipFeedFilterDefaults

/** 设置行的说明。读的人要知道短于这一档的视频不会进信息流。 */
internal fun clipMinDurationSummary(seconds: Int): String = when (val normalized = ClipFeedFilterDefaults.normalize(seconds)) {
    0 -> "不限制"
    else -> "短于 ${clipMinDurationLabel(normalized)} 的不放"
}

internal fun clipMinDurationLabel(seconds: Int): String = when (seconds) {
    0 -> "不限制"
    else -> if (seconds % 60 == 0) "${seconds / 60} 分钟" else "$seconds 秒"
}

/**
 * 信息流最短时长。点选即生效并关闭。没有时长的视频是紧挨着的另一行开关。
 */
@Composable
internal fun ClipMinDurationDialog(
    seconds: Int,
    onSelect: (Int) -> Unit,
    onDismiss: () -> Unit,
) {
    SettingsChoiceDialog(
        icon = Icons.Outlined.Timer,
        title = "最短时长",
        description = "短于所选的不放进信息流。一分钟以内的多是样片或花絮。",
        onDismiss = onDismiss,
    ) {
        ClipFeedFilterDefaults.MinDurationChoices.forEach { choice ->
            SettingsChoiceOption(
                title = clipMinDurationLabel(choice),
                selected = choice == ClipFeedFilterDefaults.normalize(seconds),
                onClick = {
                    onSelect(choice)
                    onDismiss()
                },
            )
        }
    }
}
