package com.dingsheng.decent.util.common;//package com.dingsheng.yitui.common.util.commons;
//
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//import net.coobird.thumbnailator.Thumbnails;
//import net.coobird.thumbnailator.Thumbnails.Builder;
//import net.coobird.thumbnailator.filters.Watermark;
//import net.coobird.thumbnailator.geometry.Position;
//import net.coobird.thumbnailator.geometry.Positions;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpException;
//import org.apache.commons.httpclient.methods.GetMethod;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 图像处理工具类
// *
// * 可进行图片
// * 	1.尺寸修改，（resize 压缩修改,resizeTo 等比修改）
// * 	2.裁剪区域
// * 	3.旋转图片
// * 	4.水印处理（图片合成处理）
// * 	5.读取文件到renderedImage
// * 	6.图片格式化输出(转格式)
// *
// * @author Lin.D.G
// *
// */
//public class ImageUtils {
//
//    private static Logger logger = LoggerFactory.getLogger(ImageUtils.class);
//
//    private static HttpClient httpClient = new HttpClient();
//
//    /**
//     * 等比缩放图片尺寸
//     *
//     * @param fn 原图片路径
//     * @param width 等比缩放限宽
//     * @param height 等比缩放限高
//     * @return 处理结果图片信息对象BufferedImage
//     *
//     * @throws IOException
//     */
//    public static BufferedImage resizeTo(String fn, int width, int height)
//            throws IOException {
//        return resizeTo(Thumbnails.of(fn).scale(1, 1).asBufferedImage(), width, height);
//    }
//    /**
//     * 等比缩放图片尺寸
//     *
//     * @param fn 原图片路径
//     * @param width 等比缩放限宽
//     * @param height 等比缩放限高
//     * @param to 处理结果图片保存路径
//     *
//     * @throws IOException
//     */
//    public static void resizeTo(String fn, int width, int height, String to)
//            throws IOException {
//        FileUtil.createPath(new File(to));
////		Thumbnails.of(fn).size(width, height).toFile(to);
//        format(resizeTo(fn, width, height), to);
//    }
//    /**
//     * 等比缩放图片尺寸
//     *
//     * @param bfi 原图片信息对象BufferedImage
//     * @param width 等比缩放限宽
//     * @param height 等比缩放限高
//     * @return 处理结果图片信息对象BufferedImage
//     *
//     * @throws IOException
//     */
//    public static BufferedImage resizeTo(BufferedImage bfi, int width, int height)
//            throws IOException {
//        return Thumbnails.of(bfi).size(width, height).asBufferedImage();
//    }
//    /**
//     * 等比缩放图片尺寸
//     *
//     * @param bfi 原图片信息对象BufferedImage
//     * @param width 等比缩放限宽
//     * @param height 等比缩放限高
//     * @param to 处理结果图片保存路径
//     *
//     * @throws IOException
//     */
//    public static void resizeTo(BufferedImage bfi, int width, int height, String to)
//            throws IOException {
//        FileUtil.createPath(new File(to));
//        Thumbnails.of(bfi).size(width, height).toFile(to);
//    }
//
//
//    /**
//     * 强制缩放图片尺寸
//     * （非等比，新尺寸比不同时，会导致图片变形）
//     *
//     * @param fn 原图片路径
//     * @param width 缩放后宽度
//     * @param height 缩放后高度
//     *
//     * @throws IOException
//     */
//    public static BufferedImage resize(String fn, int width, int height)
//            throws IOException {
//        return Thumbnails.of(fn).size(width, height).keepAspectRatio(false).asBufferedImage();
//    }
//    /**
//     * 强制缩放图片尺寸
//     * （非等比，新尺寸比不同时，会导致图片变形）
//     *
//     * @param fn 原图片路径
//     * @param width 缩放后宽度
//     * @param height 缩放后高度
//     * @param to 处理结果图片保存路径
//     *
//     * @throws IOException
//     */
//    public static void resize(String fn, int width, int height, String to)
//            throws IOException {
//        FileUtil.createPath(new File(to));
////		Thumbnails.of(fn).size(width, height).keepAspectRatio(false).toFile(to);
//        format(resize(Thumbnails.of(fn).scale(1, 1).asBufferedImage(), width, height), to);
//    }
//    /**
//     * 强制缩放图片尺寸
//     * （非等比，新尺寸比不同时，会导致图片变形）
//     *
//     * @param bi 原图片信息对象BufferedImage
//     * @param width 缩放后宽度
//     * @param height 缩放后高度
//     *
//     * @throws IOException
//     */
//    public static BufferedImage resize(BufferedImage bi, int width, int height)
//            throws IOException {
//        return Thumbnails.of(bi).size(width, height).keepAspectRatio(false).asBufferedImage();
//    }
//    /**
//     * 强制缩放图片尺寸
//     * （非等比，新尺寸比不同时，会导致图片变形）
//     *
//     * @param bi 原图片信息对象BufferedImage
//     * @param width 缩放后宽度
//     * @param height 缩放后高度
//     * @param to 处理结果图片保存路径
//     *
//     * @throws IOException
//     */
//    public static void resize(BufferedImage bi, int width, int height, String to)
//            throws IOException {
//        FileUtil.createPath(new File(to));
//        Thumbnails.of(bi).size(width, height).keepAspectRatio(false).toFile(to);
//    }
//
//    /**
//     * 裁剪图片
//     *
//     * @param fn	原图片路径
//     * @param x1	左上角x坐标
//     * @param y1	左上角y坐标
//     * @param x2	右下角x坐标
//     * @param y2	右下角y坐标
//     * @param to 处理结果图片保存路径
//     *
//     * @throws IOException
//     */
//    public static void cutRange(String fn, int x1, int y1, int x2, int y2,
//                                String to) throws IOException {
//        FileUtil.createPath(new File(to));
//        Thumbnails.of(fn).sourceRegion(x1, y1, x2 - x1, y2 - y1)
//                .size(Math.abs(x2 - x1), Math.abs(y2 - y1))
//                .keepAspectRatio(false).toFile(to);
//    }
//
//    /**
//     * 裁剪图片
//     *
//     * @param fn 原图片路径
//     * @param width 区域宽度
//     * @param height 区域高度
//     * @param to 处理结果图片保存路径
//     *
//     * @throws IOException
//     */
//    public static void cutRange(String fn, int width, int height, String to)
//            throws IOException {
//        FileUtil.createPath(new File(to));
//        Thumbnails.of(fn).sourceRegion(Positions.TOP_LEFT, width, height)
//                .size(width, height).keepAspectRatio(false).toFile(to);
//    }
//
//    /**
//     * 顺时针 旋转图片
//     *
//     * @param fn 原图片路径
//     * @param rotate 角度
//     * @param to 处理结果图片保存路径
//     *
//     * @throws IOException
//     */
//    public static void rotate(String fn, double rotate, String to)
//            throws IOException {
//        FileUtil.createPath(new File(to));
//        Thumbnails.of(fn).scale(1).rotate(rotate).toFile(to);
//    }
//
//    /**
//     * 水印处理
//     *
//     * @param fn 原图片路径
//     * @param mark 水印图片路径
//     * @param to 处理结果图片保存路径
//     *
//     * @throws IOException
//     */
//    public static void mark(String fn, String mark, String to) throws IOException {
//        FileUtil.createPath(new File(to));
//        Thumbnails
//                .of(fn)
//                .scale(1)
//                .watermark(Positions.BOTTOM_RIGHT,
//                        ImageIO.read(new File(mark)), 0.5f)
//                .toFile(to);
//    }
//    /**
//     * 水印处理
//     *
//     * @param fn 原图片路径
//     * @param mark 水印图片路径
//     * @param x 水印左上角相对 原图片左上角的x坐标
//     * @param y 水印左上角相对 原图片左上角的y坐标
//     * @param to 处理结果图片保存路径
//     *
//     * @throws IOException
//     */
//    public static void mark(String fn, String mark, int x, int y, String to) throws IOException {
//        mark(fn, mark, x, y, to, 0.5f);
//    }
//    /**
//     * 水印处理
//     *
//     * @param fn 原图片路径
//     * @param mark 水印图片路径
//     * @param x 水印左上角相对 原图片左上角的x坐标
//     * @param y 水印左上角相对 原图片左上角的y坐标
//     * @param to 处理结果图片保存路径
//     * @param opacity 水印不透明度值,数值在0f-1f之间
//     *
//     * @throws IOException
//     */
//    public static void mark(String fn, String mark, int x, int y, String to, float opacity) throws IOException {
//        FileUtil.createPath(new File(to));
//        Thumbnails
//                .of(fn)
//                .scale(1)
//                .watermark(new PointPosition(x, y),
//                        ImageIO.read(new File(mark)), opacity)
//                .toFile(to);
//    }
//    /**
//     * 水印处理
//     *
//     * @param fn 原图片路径
//     * @param to 处理结果图片保存路径
//     * @param watermarks 水印图片对象,可以传多个
//     * @throws IOException
//     */
//    public static void mark(String fn, String to, PointedWaterMark... watermarks) throws IOException {
//        FileUtil.createPath(new File(to));
//        Builder<File> builder = Thumbnails.of(fn).scale(1);
//
//        for(int i=0;watermarks!=null && i<watermarks.length;i++){
//            builder.watermark(watermarks[i]);
//        }
//        builder.toFile(to);
//    }
//
//
//    /**
//     *
//     *
//     * @param fn 原图片路径
//     * @param tempFile 是否读取临时
//     * @param sub 子目录
//     *
//     * @return RenderedImage 类型
//     *
//     * @throws IOException
//     */
//    public static BufferedImage readImage(String fn, boolean tempFile,
//                                          String sub) throws IOException {
//        String fp = tempFile ? FileUtil.getTempFile(fn) : FileUtil.getRealFile(
//                sub, fn);
//        return ImageIO.read(new File(fp));
//    }
//    public static BufferedImage readImage(byte[] buf) throws IOException {
//        return ImageIO.read(new ByteArrayInputStream(buf));
//    }
//    /**
//     * 读取网络上的图片
//     *
//     * @param url 网络图片地址
//     * @return 图片信息对象BufferedImage
//     *
//     * @throws IOException
//     */
//    public static BufferedImage readImage(String url) throws IOException {
//        GetMethod method = new GetMethod(url);
//        try {
//            method.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0");
//            httpClient.executeMethod(method);
//            return ImageIO.read(method.getResponseBodyAsStream());
//        } catch (HttpException e) {
//            logger.warn(String.format("读取网络图片失败：%s", e.getMessage()));
//            throw new IOException(String.format("读取网络图片失败：%s", e.getMessage()));
//        } finally{
//            method.releaseConnection();
//        }
//    }
//    /**
//     * 读取网络上的图片
//     *
//     * @param url 网络图片地址
//     * @param width 缩放后限宽
//     * @param height 缩放后限高
//     * @param scaled 是否等比缩放
//     *
//     * @return 图片信息对象BufferedImage
//     *
//     * @throws IOException
//     */
//    public static BufferedImage readImage(String url, int width, int height, boolean scaled) throws IOException {
//        BufferedImage bi = readImage(url);
//        return scaled?resizeTo(bi, width, height):resize(bi, width, height);
//    }
//    /**
//     * 转换图片格式
//     *
//     * @param fn 原图片路径
//     * @param to 处理结果图片保存路径
//     * @throws IOException
//     */
//    public static void format(String fn,String to) throws IOException{
//        FileUtil.createPath(new File(to));
//        Thumbnails.of(fn).scale(1, 1).outputFormat(FileUtil.getExtName(to)).toFile(to);
//    }
//    /**
//     * 转换图片格式
//     *
//     * @param bf 图片源BufferedImage
//     * @param to 处理结果图片保存路径
//     * @throws IOException
//     */
//    public static void format(BufferedImage bf, String to) throws IOException{
//        FileUtil.createPath(new File(to));
//        Thumbnails.of(bf).scale(1, 1).outputFormat(FileUtil.getExtName(to)).toFile(to);
//    }
//
//    /**
//     * 生成二维码图片文件
//     *
//     * @param content 二维码信息内容
//     * @param icon 二维码logo文件路径
//     * @param size 二维码尺寸size*size
//     * @param iconSize logo在二维码中间的尺寸 iconSize * iconSize
//     * @param to 处理结果图片保存路径
//     * @throws IOException
//     */
//    public static void createQrcode(String content, String icon, int size, int iconSize, String to) throws IOException{
//        FileUtil.createPath(new File(to));
//        format(getQrcode(content, icon, size, iconSize), to);
//    }
//    /**
//     * 计算出二维码图片信息(可用于非文件输出流输出)
//     *
//     * @param content 二维码信息内容
//     * @param size 二维码尺寸size*size
//     * @return 二维码图片信息对象BufferedImage
//     * @throws IOException
//     */
//    public static BufferedImage getQrcode(String content, int size) throws IOException{
//        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
//        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        hints.put(EncodeHintType.MARGIN, 1);
//        BitMatrix bitMatrix;
//        try {
//            bitMatrix = new MultiFormatWriter().encode(content,
//                    BarcodeFormat.QR_CODE, size, size, hints);
//
//            bitMatrix = clearWhiteLine(bitMatrix);
//
//            int width = bitMatrix.getWidth();
//            int height = bitMatrix.getHeight();
//
//            BufferedImage image = new BufferedImage(width, height,
//                    BufferedImage.TYPE_INT_RGB);
//            for (int x = 0; x < width; x++) {
//                for (int y = 0; y < height; y++) {
//                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
//                            : 0xFFFFFFFF);
//                }
//            }
////			return image;
//            return resizeTo(image, size, size);
//        } catch (WriterException e) {
//            logger.warn(String.format("生成二维码失败：%s", e.getMessage()));
//            throw new IOException(String.format("生成二维码失败：%s", e.getMessage()));
//        }
//    }
//    /**
//     * 计算出二维码图片信息(可用于非文件输出流输出)
//     *
//     * @param content 二维码信息内容
//     * @param icon 二维码logo文件路径
//     * @param size 二维码尺寸size*size
//     * @param iconSize logo在二维码中间的尺寸 iconSize * iconSize
//     * @return 二维码图片信息对象BufferedImage
//     * @throws IOException
//     */
//    public static BufferedImage getQrcode(String content, String icon, int size, int iconSize) throws IOException{
//        BufferedImage image = getQrcode(content, size);
//        if(!StringUtil.isEmpty(icon) && iconSize>0){
//            image = Thumbnails
//                    .of(image)
//                    .scale(1)
//                    .watermark(new PointPosition(size/2-iconSize/2, size/2-iconSize/2),
//                            ImageIO.read(new File(icon)), 1).asBufferedImage();
//        }
//        return image;
//    }
//
//    private static BitMatrix clearWhiteLine(BitMatrix bitMatrix){
//        int[] rec = bitMatrix.getEnclosingRectangle();
//        int resWidth = rec[2] + 1;
//        int resHeight = rec[3] + 1;
//
//        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
//        resMatrix.clear();
//        for (int i = 0; i < resWidth; i++) {
//            for (int j = 0; j < resHeight; j++) {
//                if (bitMatrix.get(i + rec[0], j + rec[1]))
//                    resMatrix.set(i, j);
//            }
//        }
//        return resMatrix;
//    }
//
//    /**
//     * 指定坐标的位置类
//     *
//     * @author Lin.D.G
//     *
//     */
//    public final static class PointPosition implements Position{
//        private int x = 0,y = 0;
//        public PointPosition(int x, int y){
//            this.x=x;
//            this.y=y;
//        }
//        public Point getPoint(){
//            return new Point(x, y);
//        }
//        @Override
//        public Point calculate(int i, int j, int k, int l, int i1, int j1,
//                               int k1, int l1) {
//            return getPoint();
//        }
//    }
//    /**
//     * 指定坐标位置的水印图片层
//     *
//     * @author Lin.D.G
//     *
//     */
//    public final static class PointedWaterMark extends Watermark{
//        public PointedWaterMark(PointPosition position, BufferedImage bufferedimage, float f){
//            super(position,bufferedimage,f);
//        }
//
//    }
//}
//
