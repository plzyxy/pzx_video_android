package com.txf.net_okhttp3library.body;


import com.txf.net_okhttp3library.listener.DownloadListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * @author txf
 * @create 2019/3/15 0015
 * @
 */
public class DownloadBody extends ResponseBody {

    private Response originalResponse;
    private DownloadListener downloadListener;
    private long oldPoint = 0;

    public DownloadBody(Response originalResponse, long startsPoint, DownloadListener downloadListener) {
        this.originalResponse = originalResponse;
        this.downloadListener = downloadListener;
        this.oldPoint = startsPoint;
    }

    @Override
    public MediaType contentType() {
        return originalResponse.body().contentType();
    }

    @Override
    public long contentLength() {
        return originalResponse.body().contentLength();
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(originalResponse.body().source()) {
            private long bytesReaded = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                if (downloadListener != null) {
                    downloadListener.loading((int) ((bytesReaded + oldPoint) / (1024)));
                }
                return bytesRead;
            }
        });
    }
}

