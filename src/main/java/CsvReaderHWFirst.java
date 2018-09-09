import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.stat.descriptive.rank.Max;
import org.apache.commons.math3.stat.descriptive.rank.Min;

import org.apache.*;



/* ************************************************************************
Author's name(s): Junqiang Hu
Course Title: DataBase
Semester: Fall 2018
E-mail: jh689985@sju.edu
Assignment Number: HW1 Data Manipulation
Submission Date: 09/04/2018
Purpose: Write a program in your language of choice, that performs and prints out the following:
            1. loads the data in (using a library like CSV/JSON)
            2. counts the number of records in the set
            3. finds the longest 5 records in the dataset
            4. searches if there are any duplicates records in the set (and note how you define "duplicate")
            5. finds all records that were created in the first month of the data set's existence.
         Extra point: use a statistics library to calculate a statistical feature of the dataset (e.g. standard deviation, test for normality, etc.)
Input:	None
Output:  Answer to Five Questions And Extra Point
*************************************************************************** */

public class CsvReaderHWFirst {

    private HashMap<Integer, String> hmap = new HashMap();  //for Qustion 1
    private String csvFile = "KSEA.csv";
    private BufferedReader br = null;
    private String line = "";
    private String cvsSplitBy = ",";
    private int recordCount = 0;
    private int[] singleLineRecord = new int[4300];

    private HashMap<String, Integer> hmap3 = new HashMap(); //for Qustion 3
    private HashMap<String, String> hmap4 = new HashMap();  //for Qustion 4
    private HashMap<String, Date> hmap5 = new HashMap();    //for Qustion 5
    private List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>();
    private List<Map.Entry<String, String>> list4 = new ArrayList<Map.Entry<String, String>>();
    private ArrayList arrayList4 = new ArrayList<String>();
    private DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    private ArrayList arrayList5 = new ArrayList<Date>();


    public static void main(String[] args){
            CsvReaderHWFirst csvr= new CsvReaderHWFirst();
            csvr.loadData();//Question 1
            csvr.printRecordsNumber();//Question 2
            csvr.findLongestFiveRecords();//Question 3
            csvr.searchDuplicatesRecords();//Question 4
            csvr.findAllCreatedFirstMonthData();//Question 5
            csvr.calculateStatisticalDataset();
    }

    /*
     * Purpose: Initialize a population
     * Input:  null
     * Output: String[] ipop
     * Big O: n^2
     */
    public void calculateStatisticalDataset(){

    }

    /*
     * Purpose: Initialize a population
     * Input:  null
     * Output: String[] ipop
     * Big O: (O)^2
     */
    public void findAllCreatedFirstMonthData(){//Question 5
        //Q5: finds all records that were created in the first month of the data set's existence.

        System.out.println("Question 5: finds all records that were created in the first month of the data set's existence.");
        Date mindate = (Date) arrayList5.get(0);
        for (int i = 0; i < arrayList5.size(); i++) {  // (O)
            for (int j = i + 1; j < arrayList5.size(); j++) {  // (O)
                if (((Date) arrayList5.get(i)).compareTo((Date) arrayList5.get(j)) > 0) {
                    //  System.out.println(mindate+" :"+arrayList5.get(j));
                    mindate = (Date) arrayList5.get(j);
                }
            }
        }
        //System.out.println("Found the first day from all data set! "+mindate );

        int year = mindate.getYear();
        int month = mindate.getMonth();
        System.out.println("Found the first month from all data set!" );
        System.out.println("The First Month is : 0" + (1+month)+ "/"+ (1900+year) );
        System.out.println("The Data Set");
        for (Map.Entry<String, Date> entry : hmap5.entrySet()) {
            int yearCompare = entry.getValue().getYear();
            int monthCompare =entry.getValue().getMonth();
            if(year==yearCompare && month==monthCompare){
                System.out.println("Key:"+entry.getKey() );
            //    System.out.println("Key:"+entry.getKey()+"Date:"+entry.getValue());
            }
        }

        System.out.println("");
        System.out.println("--------------------------------------------------------" );
        System.out.println("--------------------------------------------------------" );
    }



