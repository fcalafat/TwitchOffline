package sheol.twichoffline.grabber.pojo;

import java.text.NumberFormat;

/**
 * Created by PC on 22/10/2016.
 */
public class ProgressIndicator {

    private int downloadedFile;
    private int filesToDownload;

    public ProgressIndicator(int filesToDownload) {
        this.filesToDownload = filesToDownload;
    }

    public void increment() {
        this.downloadedFile++;
    }

    public String getProgress() {
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(0);
        return "Progress: " + numberFormat.format((double) this.downloadedFile / (double) this.filesToDownload);
    }
}
