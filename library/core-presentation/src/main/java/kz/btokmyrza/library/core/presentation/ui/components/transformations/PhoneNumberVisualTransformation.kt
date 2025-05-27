package kz.btokmyrza.library.core.presentation.ui.components.transformations

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import kotlin.math.min

class PhoneNumberVisualTransformation(
    private val mask: String,
    private val placeholder: Char = '#', // Character in mask representing a digit
) : VisualTransformation {

    private val maxDigitsInMask = mask.count { it == placeholder }

    override fun filter(text: AnnotatedString): TransformedText {
        val originalDigits = text.text.filter { it.isDigit() }
        val trimmedDigits = originalDigits.take(maxDigitsInMask)

        if (trimmedDigits.isEmpty()) {
            return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        }

        var maskedText = ""
        var digitIndex = 0
        var maskIndex = 0

        while (maskIndex < mask.length && digitIndex < trimmedDigits.length) {
            if (mask[maskIndex] == placeholder) {
                maskedText += trimmedDigits[digitIndex]
                digitIndex++
            } else {
                maskedText += mask[maskIndex]
            }
            maskIndex++
        }
        // Append remaining mask literals if the mask is longer than the digits allow for placeholders
        // For example, if mask is "(###) " and only 2 digits are entered, it will be "(12".
        // If we want to ensure trailing literals appear if their group is "touched":
        // while (maskIndex < mask.length && digitIndex == trimmedDigits.length) {
        //    if (mask[maskIndex] != placeholder) {
        //        maskedText += mask[maskIndex]
        //        maskIndex++
        //    } else {
        //        break // Next part of mask requires a digit not available
        //    }
        // }
        return TransformedText(
            AnnotatedString(maskedText),
            PhoneOffsetMapper(mask, placeholder, maskedText, trimmedDigits.length)
        )
    }

    private class PhoneOffsetMapper(
        private val mask: String,
        private val placeholder: Char,
        private val transformedText: String, // The actual currently displayed masked text
        private val originalDigitsLength: Int, // Number of actual digits entered by user
    ) : OffsetMapping {

        override fun originalToTransformed(offset: Int): Int { // offset in original (unmasked) digits
            var consumedOriginalDigits = 0
            var transformedCursorPosition = 0
            mask.forEach { maskChar ->
                if (transformedCursorPosition >= transformedText.length) {
                    return transformedText.length
                } // Cap at current transformed length

                if (consumedOriginalDigits == offset && maskChar != placeholder) {
                    // If we've placed all `offset` digits, and current mask char is a literal,
                    // keep advancing transformedCursorPosition over these literals.
                    transformedCursorPosition++
                } else if (maskChar == placeholder) {
                    if (consumedOriginalDigits < offset) {
                        consumedOriginalDigits++
                        transformedCursorPosition++
                    } else { // Found position for `offset` digits, stop if next is placeholder
                        return transformedCursorPosition
                    }
                } else { // Mask char is a literal
                    transformedCursorPosition++
                }
            }
            return min(transformedCursorPosition, transformedText.length)
        }

        // offset in transformed (masked) text
        override fun transformedToOriginal(offset: Int): Int {
            var originalCursorPosition = 0
            var currentTransformedChars = 0
            mask.forEach { maskChar ->
                if (currentTransformedChars >= offset) {
                    return min(originalCursorPosition, originalDigitsLength)
                }

                if (maskChar == placeholder) {
                    originalCursorPosition++
                }

                currentTransformedChars++
            }

            return min(originalCursorPosition, originalDigitsLength)
        }
    }
}
