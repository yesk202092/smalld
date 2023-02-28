package com.smalld.archetype.common.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author yichuang.chen
 * @date 2019/1/9 10:43
 */
public class DownloadUtil {

    /**
     * 文件下载
     *
     * @param request
     * @param response
     * @param fileName 下载后保存的文件名
     * @param file     实际文件
     */
    public static void downLoadFile(HttpServletRequest request, HttpServletResponse response, String fileName, File file) {
        // 用于记录以完成的下载的数据量，单位是byte
        long downloadedLength = 0L;
        if (file == null || !file.exists()) {
            throw new BizException("下载的文件不存在");
        }
        String extName = fileName.substring(fileName.lastIndexOf("."));

        try (InputStream inputStream = new FileInputStream(file);// 打开本地文件流
             // 激活下载操作
             OutputStream os = response.getOutputStream()) {
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
            // 设置响应头和客户端保存文件名
            response.setCharacterEncoding("utf-8");
            handleContentType(extName, response);
            response.setHeader("Content-Disposition", "inline;fileName=" + fileName);
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.flushBuffer();



            // 循环写入输出流
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
                downloadedLength += length;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据后缀名处理contentType
     *
     * @param suffix
     * @param response
     * @return
     */
    private static void handleContentType(String suffix, HttpServletResponse response) {
        if (suffix == null || suffix.length() == 0) {
            response.setContentType("multipart/form-data");
        } else if ("pdf".equals(suffix)) {
            response.setContentType("application/pdf");
        } else if ("xls".equals(suffix) || "xlsx".equals(suffix)) {
            response.setContentType("application/vnd.ms-excel");
        } else if ("*".equals(suffix)) {
            response.setContentType("application/octet-stream");
        } else if ("323".equals(suffix)) {
            response.setContentType("text/h323");
        } else if ("acx".equals(suffix)) {
            response.setContentType("application/internet-property-stream");
        } else if ("ai".equals(suffix)) {
            response.setContentType("application/postscript");
        } else if ("aif".equals(suffix)) {
            response.setContentType("audio/x-aiff");
        } else if ("aifc".equals(suffix)) {
            response.setContentType("audio/x-aiff");
        } else if ("asf".equals(suffix) || "asr".equals(suffix) || "asx".equals(suffix)) {
            response.setContentType("video/x-ms-asf");
        } else if ("au".equals(suffix)) {
            response.setContentType("audio/basic");
        } else if ("avi".equals(suffix)) {
            response.setContentType("video/x-msvideo");
        } else if ("axs".equals(suffix)) {
            response.setContentType("application/olescript");
        } else if ("bas".equals(suffix)) {
            response.setContentType("text/plain");
        } else if ("bcpio".equals(suffix)) {
            response.setContentType("application/x-bcpio");
        } else if ("bin".equals(suffix)) {
            response.setContentType("application/octet-stream");
        } else if ("bmp".equals(suffix)) {
            response.setContentType("image/bmp");
        } else if ("c".equals(suffix)) {
            response.setContentType("text/plain");
        } else if ("cat".equals(suffix)) {
            response.setContentType("application/vnd.ms-pkiseccat");
        } else if ("cdf".equals(suffix)) {
            response.setContentType("application/x-cdf");
        } else if ("cer".equals(suffix)) {
            response.setContentType("application/x-x509-ca-cert");
        } else if ("class".equals(suffix)) {
            response.setContentType("application/octet-stream");
        } else if ("clp".equals(suffix)) {
            response.setContentType("application/x-msclip");
        } else if ("cmx".equals(suffix)) {
            response.setContentType("image/x-cmx");
        } else if ("cod".equals(suffix)) {
            response.setContentType("image/cis-cod");
        } else if ("cpio".equals(suffix)) {
            response.setContentType("application/x-cpio");
        } else if ("crd".equals(suffix)) {
            response.setContentType("application/x-mscardfile");
        } else if ("crl".equals(suffix)) {
            response.setContentType("application/pkix-crl");
        } else if ("crt".equals(suffix)) {
            response.setContentType("application/x-x509-ca-cert");
        } else if ("csh".equals(suffix)) {
            response.setContentType("application/x-csh");
        } else if ("css".equals(suffix)) {
            response.setContentType("text/css");
        } else if ("dcr".equals(suffix)) {
            response.setContentType("application/x-director");
        } else if ("der".equals(suffix)) {
            response.setContentType("application/x-x509-ca-cert");
        } else if ("dir".equals(suffix)) {
            response.setContentType("application/x-director");
        } else if ("dll".equals(suffix)) {
            response.setContentType("application/x-msdownload");
        } else if ("doc".equals(suffix) || "dot".equals(suffix)) {
            response.setContentType("application/msword");
        } else if ("dvi".equals(suffix)) {
            response.setContentType("application/x-dvi");
        } else if ("dxr".equals(suffix)) {
            response.setContentType("application/x-director");
        } else if ("eps".equals(suffix)) {
            response.setContentType("application/postscript");
        } else if ("etx".equals(suffix)) {
            response.setContentType("text/x-setext");
        } else if ("evy".equals(suffix)) {
            response.setContentType("application/envoy");
        } else if ("exe".equals(suffix)) {
            response.setContentType("application/octet-stream");
        } else if ("fif".equals(suffix)) {
            response.setContentType("application/octet-stream");
        } else if ("flr".equals(suffix)) {
            response.setContentType("application/fractals");
        } else if ("gif".equals(suffix)) {
            response.setContentType("image/gif");
        } else if ("gtar".equals(suffix)) {
            response.setContentType("application/x-gtar");
        } else if ("gz".equals(suffix)) {
            response.setContentType("application/x-gzip");
        } else if ("h".equals(suffix)) {
            response.setContentType("text/plain");
        } else if ("hdf".equals(suffix)) {
            response.setContentType("application/x-hdf");
        } else if ("hlp".equals(suffix)) {
            response.setContentType("application/winhlp");
        } else if ("hqx".equals(suffix)) {
            response.setContentType("application/mac-binhex40");
        } else if ("hta".equals(suffix)) {
            response.setContentType("application/hta");
        } else if ("htc".equals(suffix)) {
            response.setContentType("text/x-component");
        } else if ("htm".equals(suffix) || "html".equals(suffix)) {
            response.setContentType("text/html");
        } else if ("htt".equals(suffix)) {
            response.setContentType("text/webviewhtml");
        } else if ("ico".equals(suffix)) {
            response.setContentType("image/x-icon");
        } else if ("ief".equals(suffix)) {
            response.setContentType("image/ief");
        } else if ("iii".equals(suffix)) {
            response.setContentType("application/x-iphone");
        } else if ("ins".equals(suffix)) {
            response.setContentType("application/x-internet-signup");
        } else if ("isp".equals(suffix)) {
            response.setContentType("application/x-internet-signup");
        } else if ("jfif".equals(suffix)) {
            response.setContentType("image/pipeg");
        } else if ("jpe".equals(suffix) || "jpg".equals(suffix) || "jpeg".equals(suffix)) {
            response.setContentType("image/jpeg");
        } else if ("js".equals(suffix)) {
            response.setContentType("application/x-javascript");
        } else if ("latex".equals(suffix)) {
            response.setContentType("application/x-latex");
        } else if ("lha".equals(suffix)) {
            response.setContentType("application/octet-stream");
        } else if ("lsf".equals(suffix)) {
            response.setContentType("video/x-la-asf");
        } else if ("lsx".equals(suffix)) {
            response.setContentType("video/x-la-asf");
        } else if ("lzh".equals(suffix)) {
            response.setContentType("application/octet-stream");
        } else if ("m13".equals(suffix) || "m14".equals(suffix)) {
            response.setContentType("application/x-msmediaview");
        } else if ("m3u".equals(suffix)) {
            response.setContentType("audio/x-mpegurl");
        } else if ("man".equals(suffix)) {
            response.setContentType("application/x-troff-man");
        } else if ("mdb".equals(suffix)) {
            response.setContentType("application/x-msaccess");
        } else if ("me".equals(suffix)) {
            response.setContentType("application/x-troff-me");
        } else if ("mht".equals(suffix) || "mhtml".equals(suffix)) {
            response.setContentType("message/rfc822");
        } else if ("mid".equals(suffix)) {
            response.setContentType("audio/mid");
        } else if ("mny".equals(suffix)) {
            response.setContentType("application/x-msmoney");
        } else if ("mov".equals(suffix)) {
            response.setContentType("video/quicktime");
        } else if ("movie".equals(suffix)) {
            response.setContentType("video/x-sgi-movie");
        } else if ("mp2".equals(suffix) || "mpa".equals(suffix) || "mpe".equals(suffix)
                || "mpeg".equals(suffix) || "mpg".equals(suffix) || "mpv2".equals(suffix)) {
            response.setContentType("video/mpeg");
        } else if ("mp3".equals(suffix)) {
            response.setContentType("audio/mpeg");
        } else if ("mpp".equals(suffix)) {
            response.setContentType("application/vnd.ms-project");
        } else if ("ms".equals(suffix)) {
            response.setContentType("application/x-troff-ms");
        } else if ("mvb".equals(suffix)) {
            response.setContentType("application/x-msmediaview");
        } else if ("nws".equals(suffix)) {
            response.setContentType("message/rfc822");
        } else if ("oda".equals(suffix)) {
            response.setContentType("application/oda");
        } else if ("p10".equals(suffix)) {
            response.setContentType("application/pkcs10");
        } else if ("p12".equals(suffix)) {
            response.setContentType("application/x-pkcs12");
        } else if ("p7b".equals(suffix)) {
            response.setContentType("application/x-pkcs7-certificates");
        } else if ("p7c".equals(suffix)) {
            response.setContentType("application/x-pkcs7-mime");
        } else if ("p7m".equals(suffix)) {
            response.setContentType("application/x-pkcs7-mime");
        } else if ("p7r".equals(suffix)) {
            response.setContentType("application/x-pkcs7-certreqresp");
        } else if ("p7s".equals(suffix)) {
            response.setContentType("application/x-pkcs7-signature");
        } else if ("pbm".equals(suffix)) {
            response.setContentType("image/x-portable-bitmap");
        } else if ("pfx".equals(suffix)) {
            response.setContentType("application/x-pkcs12");
        } else if ("pgm".equals(suffix)) {
            response.setContentType("image/x-portable-graymap");
        } else if ("pko".equals(suffix)) {
            response.setContentType("application/ynd.ms-pkipko");
        } else if ("pma".equals(suffix)) {
            response.setContentType("application/x-perfmon");
        } else if ("pmc".equals(suffix)) {
            response.setContentType("application/x-perfmon");
        } else if ("pml".equals(suffix)) {
            response.setContentType("application/x-perfmon");
        } else if ("pmr".equals(suffix)) {
            response.setContentType("application/x-perfmon");
        } else if ("pmw".equals(suffix)) {
            response.setContentType("application/x-perfmon");
        } else if ("pnm".equals(suffix)) {
            response.setContentType("image/x-portable-anymap");
        } else if ("pot,".equals(suffix)) {
            response.setContentType("application/vnd.ms-powerpoint");
        } else if ("ppm".equals(suffix)) {
            response.setContentType("image/x-portable-pixmap");
        } else if ("pps".equals(suffix)) {
            response.setContentType("application/vnd.ms-powerpoint");
        } else if ("ppt".equals(suffix)) {
            response.setContentType("application/vnd.ms-powerpoint");
        } else if ("prf".equals(suffix)) {
            response.setContentType("application/pics-rules");
        } else if ("ps".equals(suffix)) {
            response.setContentType("application/postscript");
        } else if ("pub".equals(suffix)) {
            response.setContentType("application/x-mspublisher");
        } else if ("qt".equals(suffix)) {
            response.setContentType("video/quicktime");
        } else if ("ra".equals(suffix) || "ram".equals(suffix)) {
            response.setContentType("audio/x-pn-realaudio");
        } else if ("ras".equals(suffix)) {
            response.setContentType("image/x-cmu-raster");
        } else if ("rgb".equals(suffix)) {
            response.setContentType("image/x-rgb");
        } else if ("rmi".equals(suffix)) {
            response.setContentType("audio/mid http://www.dreamdu.com");
        } else if ("roff".equals(suffix)) {
            response.setContentType("application/x-troff");
        } else if ("rtf".equals(suffix)) {
            response.setContentType("application/rtf");
        } else if ("rtx".equals(suffix)) {
            response.setContentType("text/richtext");
        } else if ("scd".equals(suffix)) {
            response.setContentType("application/x-msschedule");
        } else if ("sct".equals(suffix)) {
            response.setContentType("text/scriptlet");
        } else if ("setpay".equals(suffix)) {
            response.setContentType("application/set-payment-initiation");
        } else if ("setreg".equals(suffix)) {
            response.setContentType("application/set-registration-initiation");
        } else if ("sh".equals(suffix)) {
            response.setContentType("application/x-sh");
        } else if ("shar".equals(suffix)) {
            response.setContentType("application/x-shar");
        } else if ("sit".equals(suffix)) {
            response.setContentType("application/x-stuffit");
        } else if ("snd".equals(suffix)) {
            response.setContentType("audio/basic");
        } else if ("spc".equals(suffix)) {
            response.setContentType("application/x-pkcs7-certificates");
        } else if ("spl".equals(suffix)) {
            response.setContentType("application/futuresplash");
        } else if ("src".equals(suffix)) {
            response.setContentType("application/x-wais-source");
        } else if ("sst".equals(suffix)) {
            response.setContentType("application/vnd.ms-pkicertstore");
        } else if ("stl".equals(suffix)) {
            response.setContentType("application/vnd.ms-pkistl");
        } else if ("stm".equals(suffix)) {
            response.setContentType("text/html");
        } else if ("svg".equals(suffix)) {
            response.setContentType("image/svg+xml");
        } else if ("sv4cpio".equals(suffix)) {
            response.setContentType("application/x-sv4cpio");
        } else if ("sv4crc".equals(suffix)) {
            response.setContentType("application/x-sv4crc");
        } else if ("swf".equals(suffix)) {
            response.setContentType("application/x-shockwave-flash");
        } else if ("t".equals(suffix)) {
            response.setContentType("application/x-troff");
        } else if ("tar".equals(suffix)) {
            response.setContentType("application/x-tar");
        } else if ("tcl".equals(suffix)) {
            response.setContentType("application/x-tcl");
        } else if ("tex".equals(suffix)) {
            response.setContentType("application/x-tex");
        } else if ("texi".equals(suffix) || "texinfo".equals(suffix)) {
            response.setContentType("application/x-texinfo");
        } else if ("tgz".equals(suffix)) {
            response.setContentType("application/x-compressed");
        } else if ("tif".equals(suffix) || "tiff".equals(suffix)) {
            response.setContentType("image/tiff");
        } else if ("tr".equals(suffix)) {
            response.setContentType("application/x-troff");
        } else if ("trm".equals(suffix)) {
            response.setContentType("application/x-msterminal");
        } else if ("tsv".equals(suffix)) {
            response.setContentType("text/tab-separated-values");
        } else if ("txt".equals(suffix)) {
            response.setContentType("text/plain");
        } else if ("uls".equals(suffix)) {
            response.setContentType("text/iuls");
        } else if ("ustar".equals(suffix)) {
            response.setContentType("application/x-ustar");
        } else if ("vcf".equals(suffix)) {
            response.setContentType("text/x-vcard");
        } else if ("vrml".equals(suffix)) {
            response.setContentType("x-world/x-vrml");
        } else if ("wav".equals(suffix)) {
            response.setContentType("audio/x-wav");
        } else if ("wcm".equals(suffix) || "wdb".equals(suffix)) {
            response.setContentType("application/vnd.ms-works");
        } else if ("wmf".equals(suffix)) {
            response.setContentType("application/x-msmetafile");
        } else if ("wps".equals(suffix)) {
            response.setContentType("application/vnd.ms-works");
        } else if ("wri".equals(suffix)) {
            response.setContentType("application/x-mswrite");
        } else if ("wrl".equals(suffix) || "wrz".equals(suffix) || "xaf".equals(suffix)) {
            response.setContentType("x-world/x-vrml");
        } else if ("xbm".equals(suffix)) {
            response.setContentType("image/x-xbitmap");
        } else if ("xla".equals(suffix) || "xlc".equals(suffix) || "xlm".equals(suffix)
                || "xlt".equals(suffix) || "xlw".equals(suffix)) {
            response.setContentType("application/vnd.ms-excel");
        } else if ("xof".equals(suffix)) {
            response.setContentType("x-world/x-vrml");
        } else if ("xpm".equals(suffix)) {
            response.setContentType("image/x-xpixmap");
        } else if ("xwd".equals(suffix)) {
            response.setContentType("image/x-xwindowdump");
        } else if ("z".equals(suffix)) {
            response.setContentType("application/x-compress");
        } else if ("zip".equals(suffix)) {
            response.setContentType("application/zip");
        }
    }
}
