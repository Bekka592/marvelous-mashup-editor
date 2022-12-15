package marvelous_mashup.team29.ui_util.styling;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import marvelous_mashup.team29.ui_util.AbstractGameWithMusic;
import marvelous_mashup.team29.ui_util.UIConstants;
import marvelous_mashup.team29.ui_util.UISettings;
import marvelous_mashup.team29.ui_util.options.ColorEnum;
import marvelous_mashup.team29.ui_util.options.ComponentsSizeEnum;
import marvelous_mashup.team29.ui_util.options.TextSizeEnum;
import marvelous_mashup.team29.ui_util.options.TextStyleEnum;

import java.util.Locale;

import static marvelous_mashup.team29.ui_util.UIConstants.*;

/**
 * This is your class, if you like to create a UI component, like a TextButton, Slider etc.
 * This class has many methods to create a UI component.
 * For eg creating a TextButton, call configureTextButton("your prefered text"). Moreover, you can change most of the design
 * if you use any other method.
 */
public class ComponentCreator {
    private static final MarvelStyle STYLE = new MarvelStyle();

    private ComponentCreator() {
    }

    /**
     * gets the default ColorScheme
     * if you like to create a new, here is the information about which color has to be at which position
     * Colorscheme: primary color means color for the outer border and inner
     * primary color (normal state), second border, primary color (over state), primary color (clicked state)
     * @return colorscheme
     */
    private static Color[] getColorScheme() {
        return UISettings.getStylingDefaults().getColorScheme();
    }

