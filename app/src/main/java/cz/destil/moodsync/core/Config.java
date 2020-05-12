package cz.destil.moodsync.core;

import olsenn1.Values.Levels;

/**
 * Global config which controls app behavior.
 *
 * @author David Vávra (david@vavra.me)
 */
public class Config {
    public static final int INITIAL_DELAY = 100; // in ms
    public static final int FINAL_DELAY = 100; // in ms
    public static final int FREQUENCE_OF_SCREENSHOTS = 50; // in ms
    public static final int DURATION_OF_COLOR_CHANGE = 400; // in ms
    public static final int LIFX_BRIGHTNESS = Levels.MAX; // 0-65535
    public static final int POSTERIZE_LEVELS = 5;
    public static final float SATURATION = 0.6f; //0-1
    public static final int VIRTUAL_DISPLAY_WIDTH = 320; // in px - default of 150x84 is approximately the pixel count google's palette routine uses
    public static final int VIRTUAL_DISPLAY_HEIGHT = 180; // in px
    public static final int MULTIZONE_REGIONS = 16;
    public static final int MULTIZONE_DIRECTION = -1;
    public static final String BRIGHTNESS_STATIC = "static"; //use brightness defined in LIFX_BRIGHTNESS for lights
    public static final String BRIGHTNESS_COLOR = "color"; //use dominant color brightness for lights
    public static final String BRIGHTNESS_AVERAGE = "average"; //use image pseudo-average brightness for lights
    public static final String BRIGHTNESS_MODE = "average"; //current brightness mode to use
}
