package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountryInfo {
    private String name;
    private String cases;
    private String deaths;
    private String recovered;
    private String active;
    private LocalTime time;

    private ObservableList<String> nameslist;
    private List<String> caseslist;
    private List<String> deathslist;
    private List<String> recoveredlist;
    private List<String> activelist;

    private ObservableList<CountryInfo> wholelist;

    public CountryInfo(){
      wholelist=FXCollections.observableArrayList();
      nameslist=FXCollections.observableArrayList();
      caseslist=new ArrayList<>();
      deathslist=new ArrayList<>();
      recoveredlist=new ArrayList<>();
      activelist=new ArrayList<>();
    }

    public CountryInfo(String name, String cases, String deaths, String recovered, String active) {
        this.name = name;
        this.cases = cases;
        this.deaths = deaths;
        this.recovered = recovered;
        this.active = active;
    }

    public ObservableList<String> getNameslist() {
        return nameslist;
    }
    public ObservableList<CountryInfo> getWholelist(){
        return wholelist;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getCases() {
        return cases;
    }

    public String getDeaths() {
        return deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getActive() {
        return active;
    }

    public void allinitialize(){
        try {
            URL url = new URL("https://coronavirus-19-api.herokuapp.com/countries");
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            BufferedReader input = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder inputbuilder=new StringBuilder(input.readLine());

            String string="(\\{)(.*?)(\\})";
            Pattern pattern=Pattern.compile(string);
            Matcher matcher=pattern.matcher(inputbuilder);

            while (matcher.find()){
                StringBuilder sb=new StringBuilder(matcher.group(2));
                String str="(\"country\")(\\:)(\")(.+?)(\")(\\,)";
                Pattern pattern1=Pattern.compile(str);
                Matcher matcher1=pattern1.matcher(sb);

                while (matcher1.find()){
                    nameslist.add(matcher1.group(4));
                }

                String str2="(\"cases\")(\\:)(.*?)(\\,)";
                Pattern pattern2=Pattern.compile(str2);
                Matcher matcher2=pattern2.matcher(sb);

                while (matcher2.find()){
                    caseslist.add(matcher2.group(3));
                }

                String str3="(\"deaths\")(\\:)(.*?)(\\,)";
                Pattern pattern3=Pattern.compile(str3);
                Matcher matcher3=pattern3.matcher(sb);

                while(matcher3.find()){
                    deathslist.add(matcher3.group(3));
                }

                String str4="(\"recovered\")(\\:)(.*?)(\\,)";
                Pattern pattern4=Pattern.compile(str4);
                Matcher matcher4=pattern4.matcher(sb);

                while (matcher4.find()){
                    recoveredlist.add(matcher4.group(3));
                }

                String str5="(\"active\")(\\:)(.*?)(\\,)";
                Pattern pattern5=Pattern.compile(str5);
                Matcher matcher5=pattern5.matcher(sb);

                while (matcher5.find()){
                    activelist.add(matcher5.group(3));
                }
            }

            for (int i=0;i<nameslist.size();i++){
                wholelist.add(new CountryInfo(nameslist.get(i),caseslist.get(i),deathslist.get(i),recoveredlist.get(i),activelist.get(i)));
            }
            for (int i=0;i<wholelist.size();i++){
                System.out.println(wholelist.get(i).getName()+" "+wholelist.get(i).getCases()+" "+wholelist.get(i).getActive());
            }
            time=LocalTime.now();
            input.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
