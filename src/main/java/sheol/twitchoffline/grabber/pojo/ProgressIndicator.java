package sheol.twitchoffline.grabber.pojo;

import org.apache.commons.lang3.StringUtils;

import java.text.NumberFormat;

/**
 * Created by PC on 22/10/2016.
 */
public class ProgressIndicator {

    private int downloadedFile;
    private int filesToDownload;
    private String lastProgress;
    private NumberFormat numberFormat;

    public ProgressIndicator(int filesToDownload) {
        this.filesToDownload = filesToDownload;

        this.numberFormat = NumberFormat.getPercentInstance();
        this.numberFormat.setMaximumFractionDigits(0);
    }

    public void increment() {
        this.downloadedFile++;
    }

    private double progress() {
        return ((double) this.downloadedFile / (double) this.filesToDownload);
    }

    public boolean loggable() {
        String newProgress = this.numberFormat.format(progress());
        if (!StringUtils.equals(this.lastProgress, newProgress)) {
            this.lastProgress = newProgress;
            return true;
        } else {
            return false;
        }
    }

    public String getProgress() {
        return "Progress: " + lastProgress + " (" + downloadedFile + "/" + filesToDownload + ")";
    }
}
