package com.example.calculator.ui.widgets

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun IconRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    colors: IconRadioButtonColors = IconRadioButtonDefaults.colors(),
    interactionSource: MutableInteractionSource? = null,
    icon: (@Composable () -> Unit)? = null,
) {
    val radioColor = colors.radioColor(enabled, selected)
    val selectableModifier =
        if (onClick != null) {
            Modifier.selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.RadioButton,
                interactionSource = interactionSource,
                indication = ripple(
                    bounded = false,
                    radius = IconRadioButtonTokens.StateLayerSize / 2
                ),
            )
        } else {
            Modifier
        }
    Box(
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier
                .then(
                    if (onClick != null) {
                        Modifier.minimumInteractiveComponentSize()
                    } else {
                        Modifier
                    }
                )
                .then(selectableModifier)
                .wrapContentSize(Alignment.Center)
                .padding(RadioButtonPadding)
                .requiredSize(IconRadioButtonTokens.IconSize)
        ) {
            // Draw the radio button
            val strokeWidth = RadioStrokeWidth.toPx()
            drawCircle(
                radioColor.value,
                radius = (IconRadioButtonTokens.IconSize / 2).toPx() - strokeWidth / 2,
                style = Stroke(width = strokeWidth),
            )
            drawCircle(
                if (selected) radioColor.value else Color.Transparent,
                radius = (IconRadioButtonTokens.IconSize / 2).toPx() - strokeWidth / 2,
                style = Fill,
            )
            if (selected)
                drawPath(
                    path = Path().apply {
                        moveTo(size.width * 0.3f, size.height * 0.55f)
                        lineTo(size.width * 0.45f, size.height * 0.7f)
                        lineTo(size.width * 0.75f, size.height * 0.4f)
                    },
                    color = Color.White,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round
                    )
                )
        }
        if (icon != null) icon()
    }
}

object IconRadioButtonDefaults {
    @Composable
    fun colors() = MaterialTheme.colorScheme.defaultIconRadioButtonColors

    @Composable
    fun colors(
        selectedColor: Color = IconRadioButtonTokens.SelectedIconColor,
        unselectedColor: Color = IconRadioButtonTokens.UnselectedIconColor,
        disabledSelectedColor: Color = IconRadioButtonTokens.DisabledSelectedIconColor
            .copy(alpha = IconRadioButtonTokens.DisabledSelectedIconOpacity),
        disabledUnselectedColor: Color = IconRadioButtonTokens.DisabledUnselectedIconColor
            .copy(alpha = IconRadioButtonTokens.DisabledUnselectedIconOpacity),
    ): IconRadioButtonColors = copy(
        selectedColor,
        unselectedColor,
        disabledSelectedColor,
        disabledUnselectedColor,
    )

    fun copy(
        selectedColor: Color,
        unselectedColor: Color,
        disabledSelectedColor: Color,
        disabledUnselectedColor: Color
    ): IconRadioButtonColors {
        return IconRadioButtonColors(
            selectedColor = selectedColor,
            unselectedColor = unselectedColor,
            disabledSelectedColor = disabledSelectedColor,
            disabledUnselectedColor = disabledUnselectedColor,
        )
    }

    internal var defaultIconRadioButtonColorsCached: IconRadioButtonColors? = null

    internal val ColorScheme.defaultIconRadioButtonColors: IconRadioButtonColors
        get() = defaultIconRadioButtonColorsCached
            ?: IconRadioButtonColors(
                selectedColor = IconRadioButtonTokens.SelectedIconColor,
                unselectedColor = IconRadioButtonTokens.UnselectedIconColor,
                disabledSelectedColor =
                    IconRadioButtonTokens.DisabledSelectedIconColor,
                disabledUnselectedColor =
                    IconRadioButtonTokens.DisabledUnselectedIconColor,
            ).also { defaultIconRadioButtonColorsCached = it }
}

internal object IconRadioButtonTokens {
    val IconSize = 20.0.dp
    val StateLayerSize = 40.0.dp
    val SelectedIconColor: Color
        get() = Color(0xFF0B57D0)
    val UnselectedIconColor: Color
        get() = Color.Gray.copy(.4f)
    val DisabledSelectedIconColor: Color
        get() = Color.Gray.copy(.2f)
    val DisabledSelectedIconOpacity: Float = 0.38f
    val DisabledUnselectedIconColor: Color
        get() = Color.Gray.copy(.2f)
    val DisabledUnselectedIconOpacity: Float = 0.38f
}

@Immutable
class IconRadioButtonColors(
    private val selectedColor: Color,
    private val unselectedColor: Color,
    private val disabledSelectedColor: Color,
    private val disabledUnselectedColor: Color,
) {

    @Composable
    internal fun radioColor(enabled: Boolean, selected: Boolean): State<Color> {
        val target =
            when {
                enabled && selected -> selectedColor
                enabled && !selected -> unselectedColor
                !enabled && selected -> disabledSelectedColor
                else -> disabledUnselectedColor
            }

        // If not enabled 'snap' to the disabled state, as there should be no animations between
        // enabled / disabled.
        return if (enabled) {
            // TODO Load the motionScheme tokens from the component tokens file
            animateColorAsState(target)
        } else {
            rememberUpdatedState(target)
        }
    }


}

private val RadioButtonPadding = 2.dp
private val RadioStrokeWidth = 2.dp