package cn.xuqplus.adminlte.tesseract;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BTest {
    @Test
    public void test() throws IOException {
        String filename = "/home/qqx/counect/work2/ocrtool/ocrtool/docs/lsd/048000101/2017-12-01/000007977t0001_2017-12-01T09-54-37.bmp";
        File file = new File(filename);
        BufferedImage bi = ImageIO.read(file);
        //获取当前图片的高,宽,ARGB
        int h = bi.getHeight();
        int w = bi.getWidth();
        int arr[][] = new int[w][h];
        //获取图片每一像素点的灰度值
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                //getRGB()返回默认的RGB颜色模型(十进制)
                arr[i][j] = getImageRgb(bi.getRGB(i, j));//该点的灰度值
            }

        }
        //判断该行是否是横线
        for (int i = 0; i < h; i++) {
            int line = 0;
            for (int j = 0; j < w; j++) {
                line += arr[j][i];
            }
            if (line / w < 10) {
                System.out.println(String.format("%s行注定是横线 - %s", i, line / w));
            } else if (line / w < 20) {
                System.out.println(String.format("%s行一点可能是横线 - %s", i, line / w));
            } else if (line / w < 40) {
                System.out.println(String.format("%s行可能横线 - %s", i, line / w));
            }
        }
    }

    private static int getImageRgb(int i) {
        String argb = Integer.toHexString(i);//将十进制的颜色值转为十六进制
        //argb分别代表透明,红,绿,蓝 分别占16进制2位
        int r = Integer.parseInt(argb.substring(2, 4), 16);//后面参数为使用进制
        int g = Integer.parseInt(argb.substring(4, 6), 16);
        int b = Integer.parseInt(argb.substring(6, 8), 16);
        int result = ((r + g + b) / 3);
        return result;
    }

    public static int getGray(int arr[][], int x, int y, int w, int h) {
        int r = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                int i0 = i, j0 = j;
                if (i0 < 0) i0 = 0;
                if (i0 > w - 1) i0 = w - 1;
                if (j0 < 0) j0 = 0;
                if (j0 > h - 1) j0 = h - 1;
                r += arr[i0][j0];
            }
        }
        return r / 9;
    }
}
