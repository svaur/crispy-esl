package ru.mvp.rsreu.templates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mvp.rsreu.fontedit.FontEditor;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * информация о стандартном шаблоне
 */
@Component
public class BaseTemplate {
    @Autowired
    FontEditor fontEditor;
    /**
     * Рисуем ценник
     * @param eslInfoTemplate полная инфа о ценнике из базы
     * @param width ширина ценника
     * @param height высота ценника
     */
    public BufferedImage drawEsl(EslInfoTemplate eslInfoTemplate, int width, int height){
        BufferedImage image = new BufferedImage(
                width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);
        // create a string with yellow
        g2d.setColor(Color.BLACK);

        fontEditor.drawString(new Font("TimesRoman", Font.BOLD, 12),
                eslInfoTemplate.getGoodName(),
                g2d,
                width,
                10);
        fontEditor.drawString(new Font("TimesRoman", Font.PLAIN, 12),
                eslInfoTemplate.getGoodSecondName(),
                g2d,
                width,
                20);
        fontEditor.drawString(new Font("TimesRoman", Font.PLAIN, 22),
                eslInfoTemplate.getOldCost(),
                g2d,
                width,
                60);
        fontEditor.drawString(new Font("TimesRoman", Font.BOLD, 30),
                eslInfoTemplate.getNewCost(),
                g2d,
                width,
                120);
        fontEditor.drawString(new Font("TimesRoman", Font.BOLD, 10),
                eslInfoTemplate.getCurrency(),
                g2d,
                width,
                160);
        fontEditor.drawString(new Font("TimesRoman", Font.PLAIN, 10),
                eslInfoTemplate.getVendorCode(),
                g2d,
                width,
                190);
        // Disposes of this graphics context and releases any system resources that it is using.
        g2d.dispose();
        return image;
    }
}
