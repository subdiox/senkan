package okio;

import android.support.v4.media.session.PlaybackStateCompat;

final class SegmentPool {
    static final SegmentPool INSTANCE = new SegmentPool();
    static final long MAX_SIZE = 65536;
    long byteCount;
    private Segment next;

    private SegmentPool() {
    }

    Segment take() {
        synchronized (this) {
            if (this.next != null) {
                Segment result = this.next;
                this.next = result.next;
                result.next = null;
                this.byteCount -= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
                return result;
            }
            return new Segment();
        }
    }

    void recycle(Segment segment) {
        if (segment.next == null && segment.prev == null) {
            synchronized (this) {
                if (this.byteCount + PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH > MAX_SIZE) {
                    return;
                }
                this.byteCount += PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
                segment.next = this.next;
                segment.limit = 0;
                segment.pos = 0;
                this.next = segment;
                return;
            }
        }
        throw new IllegalArgumentException();
    }
}
