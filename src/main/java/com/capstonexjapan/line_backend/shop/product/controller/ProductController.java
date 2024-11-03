package com.capstonexjapan.line_backend.shop.product.controller;

import com.capstonexjapan.line_backend.ftp.FileConvert;
import com.capstonexjapan.line_backend.ftp.FtpServer;
import com.capstonexjapan.line_backend.shop.product.controller.request.CreateProduct;
import com.capstonexjapan.line_backend.shop.product.controller.request.UpdateProduct;
import com.capstonexjapan.line_backend.shop.product.service.ProductService;
import com.capstonexjapan.line_backend.shop.product.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
class ProductController {
    private final ProductService productService;
//    private final S3Service s3Service;
//    private final FtpServer ftpServer;

    @PostMapping("")
    public String createProduct(@RequestPart CreateProduct dto,
                                @RequestPart(required = false) MultipartFile file)throws IOException {
        if (file !=null && !file.isEmpty()) {
            MultipartFile convertedFile = FileConvert.fileToMultipartFileConvert(
                    FileConvert.multipartFileToFileConvert(file, "src/main/resources/tmp/"));
            String fileName = productService.uploadFile(convertedFile);
            productService.addProduct(dto, "https://capstone.thewc.co.jp/A/"+fileName);
            return "파일 과 같이 추가됨";
        }
        productService.addProduct(dto);
        return "추가됨";
    }

    @PostMapping("/practice")
    public String  hi(@RequestPart(required = false)MultipartFile file) throws IOException {
        MultipartFile convertedFile = FileConvert.fileToMultipartFileConvert(
                FileConvert.multipartFileToFileConvert(file, "src/main/resources/tmp/"));
        String url = productService.uploadFile(convertedFile);

        FileConvert.removeLocalFile("src/main/resources/tmp/" + file.getOriginalFilename());

        return "https://capstone.thewc.co.jp/A/"+url;
    }

    @GetMapping("")
    public ResponseEntity<?> readProducts(@RequestParam(required = false) Long id) {
        if (Objects.nonNull(id)) {
            return ResponseEntity.ok(productService.readProduct(id));
        }
        return ResponseEntity.ok(productService.readAllProduct());
    }

    @DeleteMapping("")
    public String deleteProduct(@RequestParam Long id) {
        productService.deleteById(id);
        return "삭제됨";
    }

    @PatchMapping("")
    public String updateProduct(@RequestParam Long id, @RequestBody UpdateProduct dto) {
        productService.updateById(id, dto);
        return "업데이트 됨";
    }


}
