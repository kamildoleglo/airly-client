package controllers;

import models.APIClient;

import java.io.IOException;
import java.net.URISyntaxException;

public class ApplicationController {
    public static void main(String[] args){
        APIClient test = new APIClient("63238849a08049fc841a11d84db77ef7");
        try{
            System.out.println(test.getMeasurementsForPointOnMap("50.06201","19.94098").getCurrentMeasurements().getAddress());
           // test.test();
        }catch (IOException e){
            System.out.println("Connection problem");
        }
    }
}
