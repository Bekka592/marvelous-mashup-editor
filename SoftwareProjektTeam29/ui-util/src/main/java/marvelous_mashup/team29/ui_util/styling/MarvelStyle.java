package marvelous_mashup.team29.ui_util.styling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import marvelous_mashup.team29.ui_util.UIConstants;
import marvelous_mashup.team29.ui_util.UISettings;
import marvelous_mashup.team29.ui_util.options.ColorEnum;
import marvelous_mashup.team29.ui_util.options.ComponentsSizeEnum;
import marvelous_mashup.team29.ui_util.options.TextSizeEnum;
import marvelous_mashup.team29.ui_util.options.TextStyleEnum;

/**
 * To create a component, please use the componentCreator. This class only provides the styling for the different components.
 * The styles are complete generated and you can set for every component e.g. a different color.
 * If you like to add a new Style for e.g. a slider, look up, what you need in the class Slider.SliderStyle from LIBGDX.
 * New fonts or font sizes can be added, if you create a new BitmapFont. Therefore, add your ttf file to the folder assets/fonts
 * and add it to generateFonts(), getFont() and as new Element in TextStyleEnum or TextSizeEnum.
 */
class MarvelStyle {
    private BitmapFont font;
    private BitmapFont smallFont;
    private BitmapFont bigFont;
    private BitmapFont boldFont;
    private BitmapFont smallBoldFont;
    private BitmapFont bigBoldFont;
    private BitmapFont comicFont;
    private BitmapFont smallComicFont;
    private BitmapFont bigComicFont;

    MarvelStyle() {
        generateFonts();
    }

    /**
     * generates all used fonts
     */
    private void generateFonts() {
        // Font: NotoSans-Regular
        var generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/NotoSans-Regular.ttf"));
        var parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color = UISettings.getStylingDefaults().getPrimaryFontColor();

        parameter.size = getScaled(TextSizeEnum.MEDIUM);
        font = generator.generateFont(parameter);

        parameter.size = getScaled(TextSizeEnum.BIG);
        bigFont = generator.generateFont(parameter);

        parameter.size = getScaled(TextSizeEnum.SMALL);
        smallFont = generator.generateFont(parameter);

        // Font: NotoSans-Bold
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/NotoSans-Bold.ttf"));

        parameter.size = getScaled(TextSizeEnum.MEDIUM);
        boldFont = generator.generateFont(parameter);

        parameter.size = getScaled(TextSizeEnum.BIG);
        bigBoldFont = generator.generateFont(parameter);

        parameter.size = getScaled(TextSizeEnum.SMALL);
        smallBoldFont = generator.generateFont(parameter);

        // Font: OdibeeSans
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/OdibeeSans-Regular.ttf"));

        parameter.size = getScaled(TextSizeEnum.MEDIUM);
        comicFont = generator.generateFont(parameter);

        parameter.size = getScaled(TextSizeEnum.BIG);
        bigComicFont = generator.generateFont(parameter);

        parameter.size = getScaled(TextSizeEnum.SMALL);
        smallComicFont = generator.generateFont(parameter);

        generator.dispose();
    }

    /**
     * returns font
     *
     * @param style TextStyleEnum
     * @param size  TextSizeEnum
     * @return font
     */
    private BitmapFont getFont(TextStyleEnum style, TextSizeEnum size) {
        return switch (style) {
            case COMIC -> switch (size) {
                case BIG -> bigComicFont;
                case SMALL -> smallComicFont;
                default -> comicFont;
            };
            case BOLD -> switch (size) {
                case BIG -> bigBoldFont;
                case SMALL -> smallBoldFont;
                default -> boldFont;
            };
            default -> switch (size) {
                case BIG -> bigFont;
                case SMALL -> smallFont;
                default -> font;
            };
        };
    }

    /**
     * creates a TextButtonStyle
     *
     * @param width  width
     * @param height height
     * @param scheme colorscheme
     * @param font   font (color)
     * @param style  TextStyleEnum
     * @param size   TextSizeEnum
     * @return TextButtonStyle
     */
    TextButton.TextButtonStyle getTextButtonStyle(ComponentsSizeEnum width, ComponentsSizeEnum height, Color[] scheme,
                                                  Color font, TextStyleEnum style, TextSizeEnum size) {
        var textures = getButtonStyleTextures(scheme, width, height);
        var buttonStyle = new TextButton.TextButtonStyle();

        buttonStyle.up = textures[0];
        buttonStyle.checked = textures[0];
        buttonStyle.over = textures[2];
        buttonStyle.checkedOver = textures[2];
        buttonStyle.down = textures[1];
        buttonStyle.font = getFont(style, size);
        buttonStyle.fontColor = font;

        return buttonStyle;
    }

