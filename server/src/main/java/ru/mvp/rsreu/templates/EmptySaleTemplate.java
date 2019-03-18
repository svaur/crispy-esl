package ru.mvp.rsreu.templates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mvp.rsreu.templates.fontedit.FontEditor;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * информация о стандартном шаблоне
 */
@Component
public class EmptySaleTemplate implements SaleTemplate{
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
                width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        fontEditor.drawString(new Font("TimesRoman", Font.BOLD, 28),
                "ruESL",
                g2d,
                width,
                100,
                false);
        g2d.dispose();
        return image;
    }
}
