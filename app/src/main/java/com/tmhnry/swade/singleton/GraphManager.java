package com.tmhnry.swade.singleton;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class GraphManager {
    private static Bitmap graph;

    public static void updateGraph(String imgString) {
        graph = base64Decode(imgString);
    }

    private static Bitmap base64Decode(String imgString) {
        byte[] bytes = android.util.Base64.decode(imgString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static Bitmap getGraph() {
        return graph;
    }
}
