package com.txf.other_toolslibrary.utils;

import android.graphics.Color;

/**
 * @author txf
 * @create 2019/4/3 0003
 * @
 */
public class ColorUtils {
    /**
     * 颜色渐变
     *
     * @param value 0 ~ 1
     */
    public static int gradual(String startColor, String endColr, float value) {
        int[] startColorRgb = hex2Rgb(startColor);
        int[] endColorRgb = hex2Rgb(endColr);

        int redA = (int) (startColorRgb[0] + (endColorRgb[0] - startColorRgb[0]) * value);
        int greenA = (int) (startColorRgb[1] + (endColorRgb[1] - startColorRgb[1]) * value);
        int blueA = (int) (startColorRgb[2] + (endColorRgb[2] - startColorRgb[2]) * value);

        return Color.rgb(redA, greenA, blueA);
    }

    /**
     * Color的16进制颜色值 转 rgb数组
     * colorHex - Color的16进制颜色值——#3FE2C5
     * return Color的rgb数组 —— [63,226,197]
     */
    public static int[] hex2Rgb(String colorHex) {
        int colorInt = hex2Int(colorHex);
        return int2Rgb(colorInt);
    }

    /**
     * rgb数组转Color的16进制颜色值
     * rgb - rgb数组——[63,226,197]
     * return Color的16进制颜色值——#3FE2C5
     */
    public static String rgb2Hex(int[] rgb) {
        String hexCode = "#";
        for (int i = 0; i < rgb.length; i++) {
            int rgbItem = rgb[i];
            if (rgbItem < 0) {
                rgbItem = 0;
            } else if (rgbItem > 255) {
                rgbItem = 255;
            }
            String[] code = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
            int lCode = rgbItem / 16;//先获取商，例如，255 / 16 == 15
            int rCode = rgbItem % 16;//再获取余数，例如，255 % 16 == 15
            hexCode += code[lCode] + code[rCode];//FF
        }
        return hexCode;
    }

    /**
     * Color的16进制颜色值 转 Color的Int整型
     * colorHex - Color的16进制颜色值——#3FE2C5
     * return colorInt - -12590395
     */
    public static int hex2Int(String colorHex) {
        int colorInt = 0;
        colorInt = Color.parseColor(colorHex);
        return colorInt;
    }

    /**
     * Color的Int整型转Color的rgb数组
     * colorInt - -12590395
     * return Color的rgb数组 —— [63,226,197]
     */
    public static int[] int2Rgb(int colorInt) {
        int[] rgb = new int[]{0, 0, 0};
        int red = Color.red(colorInt);
        int green = Color.green(colorInt);
        int blue = Color.blue(colorInt);
        rgb[0] = red;
        rgb[1] = green;
        rgb[2] = blue;

        return rgb;
    }

}
