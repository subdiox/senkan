package com.kayac.lobi.libnakamap.rec.recorder;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import com.kayac.lobi.libnakamap.rec.a.b;

@TargetApi(11)
public class CameraInput implements OnFrameAvailableListener {
    private Camera mCamera;
    private boolean mIsEnabled = false;
    private boolean mIsUpdated = false;
    private SurfaceTexture mSurfaceTexture;

    private enum a {
        ROTATION_0,
        ROTATION_90,
        ROTATION_180,
        ROTATION_270
    }

    public CameraInput(Activity activity, OffScreenManager offScreenManager) {
        boolean z = false;
        try {
            this.mCamera = Camera.open(1);
            Parameters parameters = this.mCamera.getParameters();
            Size optimalCameraSize = getOptimalCameraSize(parameters);
            if (optimalCameraSize == null) {
                this.mCamera.release();
                return;
            }
            parameters.setPreviewSize(optimalCameraSize.width, optimalCameraSize.height);
            this.mCamera.setParameters(parameters);
            Point point = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(point);
            boolean z2 = point.y > point.x;
            int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            if (z2) {
                if (rotation == 0 || rotation == 2) {
                    z = true;
                }
            } else if (rotation == 1 || rotation == 3) {
                z = true;
            }
            a aVar = a.ROTATION_0;
            switch (rotation) {
                case 0:
                    if (!z) {
                        aVar = a.ROTATION_0;
                        break;
                    } else {
                        aVar = a.ROTATION_270;
                        break;
                    }
                case 1:
                    if (!z) {
                        aVar = a.ROTATION_90;
                        break;
                    } else {
                        aVar = a.ROTATION_0;
                        break;
                    }
                case 2:
                    if (!z) {
                        aVar = a.ROTATION_180;
                        break;
                    } else {
                        aVar = a.ROTATION_90;
                        break;
                    }
                case 3:
                    if (!z) {
                        aVar = a.ROTATION_270;
                        break;
                    } else {
                        aVar = a.ROTATION_180;
                        break;
                    }
            }
            this.mSurfaceTexture = new SurfaceTexture(offScreenManager.setupCamera(optimalCameraSize.width, optimalCameraSize.height, aVar.ordinal()));
            this.mSurfaceTexture.setOnFrameAvailableListener(this);
            try {
                this.mCamera.setPreviewTexture(this.mSurfaceTexture);
                this.mIsEnabled = true;
            } catch (Throwable e) {
                b.a(e);
                this.mCamera.release();
            }
        } catch (Throwable e2) {
            b.a(e2);
        }
    }

    private static Size getOptimalCameraSize(Parameters parameters) {
        Size size = null;
        int i = 0;
        for (Size size2 : parameters.getSupportedPreviewSizes()) {
            int i2;
            Size size3;
            int i3 = size2.width * size2.height;
            Object obj = size2.width <= 200 ? 1 : null;
            Object obj2 = size2.height <= 200 ? 1 : null;
            if (obj == null || obj2 == null || i >= i3) {
                i2 = i;
                size3 = size;
            } else {
                int i4 = i3;
                size3 = size2;
                i2 = i4;
            }
            i = i2;
            size = size3;
        }
        return size;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.mIsUpdated = true;
    }

    public void pause() {
        this.mCamera.stopPreview();
    }

    public void start() {
        this.mCamera.startPreview();
    }

    public void stop() {
        this.mCamera.stopPreview();
        this.mCamera.release();
        this.mCamera = null;
    }

    public boolean update() {
        if (!this.mIsUpdated) {
            return false;
        }
        this.mIsUpdated = false;
        this.mSurfaceTexture.updateTexImage();
        return true;
    }
}