    /**
     * creates the textures for the buttons
     *
     * @param scheme colorscheme
     * @param width  width
     * @param height height
     * @return textures for buttons
     */
    private TextureRegionDrawable[] getButtonStyleTextures(Color[] scheme, ComponentsSizeEnum width, ComponentsSizeEnum height) {
        var normal = new TextureRegionDrawable(new Texture(twoBorderClassicStyle(scheme[0], scheme[1], scheme[0], width, height)));
        var over = new TextureRegionDrawable(new Texture(twoBorderClassicStyle(scheme[2], scheme[1], scheme[2], width, height)));
        var clicked = new TextureRegionDrawable(new Texture(twoBorderClassicStyle(scheme[3], scheme[1], scheme[3], width, height)));

        return new TextureRegionDrawable[]{normal, clicked, over};
    }

    /**
     * creates the progressbarstyle
     *
     * @param widthSize  width
     * @param heightSize height
     * @param scheme     colorscheme
     * @return progressbarstyle
     */
    ProgressBar.ProgressBarStyle getProgressBarStyle(ComponentsSizeEnum widthSize,
                                                     ComponentsSizeEnum heightSize, Color[] scheme) {
        var displayProgress = pixmapToDrawable(getFilledPixmap(10, 10, scheme[1]));
        var normal = oneBorderClassicStyle(scheme[1], scheme[0], widthSize, heightSize, ComponentsSizeEnum.BORDER);
        return new ProgressBar.ProgressBarStyle(normal, displayProgress);

    }

    /**
     * creates the slider style
     *
     * @param scheme colorscheme
     * @param knob   knob color
     * @param width  width
     * @return sliderstyle
     */
    Slider.SliderStyle getSliderStyle(Color[] scheme, Color knob, ComponentsSizeEnum width) {
        var sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = oneBorderClassicStyle(scheme[1], scheme[0], width,
                ComponentsSizeEnum.COMPONENT_HEIGHT_SMALL, ComponentsSizeEnum.BORDER_SMALL);
        sliderStyle.knob = getColoredKnob(knob);
        return sliderStyle;
    }

    /**
     * get a colored knob (32 pixel big) back
     *
     * @param color knob color
     * @return colored knob as Drawable
     */
    private TextureRegionDrawable getColoredKnob(Color color) {
        var pixmap = new Pixmap(Gdx.files.internal("icons/white_32.png"));
        pixmap.setColor(color);
        for (var y = 0; y < pixmap.getHeight(); y++) {
            for (var x = 0; x < pixmap.getWidth(); x++) {
                if (pixmap.getPixel(x, y) != 0) pixmap.drawPixel(x, y);
            }
        }

        return new TextureRegionDrawable(new Texture(pixmap));
    }

    /**
     * creates a label style
     * needs TextStyleEnum and TextSizeEnum and a color
     *
     * @param style font style
     * @param size  font size
     * @param color font color
     * @return LabelStyle
     */
    Label.LabelStyle getLabelStyle(TextStyleEnum style, TextSizeEnum size, Color color) {
        return new Label.LabelStyle(getFont(style, size), color);
    }

    /**
     * creates a TextField
     *
     * @param border     color of the border
     * @param inner      color of the inner
     * @param widthSize  width
     * @param heightSize height
     * @param style      style
     * @param size       size
     * @return textfieldstyle
     */
    TextField.TextFieldStyle getTextFieldStyle(Color border, Color inner,
                                               ComponentsSizeEnum widthSize, ComponentsSizeEnum heightSize,
                                               TextStyleEnum style, TextSizeEnum size) {
        var selectColor = getFilledPixmap(1, 1, UISettings.getStylingDefaults().getOverColor());
        var testcursor = pixmapToDrawable(getFilledPixmap(3, 1, UISettings.getStylingDefaults().getClickedColor()));

        var textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = oneBorderClassicStyle(border, inner, widthSize, heightSize, ComponentsSizeEnum.BORDER);
        textFieldStyle.cursor = testcursor;
        textFieldStyle.font = getFont(style, size);
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.selection = pixmapToDrawable(selectColor);

        return textFieldStyle;
    }

    /**
     * same as a normal textfield but without the border
     */
    TextField.TextFieldStyle getBorderlessTextFieldStyle(Color inner,
                                                         ComponentsSizeEnum widthSize, ComponentsSizeEnum heightSize,
                                                         TextStyleEnum style, TextSizeEnum size) {
        var selectColor = getFilledPixmap(1, 1, UISettings.getStylingDefaults().getOverColor());
        var testcursor = pixmapToDrawable(getFilledPixmap(3, 1, UISettings.getStylingDefaults().getClickedColor()));

        var textFieldStyle = new TextField.TextFieldStyle();
        var pixmap = new Pixmap(widthSize.getValue(), heightSize.getValue(), Pixmap.Format.RGB888);
        pixmap.setColor(inner);
        pixmap.fill();

        textFieldStyle.background = new TextureRegionDrawable(new Texture(pixmap));
        textFieldStyle.cursor = testcursor;
        textFieldStyle.font = getFont(style, size);
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.selection = pixmapToDrawable(selectColor);

        return textFieldStyle;
    }

    /**
     * creates a filled pixmap
     *
     * @param width  width
     * @param height height
     * @param color  color
     * @return filled pixmap
     */
    private Pixmap getFilledPixmap(int width, int height, Color color) {
        var pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        pixmap.setColor(color);
        pixmap.fill();
        return pixmap;
    }

