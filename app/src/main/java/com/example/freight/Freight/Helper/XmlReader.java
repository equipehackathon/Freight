package com.example.freight.Freight.Helper;

import android.content.res.AssetManager;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlReader {
    public Document getDocumentNodes(InputSource inputSource) {
        //InputSource is = new InputSource(getResources().openRawResource(R.raw.config));
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Document doc = null;

        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(new InputSource(inputSource.getByteStream()));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return doc;
    }
}
