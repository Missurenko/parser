package service.impl;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import service.FileReadWrite;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileReadWriteImpl implements FileReadWrite {


    // delete child
    // собрать обратно
    //поток прикрутить
    //и чтоб можно автоматически параметри
    //
    @Override
    public Map<String, File> readDir(Map<String, File> allFiles, String dirPathHtml, List<String> keyWord) throws IOException {
        File htmlFile = new File(dirPathHtml);
        return listFilesFilteredForFolder(allFiles, htmlFile, keyWord);
    }


    @Override
    public boolean writeToDir(Element parseredOrigin, String path, String nameDirTask, String nameDoc) {



        File pathFile = new File(path);
        String nameDir = path.concat("/" + nameDirTask);
        File directory = new File(nameDir);
        String nameFullPathFile = nameDir.concat("/" + nameDoc);

        if (!pathFile.exists()) {
            directory.mkdir();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }
        if (!directory.exists()) {
            directory.mkdir();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }

        File dirPath = new File("C:\\Autonomy\\WebConnector\\example\\" +nameDoc);
        PrintStream out = null;
        try {
            out = new PrintStream(
                    new BufferedOutputStream(
                            new FileOutputStream(dirPath, true)));
            out.println(parseredOrigin);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        System.out.println("Writer : ");

        return true;

    }

    // TODO Change metod give only 1 folder
    private Map<String, File> listFilesFilteredForFolder(Map<String, File> allFiles, final File folder, List<String> keyWord) {

        if (folder.listFiles() == null) {
            return allFiles;
        }
        // test this part
        for (final File fileEntry : folder.listFiles()) {
            Document doc = null;
            if (allFiles.containsKey(fileEntry)) {
                if (!allFiles.get(fileEntry).equals(null)) {
                    allFiles.put(fileEntry.getName(), null);
                }
            }
            try {
                doc = Jsoup.parse(fileEntry, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!allFiles.containsKey(fileEntry)) {
                if (containKeyWordInDoc(doc, keyWord)) {
                    allFiles.put(fileEntry.getName(), fileEntry);
                } else {
                    fileEntry.delete();
                }

            }
        }
        return allFiles;
    }

    private File fileConfigFullPath(File folder) {

        if (folder.listFiles() == null) {
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fileConfigFullPath(folder);
        }
        for (final File fileEntry : folder.listFiles()) {
            String[] fileName = fileEntry.getName().split("\\.");
            if (fileName[1].equals("txt")) {
                return fileEntry;
            }
        }
        System.out.println("Need do txt file configuration webConnector/temp/");
        fileConfigFullPath(folder);
        return folder;

    }

    @Override
    public boolean delete(File file) {

        file.delete();

        return true;
    }

    @Override
    public List<String> readConfigByLine(String folder) {
        List<String> result = new ArrayList<>();
        File folderFile = new File(folder);
        String fullPath = fileConfigFullPath(folderFile).getAbsolutePath();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                String[] splitLine = line.split("/");
                if (!splitLine[0].equals("")) {
                    if (!line.equals("")) {
                        result.add(line);
                    }
                }
                ;


            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }

    @Override
    public List<String> readConfigurationTxt(String folder) {

        List<String> lines = null;
        try {

            File folderFile = new File(folder);
            String fullPath = fileConfigFullPath(folderFile).getAbsolutePath();
            File file = new File(fullPath);

            lines = FileUtils.readLines(file, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }


    public boolean containKeyWordInDoc(Document doc, List<String> keyWords) {
        for (String keyWord : keyWords) {
            if (doc.text().contains(keyWord)) {
                return true;
            }
        }
        return false;
    }
}
