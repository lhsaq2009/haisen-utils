package com.haisen.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * <p>〈功能概述〉.
 * @author haisen /20207/27 
 */
public class DownloadUtil {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        download("http://litbimg.rightinthebox.com/images/150x150/202004/djzxgn1587898612334.jpg",
                "1_li13251690212.jpg", "src/main/java/com/haisen/common/utils");
    }

    public static void download(String urlString, String filename, String savePath) throws Exception {

        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(5 * 1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;

        // 输出的文件流
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }

        OutputStream os = new FileOutputStream(sf.getPath() + "/" + filename);

        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }

        // 完毕，关闭所有链接
        os.close();
        is.close();
    }
}
