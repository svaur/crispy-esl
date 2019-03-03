package ru.mvp.rsreu.templates;

import java.awt.image.BufferedImage;

public interface SaleTemplate {
    BufferedImage drawEsl(EslInfoTemplate eslInfoTemplate, int width, int height);
}
