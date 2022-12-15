package marvelous_mashup.team29.ui_util.map;

import marvelous_mashup.team29.util.data.FieldState;

public interface IHaveATileCallback {
    void tileClicked(int xPosition, int yPosition, FieldState fieldState);
    void tileDoubleClicked(int xPosition, int yPosition, FieldState fieldState);
}
