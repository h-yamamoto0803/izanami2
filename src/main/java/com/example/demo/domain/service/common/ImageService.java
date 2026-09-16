package com.example.demo.domain.service.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    /**
     * アップロードされた画像を一時保存する。
     * 
     * 例：
     * category = "portfolio"
     * → uploads/portfolio/temp/ に保存
     *
     * @return 確認画面等で使用する参照URL
     *         例：/images/portfolio/temp/xxx.png
     */
	public String saveTempImage(
	        MultipartFile file,
	        String category) throws IOException {

	    // ファイル未選択チェック
	    if (file == null || file.isEmpty()) {
	        throw new IllegalArgumentException("ファイルが選択されていません");
	    }

	    // ファイルサイズチェック（5MB）
	    long maxSize = 1024 * 1024 * 5;

	    if (file.getSize() > maxSize) {
	        throw new IllegalArgumentException("ファイルサイズが大きすぎます");
	    }
	    // ファイル名取得
	    String originalFileName = file.getOriginalFilename();

	    if (originalFileName == null) {
	        throw new IllegalArgumentException("ファイル名を取得できません");
	    }

	    // 大文字小文字を統一して判定
	    String lowerFileName = originalFileName.toLowerCase();

	    // 拡張子チェック
	    if (!lowerFileName.endsWith(".png")
	            && !lowerFileName.endsWith(".jpg")
	            && !lowerFileName.endsWith(".jpeg")) {

	        throw new IllegalArgumentException(
	                "PNG、JPG、JPEG形式の画像のみアップロード可能です"
	        );
	    }

	    // チェックを通過したら保存処理へ
	    String fileName = createFileName(file);

	    String projectDir = System.getProperty("user.dir");

	    File saveDir = new File(
	            projectDir + "/uploads/" + category + "/temp/"
	    );

	    if (!saveDir.exists()) {
	        saveDir.mkdirs();
	    }

	    File dest = new File(saveDir, fileName);

	    file.transferTo(dest);

	    return "/images/" + category + "/temp/" + fileName;
	}


    /**
     * 一時保存画像を正式保存先へ移動する。
     *
     * 例：
     * uploads/portfolio/temp/xxx.png
     * ↓
     * uploads/portfolio/xxx.png
     *
     * @return 正式保存後の参照URL
     */
	public String moveToPermanent(
	        String tempImagePath,
	        String category) throws IOException {

	    // 一時保存URLからファイル名だけ取得する
	    // 例:
	    // /images/portfolio/temp/abc.png
	    // ↓
	    // abc.png
	    String fileName = Paths.get(tempImagePath).getFileName().toString();

	    // プロジェクトの実行ディレクトリを取得
	    String projectDir = System.getProperty("user.dir");

	    // 移動元
	    // uploads/portfolio/temp/abc.png
	    Path tempPath = Paths.get(
	            projectDir,
	            "uploads",
	            category,
	            "temp",
	            fileName
	    );

	    // 移動先フォルダ
	    // uploads/portfolio/
	    Path permanentDir = Paths.get(
	            projectDir,
	            "uploads",
	            category
	    );

	    // 念のため正式保存先フォルダを作成
	    Files.createDirectories(permanentDir);

	    // 移動先
	    // uploads/portfolio/abc.png
	    Path permanentPath = permanentDir.resolve(fileName);

	    // 一時保存場所から正式保存場所へ移動
	    Files.move(
	            tempPath,
	            permanentPath,
	            StandardCopyOption.REPLACE_EXISTING
	    );

	    // DBに保存する参照URLを返す
	    return "/images/" + category + "/" + fileName;
	}
    


    /**
     * 指定された画像ファイルを削除する。
     *
     * imagePathには、
     * /images/portfolio/xxx.png
     * のような参照URLが渡される想定。
     */
	public void deleteImage(String imagePath) throws IOException {

	    // 画像パスがnull・空なら何もしない
	    if (imagePath == null || imagePath.isBlank()) {
	        return;
	    }

	    // 例:
	    // /images/portfolio/abc.png
	    // ↓
	    // portfolio/abc.png
	    String relativePath =
	            imagePath.replaceFirst("^/images/", "");

	    // 実ファイルの保存場所を作る
	    // 例:
	    // C:/.../Traemon/uploads/portfolio/abc.png
	    Path filePath = Paths.get(
	            System.getProperty("user.dir"),
	            "uploads",
	            relativePath
	    );

	    // ファイルが存在すれば削除
	    Files.deleteIfExists(filePath);
	}


    /**
     * UUIDを使用して一意なファイル名を生成する。
     *
     * 元画像：
     * sample.png
     *
     * 生成例：
     * 550e8400-e29b-41d4-a716-446655440000.png
     */
    private String createFileName(MultipartFile image) {

        String originalFileName = image.getOriginalFilename();

        String extension = "";

        // 元ファイル名から拡張子を取得
        if (originalFileName != null
                && originalFileName.contains(".")) {

            extension = originalFileName.substring(
                    originalFileName.lastIndexOf(".")
            );
        }

        // UUID + 元画像の拡張子
        return UUID.randomUUID().toString() + extension;
    }
}