package com.roidmc.core.util.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipCompress {

    public synchronized static void zip(File folder){
        Path path = folder.toPath();
        String zipFileName = folder.getAbsolutePath().concat(".zip");
        try(ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFileName))){
            Consumer<Path> put = (out)->{
                try {
                    outputStream.putNextEntry(new ZipEntry(out.toString()));
                    byte[] data = Files.readAllBytes(out);
                    outputStream.write(data,0,data.length);
                    outputStream.closeEntry();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            };
            if(folder.isFile())put.accept(folder.getParentFile().toPath().relativize(folder.toPath()));
            else Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    put.accept(path.relativize(file));
                    return FileVisitResult.CONTINUE;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized static void unZip(File zipFile, File destDir){
        byte[] buffer = new byte[1024];
        try(ZipInputStream inputStream = new ZipInputStream(new FileInputStream(zipFile))){
            ZipEntry zipEntry;
            while ((zipEntry = inputStream.getNextEntry()) != null) {
                File newFile = newFile(destDir, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Falha em criar o diretorio " + newFile);
                    }
                } else {
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Falha em criar o diretorio " + parent);
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                    int len;
                    while ((len = inputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.close();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Tentativa de Zip Slip: " + zipEntry.getName());
        }

        return destFile;
    }
}
