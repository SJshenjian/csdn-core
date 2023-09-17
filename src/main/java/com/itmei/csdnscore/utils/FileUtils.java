package com.itmei.csdnscore.utils;

import org.springframework.boot.system.ApplicationHome;

import java.io.*;
import java.net.URL;

public class FileUtils {

    /**
     * 为了解决Idea运行查找文件路径找不到的问题以及,
     * 打包jar后查找不到文件jar包目录的文件信息
     *
     * @param fileName 文件名称
     * @return
     */
    public static String getFilePath(String fileName) {
        String jarF = null;
        //该类springBoot打包jar后就会读取不到信息,所以无法保存
        //使用Idea运行时没问题
        URL systemResource = ClassLoader.getSystemResource("");
        if (systemResource != null) {
            jarF = systemResource.getPath() + File.separator + fileName;
        } else {
            //如果是null则表示打包成jar包，需要使用以下获取路径信息
            ApplicationHome path = new ApplicationHome(ClassLoader.class);
            jarF = path.getDir().getPath() + File.separator + fileName;
        }
        return jarF;
    }


    /**
     * 文件是否存在
     *
     * @param fileName resources 下的文件
     * @return 存在返回true
     */
    public static boolean fileExist(String fileName) {
        //该类springBoot打包jar后就会读取不到信息,所以无法保存
        String filePath = getFilePath(fileName);
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }
        return false;
    }


    /**
     * 保存数据到文件中
     * 打包后会在根目录保存
     *
     * @param fileAbsolutePath 文件绝对路径 加 文件名称
     * @param data             需要保存的数据
     * @return 文件保存在target目录下的classes中
     */
    public static boolean saveFile(String fileAbsolutePath, String data) {
        File file = createFileDirectory(fileAbsolutePath);
        try {
            /**
             * 解决中文乱码
             */
            OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            oStreamWriter.write(data);
            oStreamWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 自动创建文件路径
     * -检查路径是否存在,
     * -对父目录验证是否存在自动创建路径
     *
     * @param filePath 项目中文件的相对路径
     * @return File对象
     */
    public static File createFileDirectory(String filePath) {
        String path = getFilePath(filePath);
        File file = createAbsoluteFile(path);
        return file;
    }

    /**
     * 创建绝对路径的文件或者目录
     *
     * @param absolutePath 绝对路径
     * @return file对象
     * 注意：父目录不能包含2个以上的.目录
     */
    public static File createAbsoluteFile(String absolutePath) {
        File file = new File(absolutePath);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                String[] splits = file.getPath().split("\\.");
                if (splits.length >= 2) {
                    file.createNewFile();
                } else {
                    file.mkdirs();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    /**
     * 写文件
     *
     * @param file      文件
     * @param fileBytes 字节
     */
    public static void writeFile(File file, byte[] fileBytes) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(fileBytes, 0, fileBytes.length);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(os);
        }
    }

    /**
     * 读取文件
     *
     * @param file 文件
     * @return 字节
     */
    public static byte[] readFileToByte(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            return toBytes(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * stream2byte[]
     *
     * @param input 输入流
     * @return 字节
     * @throws IOException IOException
     */
    public static byte[] toBytes(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            return output.toByteArray();
        } finally {
            close(output, input);
        }
    }


    /**
     * 关闭流
     *
     * @param outs Closeable
     */
    public static void close(Closeable... outs) {
        if (outs != null) {
            for (Closeable out : outs) {
                if (out != null) {
                    try {
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        boolean b = fileExist("weatherAreaidJson.txt");
        System.out.println(b);
    }
}
