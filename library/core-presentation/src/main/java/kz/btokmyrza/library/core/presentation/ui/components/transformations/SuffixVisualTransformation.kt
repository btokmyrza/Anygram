package kz.btokmyrza.library.core.presentation.ui.components.transformations

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class SuffixVisualTransformation(private val suffix: String) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text

        // If the input is empty, return an empty string
        if (originalText.isEmpty()) {
            return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        }

        // Append " months" to the input text
        val transformedText = "$originalText $suffix"

        // Create an offset mapping that ignores the added length of the " months" suffix
        val offsetMapping = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {
                // In transformed text,
                // the offset is the same as the original up to the length of the original text.
                return offset.coerceAtMost(originalText.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                // In original text, the offset does not account for the " months" suffix.
                return offset.coerceAtMost(originalText.length)
            }
        }

        // Return the transformed text and the offset mapping
        return TransformedText(
            text = AnnotatedString(transformedText),
            offsetMapping = offsetMapping,
        )
    }
}
