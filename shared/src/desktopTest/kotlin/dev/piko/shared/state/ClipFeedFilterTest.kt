package dev.piko.shared.state

import io.github.nihildigit.pikpak.FileKind
import io.github.nihildigit.pikpak.FileStat
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/** 信息流按设置里的最短时长、以及「没有时长也列入」收视频。 */
class ClipFeedFilterTest {

    @Test
    fun `listing without duration is skipped until the setting says otherwise`() {
        val file = video(duration = null)
        assertFalse(file.meetsClipDuration(60_000, includeUntimed = false))
        assertTrue(file.meetsClipDuration(60_000, includeUntimed = true))
        assertTrue(file.meetsClipDuration(0, includeUntimed = false))
    }

    @Test
    fun `known duration follows the minimum`() {
        assertTrue(video("193").meetsClipDuration(60_000, includeUntimed = false))
        assertFalse(video("45").meetsClipDuration(60_000, includeUntimed = false))
        assertFalse(video("45").meetsClipDuration(60_000, includeUntimed = true))
        assertTrue(video("45").meetsClipDuration(30_000, includeUntimed = false))
    }

    @Test
    fun `vault entries are kept without a listing duration`() {
        val file = video(duration = null).copy(id = "piko-vault:entry")
        assertTrue(file.meetsClipDuration(60_000, includeUntimed = false))
    }

    private fun video(duration: String?) = FileStat(
        kind = FileKind.FILE,
        id = "file",
        name = "clip.mp4",
        params = if (duration == null) emptyMap() else mapOf("duration" to duration),
    )
}
