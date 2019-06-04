package cz.destil.moodsync.light;

import android.graphics.Bitmap;

import cz.destil.moodsync.R;
import cz.destil.moodsync.core.App;
import cz.destil.moodsync.core.Config;
import cz.destil.moodsync.util.SleepTask;
import de.androidpit.colorthief.ColorThiefAsync;
/**
 * Periodically extracts color from a bitmap.
 *
 * @author David Vávra (david@vavra.me)
 */
public class ColorExtractor {


    private static ColorExtractor sInstance;
    private boolean mRunning = true;

    public static ColorExtractor get() {
        if (sInstance == null) {
            sInstance = new ColorExtractor();
        }
        return sInstance;
    }

    public void start(final MirroringHelper mirroring, final Listener listener) {
        mRunning = true;
        new SleepTask(Config.INITIAL_DELAY, new SleepTask.Listener() {
            @Override
            public void awoken() {
                extractBitmap(mirroring, listener);
            }
        }).start();
    }

    private void extractBitmap(final MirroringHelper mirroring, final Listener listener) {
        if (mRunning) {
            mirroring.getLatestBitmap(new MirroringHelper.Listener() {
                @Override
                public void onBitmapAvailable(final Bitmap bitmap) {
                    final Bitmap bitmapCopy = bitmap.copy(bitmap.getConfig(),true);
                    ColorThiefAsync
                            .from(bitmapCopy)
                            .setRegion(Config.COLOR_REGION_LEFT,Config.COLOR_REGION_TOP,Config.COLOR_REGION_RIGHT,Config.COLOR_REGION_BOTTOM)
                            .getDominantColor(new ColorThiefAsync.ColorThiefAsyncListener(){
                                @Override
                                public void onColorExtracted(Integer color, Integer overallBrightness) {
                                    bitmapCopy.recycle();
                                    listener.onColorExtracted(color, overallBrightness);
                                    new SleepTask(Config.FREQUENCE_OF_SCREENSHOTS, new SleepTask.Listener() {
                                        @Override
                                        public void awoken() {
                                            extractBitmap(mirroring, listener);
                                        }
                                    }).start();
                                }
                            });
                }
            });
        }
    }

    public void stop() {
        mRunning = false;
    }

    public interface Listener {
        public void onColorExtracted(int color, int overallBrightness);
    }
}
