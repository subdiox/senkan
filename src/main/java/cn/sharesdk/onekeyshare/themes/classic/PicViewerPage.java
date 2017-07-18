package cn.sharesdk.onekeyshare.themes.classic;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView.ScaleType;
import cn.sharesdk.onekeyshare.OnekeySharePage;
import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import com.mob.tools.gui.ScaledImageView;

public class PicViewerPage extends OnekeySharePage implements OnGlobalLayoutListener {
    private Bitmap pic;
    private ScaledImageView sivViewer;

    public PicViewerPage(OnekeyShareThemeImpl impl) {
        super(impl);
    }

    public void setImageBitmap(Bitmap pic) {
        this.pic = pic;
    }

    public void onCreate() {
        this.activity.getWindow().setBackgroundDrawable(new ColorDrawable(1275068416));
        this.sivViewer = new ScaledImageView(this.activity);
        this.sivViewer.setScaleType(ScaleType.MATRIX);
        this.activity.setContentView(this.sivViewer);
        if (this.pic != null) {
            this.sivViewer.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }
    }

    public void onGlobalLayout() {
        this.sivViewer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        this.sivViewer.post(new Runnable() {
            public void run() {
                PicViewerPage.this.sivViewer.setBitmap(PicViewerPage.this.pic);
            }
        });
    }
}
