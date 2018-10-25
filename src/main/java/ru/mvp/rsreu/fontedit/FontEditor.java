package ru.mvp.rsreu.fontedit;

import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class FontEditor {
    public void drawString(Font font, String labelString, Graphics2D g2d, int width, int height) {
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics(font);
        int stringWidth = metrics.stringWidth(labelString);
        int startCoordinates = (width - stringWidth) / 2;
        g2d.drawString(labelString, startCoordinates, height);
    }
}
