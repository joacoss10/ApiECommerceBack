package com.example.uade.tpo.backend.auxiliar.img;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class AddFileRequest {
    List<MultipartFile> files;
}
