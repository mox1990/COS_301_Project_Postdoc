/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softserve.ejb;

import java.io.BufferedReader;
import java.io.IOException;
import javax.ejb.Local;

/**
 *
 * @author kgothatso
 */
@Local
public interface ImportApplicationServicesLocal {
    public void importCSVApplicationData(BufferedReader csvInput) throws IOException, IllegalAccessException, Exception;
    public void importCSVRefereeReportData(BufferedReader csvInput) throws IOException, IllegalAccessException, Exception;
    public void importCSVProgressReportData(BufferedReader csvInput) throws IOException, IllegalAccessException, Exception;
    public void importCSVRecommendationReportData(BufferedReader csvInput) throws IOException, IllegalAccessException, Exception;
    public void importCSVEndorsementData(BufferedReader csvInput) throws IOException, IllegalAccessException, Exception;
    public void importCSVFundingReportData(BufferedReader csvInput) throws IOException, IllegalAccessException, Exception;
}
