import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Java Program to parse/read HTML documents from File using Jsoup library.
 * Jsoup is an open source library which allows Java developer to parse HTML
 * files and extract elements, manipulate data, change style using DOM, CSS and
 * JQuery like method.
 *
 * @author Javin Paul
 */
public class HTMLParser{
    public static void main(String[] args) throws Exception {
        HTMLParser parser = new HTMLParser();
        String url;
        String name;
        Thread myThready = new Thread(new Runnable(){
            public void run(){
                parser.startParsing();
            }
        });
        parser.startParsing();
        myThready.start();
    }

    private String randomImg(){
        int length = 6;
        Random r = new Random();
        String s = r.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        System.out.println(s.toLowerCase());
        return s.toLowerCase();
    }

    private void startParsing(){
        String url;
        String name;
        for (int i = 0; i < 200; i++) {
            name = randomImg();
            try {
                Document doc = Jsoup.connect("https://prnt.sc/" + name + ".png").get();
                url = doc.select("img[src$=.png]")
                        .first()
                        .attr("src");
                System.out.println(url);

                URL urls = new URL(url);
                URLConnection conn = urls.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");

                InputStream ins = conn.getInputStream();
                Files.copy(ins, Paths.get("C://Users/minec/Desktop/JavaParserPrnsc/" + name + ".png"));
            } catch (MalformedURLException ignored){} catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}