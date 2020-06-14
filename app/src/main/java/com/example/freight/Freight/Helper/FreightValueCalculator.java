package com.example.freight.Freight.Helper;

import com.example.freight.Freight.Model.Schedule;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class FreightValueCalculator {

    public double calculateFreightValue(InputSource inputSource, Schedule schedule) {
        XmlReader xmlReader = new XmlReader();
        Document document = xmlReader.getDocumentNodes(inputSource);

        document.getDocumentElement().normalize();
        double freightValue;

        NodeList tableNodeList = document.getElementsByTagName("Tables").item(0).getChildNodes();
        float displacementValue = 0;
        float cargoValue = 0;

        for (int i = 0; i < tableNodeList.getLength(); i++) {
            if (tableNodeList.item(i).getNodeType() == Node.ELEMENT_NODE && Integer.parseInt(tableNodeList.item(i).getAttributes().getNamedItem("value").getNodeValue()) == schedule.getTransportCategory()) {
                NodeList cargoTypeNodeList = tableNodeList.item(i).getChildNodes();

                for (int j = 0; j < cargoTypeNodeList.getLength(); j++) {
                    if (cargoTypeNodeList.item(j).getNodeType() == Node.ELEMENT_NODE && Integer.parseInt(cargoTypeNodeList.item(j).getAttributes().getNamedItem("value").getNodeValue()) == schedule.getCargoType()) {

                        NodeList displacementNodeList = cargoTypeNodeList.item(j).getChildNodes();

                        for (int k = 0; k < displacementNodeList.getLength(); k++) {
                            if (displacementNodeList.item(k).getNodeType() == Node.ELEMENT_NODE && Integer.parseInt(displacementNodeList.item(k).getAttributes().getNamedItem("value").getNodeValue()) == 1) {
                                NodeList axisNumberNodeList = displacementNodeList.item(k).getChildNodes();

                                for (int l = 0; l < axisNumberNodeList.getLength(); l++) {
                                    if (axisNumberNodeList.item(l).getNodeType() == Node.ELEMENT_NODE && Integer.parseInt(axisNumberNodeList.item(l).getAttributes().getNamedItem("value").getNodeValue()) == schedule.getAxisNumber()) {
                                        displacementValue = Float.parseFloat(axisNumberNodeList.item(l).getTextContent());
                                        break;
                                    }
                                }
                                break;
                            }
                        }

                        NodeList cargoNodeList = cargoTypeNodeList.item(j).getChildNodes();

                        for (int k = 0; k < cargoNodeList.getLength(); k++) {
                            if (cargoNodeList.item(k).getNodeType() == Node.ELEMENT_NODE && Integer.parseInt(cargoNodeList.item(k).getAttributes().getNamedItem("value").getNodeValue()) == 2) {
                                NodeList axisNumberNodeList = displacementNodeList.item(k).getChildNodes();

                                for (int l = 0; l < axisNumberNodeList.getLength(); l++) {
                                    if (axisNumberNodeList.item(l).getNodeType() == Node.ELEMENT_NODE && Integer.parseInt(axisNumberNodeList.item(l).getAttributes().getNamedItem("value").getNodeValue()) == schedule.getAxisNumber()) {
                                        cargoValue = Float.parseFloat(axisNumberNodeList.item(l).getTextContent());
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }

        freightValue = displacementValue * schedule.getDistance() + cargoValue;

        return freightValue;

    }

}
