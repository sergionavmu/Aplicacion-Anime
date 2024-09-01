package abp.animeg7.anime.dao;

import abp.animeg7.anime.model.Anime;
import abp.animeg7.anime.model.Favorito;
import abp.animeg7.anime.model.User;
import abp.animeg7.anime.model.Video;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HandlerSax extends DefaultHandler {
    private ArrayList<Anime> animes;
    private ArrayList<Video> videos;
    private ArrayList<User> users;
    private ArrayList<Favorito> favoritos;
    private Anime anime;
    private Video video;
    private User user;
    private Favorito favorito;
    private StringBuilder buffer = new StringBuilder();
    private String textoColumna;
    private String modelo;

    public ArrayList<Anime> getAnimes(String s) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser;
        try {
            parser = factory.newSAXParser();
            File file = new File(s);
            parser.parse(file, this);
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println("ERROR creating the parser");
        } catch (IOException e) {
            System.out.println("ERROR file not found");
        }

        return this.animes;
    }

    public ArrayList<User> getUsers(String s) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser;
        try {
            parser = factory.newSAXParser();
            File file = new File(s);
            parser.parse(file, this);
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println("ERROR creating the parser");
        } catch (IOException e) {
            System.out.println("ERROR file not found");
        }

        return this.users;
    }

    public ArrayList<Video> getVideos(String s) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser;
        try {
            parser = factory.newSAXParser();
            File file = new File(s);
            parser.parse(file, this);
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println("ERROR creating the parser");
        } catch (IOException e) {
            System.out.println("ERROR file not found");
        }

        return this.videos;
    }

    public ArrayList<Favorito> getFavoritos(String s) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser;
        try {
            parser = factory.newSAXParser();
            File file = new File(s);
            parser.parse(file, this);
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println("ERROR creating the parser");
        } catch (IOException e) {
            System.out.println("ERROR file not found");
        }

        return this.favoritos;
    }

    @Override
    public void startDocument() throws SAXException {
        this.animes = new ArrayList<>();
        this.videos = new ArrayList<>();
        this.users = new ArrayList<>();
        this.favoritos = new ArrayList<>();
    }

    @Override
    public void endDocument() throws SAXException {
        printDocument();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        buffer.append(ch, start, length);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "table":
                if ("animes".equals(attributes.getValue("name"))) {
                    anime = new Anime();
                    modelo = "anime";
                    animes.add(anime);
                }
                if ("users".equals(attributes.getValue("name"))) {
                    user = new User();
                    modelo = "user";
                    users.add(user);
                }
                if ("videos".equals(attributes.getValue("name"))) {
                    video = new Video();
                    modelo = "video";
                    videos.add(video);
                }
                if ("favorite".equals(attributes.getValue("name"))) {
                    favorito = new Favorito();
                    modelo = "favorite";
                    favoritos.add(favorito);
                }
                break;
            case "column":
                textoColumna = attributes.getValue("name");
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "column":
                if (modelo.equals("anime")) {
                    switch (textoColumna) {
                        case "id":
                            anime.setId(Integer.parseInt(buffer.toString().trim()));
                            break;
                        case "name":
                            anime.setName(buffer.toString().trim());
                            break;
                        case "description":
                            anime.setDescription(buffer.toString().trim());
                            break;
                        case "type":
                            anime.setType(buffer.toString().trim());
                            break;
                        case "year":
                            anime.setYear(Integer.parseInt(buffer.toString().trim()));
                            break;
                        case "image":
                            anime.setImage(buffer.toString().trim());
                            break;
                        case "originalname":
                            anime.setOriginalName(buffer.toString().trim());
                            break;
                        case "rating":
                            anime.setRating(buffer.toString().trim());
                            break;
                        case "demography":
                            anime.setDemography(buffer.toString().trim());
                            break;
                        case "genre":
                            anime.setGenre(buffer.toString().trim());
                            break;
                        case "image2":
                            anime.setImage2(buffer.toString().trim());
                            break;
                        case "active":
                            anime.setActive(Boolean.parseBoolean(buffer.toString().trim()));
                            break;
                    }
                } else if (modelo.equals("user")) {
                    switch (textoColumna) {
                        case "name":
                            user.setName(buffer.toString().trim());
                        case "email":
                            user.setEmail(buffer.toString().trim());
                            break;
                        case "password":
                            user.setPassword(buffer.toString().trim());
                            break;
                        case "phone":
                            user.setPhone(buffer.toString().trim());
                            break;
                    }
                } else if (modelo.equals("video")) {
                    switch (textoColumna) {
                        case "idanime":
                            video.setIdanime(Integer.parseInt(buffer.toString().trim()));
                            break;
                        case "episode":
                            video.setEpisode(Integer.parseInt(buffer.toString().trim()));
                            break;
                        case "url":
                            video.setUrl(buffer.toString().trim());
                            break;
                        case "image":
                            video.setImage(buffer.toString().trim());
                            break;
                    }
                } else if (modelo.equals("favorite")) {
                    switch (textoColumna) {
                        case "iduser":
                            favorito.setIduser(Integer.parseInt(buffer.toString().trim()));
                            break;
                        case "idanime":
                            favorito.setIdanime(Integer.parseInt(buffer.toString().trim()));
                            break;
                    }
                }
                buffer.setLength(0); // Limpiar el buffer después de procesar el contenido
                break;
        }
    }

    private void printDocument() {

    }
}
