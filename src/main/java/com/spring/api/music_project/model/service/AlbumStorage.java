package com.spring.api.music_project.model.service;

import com.spring.api.music_project.model.AlbumSummary;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"baos"})
public class AlbumStorage implements Savable {

    private static final Logger LOGGER = Logger.getLogger(AlbumStorage.class);

    private IMusicService musicService;
    private String storagePoint;

    public AlbumStorage(@Value("${documentPath}") String storagePoint, IMusicService musicService) {
        this.storagePoint = storagePoint;
        this.musicService = musicService;
    }

    @Override
    @Cacheable
    public ByteArrayOutputStream createTemplateDocument(List<AlbumSummary> summaryList) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        File file = new File(storagePoint);
        if (!file.exists()) {
            createFileWithFolder();
        }
        XWPFDocument document = new XWPFDocument();
        try {
            XWPFParagraph h1 = document.createParagraph();
            h1.setAlignment(ParagraphAlignment.CENTER);
            XWPFParagraph image = document.createParagraph();
            image.setAlignment(ParagraphAlignment.CENTER);
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.DISTRIBUTE);
            XWPFParagraph name = document.createParagraph();
            name.setAlignment(ParagraphAlignment.DISTRIBUTE);
            XWPFParagraph genre = document.createParagraph();
            genre.setAlignment(ParagraphAlignment.DISTRIBUTE);

            XWPFRun h1Run = h1.createRun();
            h1Run.setText("The Response Generated WORD Document");
            h1Run.setBold(true);
            h1Run.setFontFamily("Times New Roman");
            h1Run.setFontSize(18);
            h1Run.addBreak();
            h1Run.setText("Made in Java Spring Boot using Last.fm API");
            h1Run.setBold(true);
            h1Run.setFontFamily("Times New Roman");
            h1Run.setFontSize(15);
            h1Run.addBreak();

            for (AlbumSummary content : summaryList) {
                XWPFRun imageRun = image.createRun();
                if (content.getListOfPosters().isEmpty()) {
                    imageRun.setText("The found album doesn't have any posters");
                } else {
//                    try {
                    for (int i = 2; i < content.getListOfPosters().size(); i += 10) {
                        try {
                            imageRun.addPicture(getImage(content.getListOfPosters().get(i).getStorageUrl()),
                                    XWPFDocument.PICTURE_TYPE_PNG, "",
                                    Units.toEMU(200), Units.toEMU(200));
                        } catch (IOException e) {
                            LOGGER.error("Can't input the image ", e);
                        } catch (InvalidFormatException e) {
                            LOGGER.error("Invalid format ", e);
                        }
                    }
//                    } catch (InvalidFormatException e) {
//                        LOGGER.error("Invalid format ", e);
//                    }
//                    } catch (MalformedURLException e) {
//                        LOGGER.error("Can't create a url link ", e);
//                    } catch (IOException e) {
//                        LOGGER.error("Writing process's failed ", e);
//                    }
                }
                imageRun.addBreak();
                imageRun.setTextPosition(20);
                imageRun.setText("Fig. Main Poster Of Album");
                imageRun.addBreak();
                imageRun.addBreak();
                XWPFRun titleRun = title.createRun();
                titleRun.setText("The Album Name is: " + content.getAlbumTitle());
                titleRun.setFontFamily("Times New Roman");
                titleRun.setFontSize(14);
                XWPFRun nameRun = name.createRun();
                nameRun.setText("The Band|Artist|Signer name is: " + content.getArtistName());
                nameRun.setFontFamily("Times New Roman");
                nameRun.setFontSize(14);
                nameRun.addBreak();
                nameRun.addBreak();
                XWPFRun genreRun = genre.createRun();
                genreRun.setFontFamily("Times New Roman");
                genreRun.setFontSize(14);
                if (content.getTags().isEmpty()) {
                    genreRun.setText("Album don't have information about genre of music");
                } else {
                    String text = null;
                    genreRun.setText("The Genre Of Music:");
                    for (int i = 0; i < content.getTags().size(); i++) {
                        text = " " + content.getTags().get(i).getName() + " ";
                        genreRun.setText(text);
                    }
                }
                genreRun.addBreak();
                genreRun.addBreak();
                XWPFRun trackRun = title.createRun();
                if (content.getCollectionOfTracks().isEmpty()) {
                    trackRun.setText("There're no tracks in album");
                } else {
                    XWPFTable table = document.createTable();
                    XWPFTableRow mainRow = table.getRow(0);
                    mainRow.getCell(0).setText("Name Of Track");
                    mainRow.addNewTableCell().setText("Track Duration (X:XX, MI:SS)");
                    for (int i = 0; i < content.getCollectionOfTracks().size(); i++) {
                        XWPFTableRow contentRow = table.createRow();
                        XWPFTableCell column_1 = contentRow.getCell(0);
                        if (column_1 != null) {
                            column_1.setText(content.getCollectionOfTracks().get(i).getTrackName());
                        }
                        XWPFTableCell column_2 = contentRow.getCell(1);
                        if (column_2 != null) {
                            column_2.setText(content.getCollectionOfTracks().get(i).getDuration());
                        }
                    }
                }
            }
            try {
                document.write(baos);
            } catch (IOException e) {
                LOGGER.error("Some went wrong in process writing into file ", e);
            } finally {
                try {
//                    fis.close();
                    baos.flush();
                    baos.close();
                } catch (IOException e) {
                    LOGGER.error("Can't close output stream ", e);
                }
            }
        } finally {
            try {
//                fis.close();
                document.close();
            } catch (IOException e) {
                LOGGER.error("Can't close document stream ", e);
            }
        }
        return baos;
    }

    private InputStream getImage(String url) throws IOException {
        InputStream is = null;
        try {
            URL imageUrl = new URL(url);
            URLConnection urlConnection = imageUrl.openConnection();
            is = imageUrl.openStream();
            urlConnection.setRequestProperty("User-Agent", "Default");
        } catch (MalformedURLException e) {
            LOGGER.error("Can't create a url link ", e);
        } catch (IOException e) {
            LOGGER.error("Writing process's failed ", e);
        }
        return is;
    }

    private void closeInputStream(InputStream is) {
        try {
            is.close();
        } catch (IOException e) {
            LOGGER.error("Can't close the stream ", e);
        }
    }

    private void createFileWithFolder() {
        Path path = Paths.get(storagePoint);
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            LOGGER.error("Can't create folder by path", e);
            e.printStackTrace();
        }
        try {
            Files.createFile(path);
        } catch (FileAlreadyExistsException e) {
            LOGGER.error("Can't create file in folder", e);
        } catch (IOException e) {
            LOGGER.error("Failure in reading path", e);
        }
    }

    @Override
    public ByteArrayOutputStream saveAlbum(String nameOfArtist, String titleOfAlbum) {
        return createTemplateDocument(musicService.obtaineAlbumThroughName(nameOfArtist, titleOfAlbum));
    }
}
