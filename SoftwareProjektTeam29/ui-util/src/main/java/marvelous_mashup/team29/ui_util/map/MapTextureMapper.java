package marvelous_mashup.team29.ui_util.map;

import com.badlogic.gdx.graphics.Texture;
import marvelous_mashup.team29.ui_util.UIConstants;
import marvelous_mashup.team29.util.data.FieldState;

public class MapTextureMapper {

    private MapTextureMapper() {
    }

    /**
     * F = Field
     * x x x    0 1 2
     * x F x => 3 F 4 => 0 1 2 3 4 5 6 7 => Values between 0 and 255
     * x x x    5 6 7
     *
     * @param status The status of the tile
     * @param value  The value as described above between 0 and 255
     * @return The right Texture
     */
    public static Texture getTexture(FieldState status, int value) {
        if (status == FieldState.GRASS) return UIConstants.ASSET_FINDER.get("textures/map/grass/standard.png");
        if (status == FieldState.PORTAL) return UIConstants.ASSET_FINDER.get("textures/map/portal/standard.png");

        return switch (value) {
            //Standard
            case 0, 1, 4, 5, 32, 33, 36, 37, 128, 129, 132, 133, 160, 161, 164, 165 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Single/Mountain.png");

            //End
            case 64, 65, 68, 69, 96, 97, 100, 101, 192, 193, 196, 197, 224, 225, 228, 229 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/End/Top.png");
            case 16, 17, 20, 21, 48, 49, 52, 53, 144, 145, 148, 149, 176, 177, 180, 181 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/End/Left.png");
            case 8, 9, 12, 13, 40, 41, 44, 45, 136, 137, 140, 141, 168, 169, 172, 173 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/End/Right.png");
            case 2, 3, 6, 7, 34, 35, 38, 39, 130, 131, 134, 135, 162, 163, 166, 167 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/End/Bottom.png");

            //Edge
            case 80, 81, 84, 85, 112, 113, 116, 117 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Edge/LeftTop.png");
            case 18, 19, 50, 51, 146, 147, 178, 179 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Edge/LeftBottom.png");
            case 72, 73, 76, 77, 200, 201, 204, 205 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Edge/RightTop.png");
            case 10, 14, 42, 46, 138, 142, 170, 174 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Edge/RightBottom.png");

            case 208, 209, 212, 213, 240, 241, 244, 245 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Edge/LeftTopFilled.png");
            case 22, 23, 54, 55, 150, 151, 182, 183 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Edge/LeftBottomFilled.png");
            case 104, 105, 108, 109, 232, 233, 236, 237 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Edge/RightTopFilled.png");
            case 11, 15, 43, 47, 139, 143, 171, 175 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Edge/RightBottomFilled.png");

            //Line
            case 24, 25, 28, 29, 56, 57, 60, 61, 152, 153, 156, 157, 184, 185, 188, 189 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Line/Horizontal.png");
            case 66, 67, 70, 71, 98, 99, 102, 103, 194, 195, 198, 199, 226, 227, 230, 231 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Line/Vertical.png");

            case 248, 249, 252, 253 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Line/HorizontalTop.png");
            case 31, 63, 159, 191 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Line/HorizontalBottom.png");

            case 214, 215, 246, 247 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Line/VerticalLeft.png");
            case 107, 111, 235, 239 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Line/VerticalRight.png");

            //TShape
            //LeftTopRight
            case 88, 89, 92, 93 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/LeftTopRight/Normal.png");
            case 216, 217, 220, 221 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/LeftTopRight/FirstFilled.png");
            case 120, 121, 124, 125 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/LeftTopRight/SecondFilled.png");
            case 95 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/LeftTopRight/BackFilled.png");
            //LeftBottomRight
            case 26, 58, 154, 186 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/LeftBottomRight/Normal.png");
            case 30, 62, 158, 190 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/LeftBottomRight/FirstFilled.png");
            case 27, 59, 155, 187 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/LeftBottomRight/SecondFilled.png");
            case 250 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/LeftBottomRight/BackFilled.png");
            case 254 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/LeftBottomRight/BackFirstFilled.png");
            case 251 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/LeftBottomRight/BackSecondFilled.png");
            //LeftTopBottom
            case 82, 83, 114, 115 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/LeftTopBottom/Normal.png");
            case 210, 211, 242, 243 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/LeftTopBottom/FirstFilled.png");
            case 86, 87, 119, 118 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/LeftTopBottom/SecondFilled.png");
            case 123 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/LeftTopBottom/BackFilled.png");
            case 127 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/LeftTopBottom/BackSecondFilled.png");
            //RightTopBottom
            case 74, 78, 202, 206 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/RightTopBottom/Normal.png");
            case 106, 110, 234, 238 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/RightTopBottom/FirstFilled.png");
            case 75, 79, 203, 207 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/RightTopBottom/SecondFilled.png");
            case 222 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/RightTopBottom/BackFilled.png");
            case 223 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/TShape/RightTopBottom/BackSecondFilled.png");

            //Center
            case 90 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Center/Standard/Normal.png");
            case 255 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Center/Standard/Completed.png");
            //OneEdge
            case 218 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Center/OneEdge/LeftTop.png");
            case 94 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Center/OneEdge/LeftBottom.png");
            case 122 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Center/OneEdge/RightTop.png");
            case 91 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Center/OneEdge/RightBottom.png");
            //TwoEdge
            case 126 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Center/TwoEdge/LeftBottomRightTop.png");
            case 219 -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Center/TwoEdge/LeftTopRightBottom.png");
            default -> UIConstants.ASSET_FINDER.get("textures/map/mountain/Single/Mountain.png");
        };
    }
}