    /*
     * Purpose: Initialize a population
     * Input:  null
     * Output: String[] ipop
     * Big O:  (O)
     */
    public void searchDuplicatesRecords(){//Question 4
        System.out.println("Question 4: searches if there are any duplicates records in the set :");


        ArrayList al = new ArrayList<Integer>();
        int size = arrayList4.size();
        int[] array = new int[size];
        entry:
        hmap4.entrySet();

        for (int i = 0; i < arrayList4.size(); i++) {
            //     System.out.println(entry.getValue());
            if (hmap4.containsKey(arrayList4.get(i))) {
                array[i]++;
            }
        }
        int countDuplicatesRecords = 0;
        for (int i = 0; i < array.length; i++) {
           // System.out.println("i:" + i + " size:" + array[i]);
            if(array[i]>1){
                System.out.println("Find Duplicates Records!"+arrayList4.get(i));
                countDuplicatesRecords++;
            }
        }
        if(countDuplicatesRecords==0){
            System.out.println( "There is not any Duplicates Records!" );
        }

        System.out.println("");
        System.out.println("--------------------------------------------------------" );
        System.out.println("--------------------------------------------------------" );
    }


    /*
     * Purpose: Initialize a population
     * Input:  null
     * Output: String[] ipop
     * Big O:  (O)
     */
    public void findLongestFiveRecords(){//Question 3
        //Q3: finds the longest 5 records in the dataset

        System.out.println("Q3: finds the longest 5 records in the dataset:");
        list.addAll(hmap3.entrySet());
        list4.addAll(hmap4.entrySet());
        CsvReaderHWFirst .ValueComparator vc = new CsvReaderHWFirst.ValueComparator();
        Collections.sort(list, vc);

        int count = 0;
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            if (count < 5) {
                System.out.println(it.next());
                count++;
            } else {
                break;
            }
        }
        System.out.println("");
        System.out.println("--------------------------------------------------------" );
        System.out.println("--------------------------------------------------------" );
    }


    /*
     * Purpose: Initialize a population
     * Input:  null
     * Output: String[] ipop
     * Big O: (1)
     */
    public static class ValueComparator implements Comparator<Map.Entry<String,Integer>> { //Question 3 Class
        public int compare(Map.Entry<String, Integer> m, Map.Entry<String, Integer> n) {
            return n.getValue() - m.getValue();
        }
    }


    /*
     * Purpose: Initialize a population
     * Input:  null
     * Output: String[] ipop
     * Big O: (1)
     */
    public void printRecordsNumber(){//Question 2
        System.out.println("Q2: The number of records in the set :" + recordCount);
        System.out.println("");
        System.out.println("--------------------------------------------------------" );
        System.out.println("--------------------------------------------------------" );
    }


    /*
     * Purpose: Initialize a population
     * Input:  null
     * Output: String[] ipop
     * Big O: O
     */
    public void loadData(){//Question 1
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) { //O
                // use comma as separator
              //  System.out.println(line);
                String[] temperatureDataSet = line.split(cvsSplitBy);
/*
                System.out.println("date= "                     + temperatureDataSet[0]   +   ",   actual_mean_temp=" + temperatureDataSet[1] +
                                   ",   actual_min_temp= "      + temperatureDataSet[2]   +   ",   actual_max_temp=" + temperatureDataSet[3]  +
                                   ",   average_min_temp= "     + temperatureDataSet[4]   +   ",   average_max_temp=" + temperatureDataSet[5] +
                                   ",   record_min_temp= "      + temperatureDataSet[6]   +   ",   record_max_temp=" + temperatureDataSet[7] +
                                   ",   record_min_temp_year= " + temperatureDataSet[8]   +   ",   record_max_temp_year=" + temperatureDataSet[9] +
                                   ",   actual_precipitation= " + temperatureDataSet[10]  +   ",   average_precipitation=" + temperatureDataSet[11] +
                                   ",   record_precipitation= " + temperatureDataSet[12] );
*/
                if (recordCount > 0) {
                    hmap3.put(temperatureDataSet[0], line.length());
                    hmap5.put(line, format1.parse(temperatureDataSet[0]));
                    hmap4.put(line, temperatureDataSet[0]);
                    arrayList4.add(recordCount - 1, line);
                    arrayList5.add(recordCount - 1, format1.parse(temperatureDataSet[0]));
                  //  System.out.println(line.length());
                }

                String s1 = temperatureDataSet[7];//record_max_temp
                if (recordCount > 0) {
                    try {
                        int i1 = Integer.parseInt(s1);  //record_max_temp

                        //  System.out.println(i1);
                        hmap.put(i1, temperatureDataSet[0]);
                    } catch (NumberFormatException NFE) {
                        System.out.println("error");
                    }
                }
                recordCount++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Question 1 : loads the data in (using a library like CSV/JSON):");
        System.out.println("Load the data Successfully!");
        System.out.println("");
        System.out.println("--------------------------------------------------------" );
        System.out.println("--------------------------------------------------------" );
    }
}
