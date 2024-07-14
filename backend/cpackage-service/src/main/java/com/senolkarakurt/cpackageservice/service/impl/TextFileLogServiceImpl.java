package com.senolkarakurt.cpackageservice.service.impl;

import com.senolkarakurt.cpackageservice.service.SystemLogService;
import com.senolkarakurt.dto.request.SystemLogSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Service("textFileLogService")
@RequiredArgsConstructor
public class TextFileLogServiceImpl implements SystemLogService {

    public final static DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");

    @Override
    public void save(SystemLogSaveRequestDto systemLogSaveRequestDto) {
        Path resourceDirectory = Paths.get("cpackage-service", "src", "main", "resources", "logs", STR."\{systemLogSaveRequestDto.getRecordDateTime().format(CUSTOM_FORMATTER)}.txt");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        FileOutputStream fileOutputStream = null;
        File file;
        try{
            file = new File(absolutePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            byte[] array = systemLogSaveRequestDto.getContent().getBytes();
            fileOutputStream.write(array);
            fileOutputStream.flush();
            fileOutputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}