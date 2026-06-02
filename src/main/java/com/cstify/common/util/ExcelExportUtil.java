//package com.snc.pg.common.util;
//
//import com.alibaba.excel.EasyExcel;
//import com.alibaba.excel.write.handler.WriteHandler;
//import com.snc.pg.excel.handler.HeaderRowHeightHandler;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.function.Function;
//import java.util.stream.Stream;
//
//public class ExcelExportUtil {
//    private static final DateTimeFormatter FILE_DATE_FMT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
//
//    private static String generateFileName(String prefix) {
//        return prefix + "_" + LocalDateTime.now().format(FILE_DATE_FMT) + ".xlsx";
//    }
//
//    public static <T, D> void download(
//            HttpServletResponse response,
//            String prefix,
//            String sheetName,
//            List<T> data,
//            Function<T, D> mapper,
//            Class<D> header,
//            WriteHandler styleHandler
//    ) throws Exception {
//
//        String fileName = generateFileName(prefix);
//        write(response, fileName, sheetName, data.stream().map(mapper), header, styleHandler);
//    }
//
//    public static <T, D> void download(
//            HttpServletResponse response,
//            String prefix,
//            String sheetName,
//            WriteHandler styleHandler,
//            Class<D> header,
//            Stream<T> dataStream,
//            Function<T, D> mapper
//    ) throws Exception {
//
//        String fileName = generateFileName(prefix);
//        write(response, fileName, sheetName, dataStream.map(mapper), header, styleHandler);
//    }
//
//    private static <D> void write(
//            HttpServletResponse response,
//            String fileName,
//            String sheetName,
//            Stream<?> dataStream,
//            Class<D> header,
//            WriteHandler styleHandler
//    ) throws Exception {
//
//        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
//
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
//
//        EasyExcel.write(response.getOutputStream(), header)
//                .registerWriteHandler(styleHandler)
//                .registerWriteHandler(new HeaderRowHeightHandler())
//                .sheet(sheetName)
//                .doWrite(dataStream.toList()); // stream을 리스트로 소비 후 다운로드
//    }
//}
