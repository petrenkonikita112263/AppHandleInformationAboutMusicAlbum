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
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Class marked as service, that gets the list of the albums, and writes
 * the information into docx document using Apache POI.
 * Caching the ByteArrayOutputStream.
 */
@Service
@CacheConfig(cacheNames = {"baos"})
public class AlbumStorage implements Savable {

    /**
     * Constant for this class that add logging functionality.
     */
    private static final Logger LOGGER = Logger.getLogger(AlbumStorage.class);

    private IMusicService musicService;
    private String storagePoint;

    /**
     * Constructor with arguments.
     *
     * @param storagePoint - holds the path from properties file to the string
     * @param musicService - object of interface class
     */
    public AlbumStorage(@Value("${documentPath}") String storagePoint, IMusicService musicService) {
        this.storagePoint = storagePoint;
        this.musicService = musicService;
    }

    /**
     * Implementing method from interface class. Also this method marked as
     * cacheable - the return value is now caching.
     *
     * @param summaryList - reads information about list of album and
     *                    writes it to the docx document
     * @return - stream of data that was sent to the buffer
     */
    @Override
    @Cacheable
    public ByteArrayOutputStream createTemplateDocument(List<AlbumSummary> summaryList) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        File file = new File(storagePoint);
        if (!file.exists()) {
            createFileWithFolder();
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            try (XWPFDocument document = new XWPFDocument(fis)) {

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
                        for (int i = 2; i < content.getListOfPosters().size(); i += 10) {
                            String imgUrl = content.getListOfPosters().get(i).getStorageUrl();
                            try (BufferedInputStream bis = new BufferedInputStream(new URL(imgUrl).openStream());) {
                                try {
                                    imageRun.addPicture(bis, XWPFDocument.PICTURE_TYPE_PNG, "",
                                            Units.toEMU(200), Units.toEMU(200));
                                } catch (InvalidFormatException e) {
                                    LOGGER.error("Invalid type format", e);
                                }
                            } catch (IOException e) {
                                LOGGER.error("Can't close BufferedInputStream with ");
                            }
                        }
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
                        String text;
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
                        table.setTableAlignment(TableRowAlign.CENTER);
                        XWPFTableRow mainRow = table.getRow(0);
                        mainRow.getCell(0).setText("Name Of Track");
                        mainRow.addNewTableCell().setText("Track Duration (X:XX, MI:SS)");
                        for (int i = 0; i < content.getCollectionOfTracks().size(); i++) {
                            XWPFTableRow contentRow = table.createRow();
                            XWPFTableCell column_1 = contentRow.getCell(0);
                            column_1.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                            if (column_1 != null) {
                                column_1.setText(content.getCollectionOfTracks().get(i).getTrackName());
                            }
                            XWPFTableCell column_2 = contentRow.getCell(1);
                            column_2.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
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
                        baos.flush();
                        baos.close();
                    } catch (IOException e) {
                        LOGGER.error("Can't close output stream ", e);
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Can't close XWPFDocument using try-with-resources", e);
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Can't get acess to the file, invalid path or etc.", e);
        } catch (IOException e) {
            LOGGER.error("Can't close FileInputStream using try-with-resources", e);
        }
        return baos;
    }

    /**
     * Additional method that creates default empty docs document.
     */
    private void createFileWithFolder() {
        Path path = Paths.get(storagePoint);
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            LOGGER.error("Can't create folder by path", e);
        }
        try {
            XWPFDocument document = new XWPFDocument();
            FileOutputStream out = new FileOutputStream(
                    new File(storagePoint));
            document.write(out);
            out.close();
            document.close();
        } catch (FileAlreadyExistsException e) {
            LOGGER.error("Can't create file in folder", e);
        } catch (IOException e) {
            LOGGER.error("Failure in reading path", e);
        }
    }

    /**
     * Implemeting another method from interface Savable.
     *
     * @param nameOfArtist - string value with the name of the band|signer
     * @param titleOfAlbum - string value with the name of the album
     * @return - sent buffer data to the stream
     */
    @Override
    public ByteArrayOutputStream saveAlbum(String nameOfArtist, String titleOfAlbum) {
        return createTemplateDocument(musicService.obtaineAlbumThroughName(nameOfArtist, titleOfAlbum));
    }
}
