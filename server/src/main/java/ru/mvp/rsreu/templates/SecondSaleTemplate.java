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
public class SecondSaleTemplate implements SaleTemplate{
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
        fontEditor.drawString(new Font("TimesRoman", Font.BOLD, 14),
                eslInfoTemplate.getGoodName(),
                g2d,
                width,
                23,
                false);
        fontEditor.drawString(new Font("TimesRoman", Font.PLAIN, 14),
                eslInfoTemplate.getGoodSecondName(),
                g2d,
                width,
                38,
                false);
        fontEditor.drawString(new Font("TimesRoman", Font.BOLD, 28),
                eslInfoTemplate.getNewCost(),
                g2d,
                width,
                100,
                false);
        fontEditor.drawString(new Font("TimesRoman", Font.BOLD, 12),
                eslInfoTemplate.getCurrency(),
                g2d,
                width,
                135,
                false);
        fontEditor.drawString(new Font("TimesRoman", Font.PLAIN, 12),
                eslInfoTemplate.getVendorCode(),
                g2d,
                width,
                150,
                false);
        g2d.setColor(Color.WHITE);
        fontEditor.drawString(new Font("TimesRoman", Font.BOLD, 10),
                "АКЦИЯ",
                g2d,
                width,
                10,
                false);
        g2d.dispose();
        return image;
    }
}