    /**
     * Configures a TextButton with the given text.
     * all used values can be set (following methods sets all non given values to default)
     * if you create one button with e.g. a larger width do not affect any other button sizes
     *
     * @param text   text of the button
     * @param width  width (ComponentSizeEnum)
     * @param height height (ComponentSizeEnum)
     * @param scheme colorscheme (for more information, please look up to the comment on colorscheme next to getColorScheme())
     * @param font   Color of the font
     * @param style  TextStyleEnum
     * @param size   TextSizeEnum
     * @return TextButton
     */
    public static TextButton configureButton(String text, ComponentsSizeEnum width, ComponentsSizeEnum height,
                                             Color[] scheme, Color font, TextStyleEnum style, TextSizeEnum size) {
        var button = new TextButton(text.toUpperCase(Locale.ROOT),
                STYLE.getTextButtonStyle(width, height, scheme, font, style, size));
        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AbstractGameWithMusic.playButtonClickSound();
                return false;
            }
        });
        return button;
    }

    public static TextButton configureButton(String text) {
        return configureButton(text, ComponentsSizeEnum.COMPONENT_WIDTH);
    }

    public static TextButton configureButton(String text, ComponentsSizeEnum width) {
        return configureButton(text, width, ComponentsSizeEnum.COMPONENT_HEIGHT, getColorScheme(),
                UISettings.getStylingDefaults().getPrimaryFontColor(),
                UISettings.getStylingDefaults().getFontStyle(),
                UISettings.getStylingDefaults().getTextSize());
    }

    public static TextButton configureButton(String text, Color[] scheme) {
        return configureButton(text, ComponentsSizeEnum.COMPONENT_WIDTH,
                ComponentsSizeEnum.COMPONENT_HEIGHT, scheme,
                UISettings.getStylingDefaults().getPrimaryFontColor(),
                UISettings.getStylingDefaults().getFontStyle(),
                UISettings.getStylingDefaults().getTextSize());
    }

    public static TextButton configureButton(String text, Color font) {
        return configureButton(text, ComponentsSizeEnum.COMPONENT_WIDTH,
                ComponentsSizeEnum.COMPONENT_HEIGHT, getColorScheme(), font,
                UISettings.getStylingDefaults().getFontStyle(),
                UISettings.getStylingDefaults().getTextSize());
    }

    public static TextButton configureButton(String text, TextSizeEnum size) {
        return configureButton(text, UISettings.getStylingDefaults().getFontStyle(), size);
    }

    public static TextButton configureButton(String text, TextStyleEnum style) {
        return configureButton(text, style, UISettings.getStylingDefaults().getTextSize());
    }

    public static TextButton configureButton(String text, TextStyleEnum style, TextSizeEnum size) {
        return configureButton(text, ComponentsSizeEnum.COMPONENT_WIDTH,
                ComponentsSizeEnum.COMPONENT_HEIGHT, getColorScheme(),
                UISettings.getStylingDefaults().getPrimaryFontColor(),
                style, size);
    }

    /**
     * Configures a label with the given text.
     * All style parameters have been set to default values.
     * If you like to have a different font or font size, just add your preferred TextStyleEnum value or TextSizeEnum value.
     * If you like to have a different font color, just add a color.
     * Watch out:
     * if you like to change color and style or color and size, you have to give all three values in the following order
     * TextStyleEnum, TextSizeEnum, Color
     *
     * @param text Text
     * @return Label
     */
    public static Label configureLabel(String text) {
        return configureLabel(text, UISettings.getStylingDefaults().getSecondaryFontColor());
    }

    public static Label configureLabel(String text, Color color) {
        return configureLabel(text, UISettings.getStylingDefaults().getFontStyle(),
                UISettings.getStylingDefaults().getTextSize(), color);
    }

    public static Label configureLabel(String text, TextSizeEnum size) {
        return configureLabel(text, UISettings.getStylingDefaults().getFontStyle(), size);
    }

    public static Label configureLabel(String text, TextStyleEnum style) {
        return configureLabel(text, style, UISettings.getStylingDefaults().getTextSize());
    }

    public static Label configureLabel(String text, TextStyleEnum style, TextSizeEnum size) {
        return configureLabel(text, style, size, UISettings.getStylingDefaults().getSecondaryFontColor());
    }

    public static Label configureLabel(String text, TextStyleEnum style, TextSizeEnum size, Color color) {
        return new Label(text, STYLE.getLabelStyle(style, size, color));
    }

    /**
     * Configures a slider with the given length.
     * All style parameters have been set to default values.
     * If you like to have a different length, just add a ComponentSizeEnum length value.
     * If you like to have a different knob color, just add your preferred knob color value.
     * If you like to have a different colorscheme, just add your preferred colorscheme.
     * (for more information about the colorscheme, please look up to the comment on colorscheme next to getColorScheme())
     *
     * @return Label
     */
    public static Slider configureSlider() {
        return configureSlider(UISettings.getStylingDefaults().getLogoColor());
    }

    public static Slider configureSlider(Color knob) {
        return configureSlider(ComponentsSizeEnum.COMPONENT_WIDTH_BIG, knob);
    }

    public static Slider configureSlider(ComponentsSizeEnum width) {
        return configureSlider(width, UISettings.getStylingDefaults().getLogoColor());
    }

    public static Slider configureSlider(ComponentsSizeEnum width, Color knob) {
        return configureSlider(getColorScheme(), knob, width);
    }

    public static Slider configureSlider(Color[] scheme, Color knob) {
        return configureSlider(scheme, knob, ComponentsSizeEnum.COMPONENT_WIDTH_BIG);
    }

    public static Slider configureSlider(Color[] scheme, Color knob, ComponentsSizeEnum width) {
        var slider = new Slider(0, 1, 0.05f, false, STYLE.getSliderStyle(scheme, knob, width));
        slider.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AbstractGameWithMusic.playButtonClickSound();
                return false;
            }
        });
        return slider;
    }

    /**
     * Creating a TextField where only the characters 0-9, and only up to {@link UIConstants#TEXT_LENGTH_IN_TEXT_FIELD_SMALL}
     * of them, can be entered.
     * Using the methods below to design the text field.
     *
     * @param optional if this is true the text field will have a lighter background color
     * @return the created text field
     */
    public static TextField configureSmallNumberTextField(boolean optional) {
        return configureFilterTextField(TEXT_LENGTH_IN_TEXT_FIELD_SMALL, new TextField.TextFieldFilter.DigitsOnlyFilter(), optional);
    }

    /**
     * Creating a TextField where only the characters 0-9, and only up to {@link UIConstants#TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM}
     * of them, can be entered.
     * Using the methods below to design the text field.
     *
     * @param optional if this is true the text field will have a lighter background color
     * @return the created text field
     */
    public static TextField configureBigNumberTextField(boolean optional) {
        return configureFilterTextField(TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM, new TextField.TextFieldFilter.DigitsOnlyFilter(), optional);
    }

    /**
     * Creating a TextField where only only up to {@link UIConstants#TEXT_LENGTH_IN_TEXT_FIELD_BIG} characters can be entered.
     * Using the methods below to design the text field.
     *
     * @param optional if this is true the text field will have a lighter background color
     * @return the created text field
     */
    public static TextField configureTextTextField(boolean optional) {
        return configureFilterTextField(TEXT_LENGTH_IN_TEXT_FIELD_BIG, (textField1, c) -> true, optional);
    }

    public static TextField configureFilterTextField(int maxLength, TextField.TextFieldFilter filter, boolean optional) {
        Color color = optional ? ColorEnum.THEME_COLOR.getColor() : ColorEnum.DARK_THEME_COLOR.getColor();
        var textField = configureBorderlessTextField(color);
        textField.setMaxLength(maxLength);
        textField.setTextFieldFilter(filter);
        return textField;
    }

    /**
     * Configures a text field.
     * All style parameters have been set to default values.
     * If you like to have a different size dimension, just add a width and height as {@link ComponentsSizeEnum} objects.
     * If you like to have a different text style, just add the style and the text size.
     * If you like to have a different colorscheme, just add the new inner color and the new border color.
     *
     * @return Label
     */
    public static TextField configureTextField() {
        return configureTextField(ComponentsSizeEnum.COMPONENT_WIDTH, ComponentsSizeEnum.COMPONENT_HEIGHT);
    }

    public static TextField configureTextField(ComponentsSizeEnum width, ComponentsSizeEnum height) {
        return configureTextField(UISettings.getStylingDefaults().getHighlightColor(),
                UISettings.getStylingDefaults().getBaseColor(),
                width, height, UISettings.getStylingDefaults().getFontStyle(),
                UISettings.getStylingDefaults().getTextSize());
    }

    public static TextField configureTextField(TextStyleEnum style, TextSizeEnum size) {
        return configureTextField(UISettings.getStylingDefaults().getHighlightColor(),
                UISettings.getStylingDefaults().getBaseColor(),
                ComponentsSizeEnum.COMPONENT_WIDTH, ComponentsSizeEnum.COMPONENT_HEIGHT, style, size);
    }

    public static TextField configureTextField(Color border, Color inner,
                                               ComponentsSizeEnum width, ComponentsSizeEnum height,
                                               TextStyleEnum style, TextSizeEnum size) {
        var textField = new TextField("", STYLE.getTextFieldStyle(border, inner, width, height, style, size));
        textField.setAlignment(Align.center);
        return textField;
    }

    public static Texture configureTexture(String source) {
        var texture = (Texture) UIConstants.ASSET_FINDER.get("textures/" + source);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return texture;
    }

    /**
     * same as a normal TextField, but without a border
     *
     * @param color      color of the textField
     * @param widthSize  width
     * @param heightSize height
     * @param style      TextStyle
     * @param size       TextSize
     * @return textField
     */
    public static TextField configureBorderlessTextField(Color color,
                                                         ComponentsSizeEnum widthSize, ComponentsSizeEnum heightSize,
                                                         TextStyleEnum style, TextSizeEnum size) {
        var textField = new TextField("", STYLE.getBorderlessTextFieldStyle(color, widthSize, heightSize, style, size));
        textField.setAlignment(Align.center);
        return textField;
    }

    public static TextField configureBorderlessTextField(Color inner) {
        return configureBorderlessTextField(inner, ComponentsSizeEnum.COMPONENT_WIDTH, ComponentsSizeEnum.COMPONENT_HEIGHT,
                UISettings.getStylingDefaults().getFontStyle(), UISettings.getStylingDefaults().getTextSize());
    }

    /**
     * configures a progressbar
     * All style parameters have been set to default values.
     * If you like to have a different length, just add your ComponentSizeEnum length value for width and height.
     *
     * @param scheme colorscheme (for more information, please look up to the comment on colorscheme next to getColorScheme())
     * @param width  width
     * @param height height
     * @return progressbar
     */
    public static ProgressBar configureProgressBar(Color[] scheme, ComponentsSizeEnum width, ComponentsSizeEnum height) {
        return new ProgressBar(0, 1, 0.5f, false, STYLE.getProgressBarStyle(width, height, scheme));
    }

    public static ProgressBar configureProgressBar() {
        return configureProgressBar(ComponentsSizeEnum.COMPONENT_WIDTH, ComponentsSizeEnum.COMPONENT_HEIGHT);
    }

    public static ProgressBar configureProgressBar(ComponentsSizeEnum width, ComponentsSizeEnum height) {
        return configureProgressBar(getColorScheme(), width, height);
    }

    /**
     * Creating a vertical Scrollbar that uses our {@link MarvelStyle}.
     * As a special feature this scroll pane directly focuses whenever its hovered, so there is no need to first click
     * on it - you can directly scroll without doing so.
     *
     * @param table the content of the scroll pane
     * @param stage that the scroll pane is laying on
     * @return the configured scroll pane
     */
    public static ScrollPane configureScrollPane(Table table, Stage stage) {
        var scrollPane = new ScrollPane(table, STYLE.getScrollPaneStyle());
        scrollPane.setVariableSizeKnobs(false);
        scrollPane.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                stage.setScrollFocus(scrollPane);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                stage.setScrollFocus(null);
            }
        });
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setFadeScrollBars(false);
        scrollPane.layout();
        return scrollPane;
    }

    /**
     * configures a dialog with the default style
     *
     * @return Dialog
     */
    public static Dialog configureDialog() {
        return new Dialog("", STYLE.getWindowStyle());
    }


    /**
     * configures a Tooltip with the default style
     *
     * @param text text
     * @return Tooltip
     */
    public static TextTooltip configureToolTip(String text) {
        return STYLE.getToolTipStyle(text);
    }
}
