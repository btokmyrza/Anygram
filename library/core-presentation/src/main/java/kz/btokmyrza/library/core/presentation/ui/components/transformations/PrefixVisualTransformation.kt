package kz.btokmyrza.library.core.presentation.ui.components.transformations

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PrefixVisualTransformation(private val prefix: String) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val prefixOffset = prefix.length

        val prefixOffsetTranslator = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {
                return offset + prefixOffset
            }

            override fun transformedToOriginal(offset: Int): Int {
                return (offset - prefixOffset).coerceAtLeast(0)
            }
        }

        val builder = AnnotatedString.Builder()
        builder.append(prefix)
        builder.append(text.text)

        return TransformedText(
            builder.toAnnotatedString(),
            prefixOffsetTranslator,
        )
    }
}
