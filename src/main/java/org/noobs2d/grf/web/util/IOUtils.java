package org.noobs2d.grf.web.util;

import org.noobs2d.grf.web.config.json.GsonFactory;
import org.noobs2d.grf.web.controller.base.api.ApiResponse;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class IOUtils {

    public static void write(ApiResponse apiResponse, HttpServletResponse response) throws IOException {
        String responseTxt = GsonFactory.makeDefault().toJson(apiResponse);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().append(responseTxt);
        response.setStatus(apiResponse.getStatusCode());
    }

    public static void write(String responseBody, int statusCode, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().append(responseBody);
        response.setStatus(statusCode);
    }

    public static void writeImage(BufferedImage bufferedImage, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        try(ServletOutputStream outputStream = response.getOutputStream()) {
            ImageIO.write(bufferedImage, "jpeg", outputStream);
        }
    }

    public static void writeFile(File file, String fileName, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);

        OutputStream out = response.getOutputStream();
        try (FileInputStream in = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];

            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            out.flush();
        }
    }
}
