package service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ParserHtml{



    List<Element> getSortedHtml(File file, List<String> keyWord) throws IOException;
}
