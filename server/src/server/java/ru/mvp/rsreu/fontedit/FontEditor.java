package ru.mvp.rsreu.fontedit;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Line2D;

@Component
public class FontEditor {
    public void drawString(Font font, String drawableString, Graphics2D g2d, int width, int yCoordinate, boolean crossed) {
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics(font);
        int stringWidth = metrics.stringWidth(drawableString);
        int xCoordinate = (width - stringWidth) / 2;
        g2d.drawString(drawableString, xCoordinate, yCoordinate);
        if (crossed)
            // зачеркнуто
            //todo вынести хардкод
            g2d.draw(new Line2D.Double(xCoordinate-10,
                    //todo тут захаржкожена половина шрифта (надо подумать как его сюда передать без дублирования)
                    yCoordinate-10,
                    xCoordinate+stringWidth+10,
                    yCoordinate-10));
    }
}