    /**
     * converts a pixmap to a Drawable
     *
     * @param pixmap Pixmap
     * @return Drawable
     */
    Drawable pixmapToDrawable(Pixmap pixmap) {
        return new TextureRegionDrawable(new Texture(pixmap));
    }

    /**
     * gives back a pixmap with a border, highlight, inner in different colors and a highlighted second border
     *
     * @param border     color of inner and the outer border
     * @param highlight  color of highlight
     * @param inner      color of inner
     * @param widthSize  width of the pixmap
     * @param heightSize height of the pixmap
     * @return pixmap
     */
    private Pixmap twoBorderClassicStyle(Color border, Color highlight, Color inner,
                                         ComponentsSizeEnum widthSize, ComponentsSizeEnum heightSize) {
        int width = getScaled(widthSize);
        int borderSize = getScaled(ComponentsSizeEnum.BORDER);
        int height = getScaled(heightSize);

        var pixmap = getFilledPixmap(width, height, border);
        pixmap.setColor(highlight);
        pixmap.fillRectangle(borderSize, borderSize, width - 2 * borderSize, height - 2 * borderSize);
        pixmap.setColor(inner);
        borderSize += getScaled(ComponentsSizeEnum.BORDER_SMALL);
        pixmap.fillRectangle(borderSize, borderSize, width - 2 * borderSize, height - 2 * borderSize);
        return pixmap;
    }

    private Drawable oneBorderClassicStyle(Color border, Color inner, ComponentsSizeEnum width,
                                           ComponentsSizeEnum height, ComponentsSizeEnum sizeBorder) {
        return oneBorderClassicStyle(border, inner, getScaled(width), getScaled(height), getScaled(sizeBorder));
    }

    /**
     * gives a pixmap with a border and a filled inner
     *
     * @param border     color of the inner
     * @param inner      color of the inner
     * @param width      width of the pixmap
     * @param height     height of the pixmap
     * @param sizeBorder width of the border
     */
    private Drawable oneBorderClassicStyle(Color border, Color inner, int width, int height, int sizeBorder) {
        var pixmap = getFilledPixmap(width, height, border);
        pixmap.setColor(inner);
        pixmap.fillRectangle(sizeBorder, sizeBorder, width - 2 * sizeBorder, height - 2 * sizeBorder);
        return pixmapToDrawable(pixmap);
    }

    private int getScaled(ComponentsSizeEnum size) {
        return (int) (size.getValue() * UIConstants.SCALING_FACTOR);
    }

    private int getScaled(TextSizeEnum size) {
        return (int) (size.getFontSize() * UIConstants.SCALING_FACTOR);
    }

    /**
     * creates the WindowStyle
     *
     * @return WindowStyle
     */
    Window.WindowStyle getWindowStyle() {
        var normal = oneBorderClassicStyle(ColorEnum.THEME_COLOR.getColor(),
                ColorEnum.BRIGHT_THEME_COLOR.getColor(), UIConstants.WINDOW_CENTER_X,
                UIConstants.WINDOW_CENTER_Y, UIConstants.BORDER_WIDTH);

        return new Window.WindowStyle(font, Color.WHITE, normal);
    }

    /**
     * creates the tooltipstyle
     *
     * @return tooltipstyle
     */
    public TextTooltip getToolTipStyle(String text) {
        int width = getScaled(ComponentsSizeEnum.COMPONENT_WIDTH_BIG);

        // tooltip manager
        var manager = new TooltipManager();
        manager.maxWidth = width - 4f * getScaled(ComponentsSizeEnum.BORDER);

        var labelStyle = getLabelStyle(UISettings.getStylingDefaults().getFontStyle(),
                UISettings.getStylingDefaults().getTextSize(), UISettings.getStylingDefaults().getSecondaryFontColor());

        // to get the right height
        var testLabel = new Label(text, labelStyle);
        testLabel.setWrap(true);
        testLabel.setWidth(width);
        testLabel.pack();

        var style = new TextTooltip.TextTooltipStyle(labelStyle, oneBorderClassicStyle(ColorEnum.THEME_COLOR.getColor(),
                ColorEnum.BRIGHT_THEME_COLOR.getColor(), width,
                (int) testLabel.getHeight(), UIConstants.BORDER_WIDTH));

        return new TextTooltip(text, manager, style);
    }

    /**
     * creates the scrollpanestyle
     *
     * @return scrollpanestyle
     */
    public ScrollPane.ScrollPaneStyle getScrollPaneStyle() {
        var style = new ScrollPane.ScrollPaneStyle();
        style.vScrollKnob = getColoredKnob(UISettings.getStylingDefaults().getLogoColor());
        var pixmap = new Pixmap(32, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(ColorEnum.BRIGHT_THEME_COLOR.getColor());
        pixmap.drawLine(7, 0, 24, 0);

        style.vScroll = new TextureRegionDrawable(new Texture(pixmap));
        return style;
    }
}