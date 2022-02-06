package Tetris;


import java.io.*;
import java.util.*;
import java.util.List;

public class ScoreSaver {

    private String path = "src/main/resources/file.csv";

    public ScoreSaver() {
    }

    public void saveScore(String name, int score, int lvl, int lines, int x4lines, String time) throws IOException {
        List<List<String>> rows = getScores();
        rows.add(Arrays.asList(name, String.valueOf(score), String.valueOf(lvl), String.valueOf(lines),
                String.valueOf(x4lines), time));
        Collections.sort(rows, new Comparator<List<String>>() {
            @Override
            public int compare(List<String> l, List<String> r) {
                return Integer.parseInt(r.get(1)) - Integer.parseInt(l.get(1)) ;
            }
        });
        System.out.println(Integer.parseInt(String.valueOf(score)));
        FileWriter csvWriter = new FileWriter(path);
        for (List<String> rowData : rows) {
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
        getScores();
    }


    public List<List<String>> getScores() throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(path));
        List<List<String>> rows = new ArrayList<>();
        String row;
        while ((row = csvReader.readLine()) != null) {
            List<String> data = Arrays.asList(row.split(","));
            rows.add(data);
            System.out.println(data);
        }
        return rows;
    }


}
