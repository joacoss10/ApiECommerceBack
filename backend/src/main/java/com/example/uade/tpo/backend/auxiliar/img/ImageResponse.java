package com.example.uade.tpo.backend.auxiliar.img;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageResponse {
    private long id;
    private String content;
}
