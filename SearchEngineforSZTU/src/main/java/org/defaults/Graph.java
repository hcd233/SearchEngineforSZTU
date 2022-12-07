package org.defaults;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Graph {
    private final double[][]  Matrix;
    private final int Vertex_Num;
    private final boolean[] isVisit;
    public String Start_Vertex;
    private final String[] Vertex_Name;
    private final MinDistPath[][] Paths;
    private static final int MAX_VERTEX_NUM = 100;
    private static final double MAX_DISTANCE = 1e4;
    public Graph() {
        this.Vertex_Num = MAX_VERTEX_NUM;
        this.isVisit = new boolean[this.Vertex_Num];
        this.Vertex_Name = new String[this.Vertex_Num];
        this.Matrix = new double[this.Vertex_Num][this.Vertex_Num];
        this.Paths = new MinDistPath[this.Vertex_Num][this.Vertex_Num];
        for(int i=0;i<Vertex_Num;i++){
            isVisit[i] = false;
            Vertex_Name[i] = "Vertex_" + i;
        }
    }
    public Graph(int vertex_num) {
        this.Vertex_Num = vertex_num;
        this.isVisit = new boolean[this.Vertex_Num];
        this.Vertex_Name = new String[this.Vertex_Num];
        this.Matrix = new double[this.Vertex_Num][this.Vertex_Num];
        this.Paths = new MinDistPath[this.Vertex_Num][this.Vertex_Num];
        for(int i=0;i<Vertex_Num;i++){
            isVisit[i] = false;
            Vertex_Name[i] = "Vertex_" + i;
        }
    }
    public void Set_Matrix(){
        Scanner inp = new Scanner(System.in);
        for(int i=0;i<Vertex_Num;i++){
            for(int j=0;j<Vertex_Num;j++){
                Matrix[i][j] = inp.nextDouble();
                if (Matrix[i][j] == 0) {
                    Matrix[i][j] = MAX_DISTANCE;
                }
            }
            //System.out.println(Arrays.toString(Matrix[i]));
        }
        for(int i=0;i<Vertex_Num;i++){
            for(int j=0;j<Vertex_Num;j++){
                Paths[i][j] = new MinDistPath(Vertex_Name[i],Vertex_Name[j]);
            }
        }
    }

    public void Set_Vertex_Name(){
        Scanner inp = new Scanner(System.in);
        for(int i=0;i<Vertex_Num;i++){
            System.out.println("Set the Vertex " + i + " Name:");
            Vertex_Name[i] = inp.next();
            //System.out.println(Vertex_Name[i]);
        }
    }
    private void Dijkstra(int index){
        int p = -1;
        double MIN;
        for (int i = 0; i < Vertex_Num; i++) {
            isVisit[i] = false;
            Paths[index][i].Path = Vertex_Name[index];
            Paths[index][i].Distance = MAX_DISTANCE;
            if (Matrix[index][i] < MAX_DISTANCE) {
                Paths[index][i].Distance = Matrix[index][i];
                Paths[index][i].Path += " -> " +Vertex_Name[i];
            }
        }
        isVisit[index] = true;
        for (int i = 0; i < Vertex_Num - 1; i++) {
            MIN = MAX_DISTANCE;
            for (int j = 0; j < Vertex_Num; j++) {
                if (!isVisit[j] && Paths[index][j].Distance  < MIN) {
                    p = j;
                    MIN = Paths[index][j].Distance;
                }
            }
            isVisit[p] = true;
            for (int j = 0; j < Vertex_Num; j++) {
                if (!isVisit[j] && Matrix[p][j] + Paths[index][p].Distance < Paths[index][j].Distance) {
                    Paths[index][j].Distance = Matrix[p][j] + Paths[index][p].Distance;
                    Paths[index][j].Path = Paths[index][p].Path + " -> " + Vertex_Name[j];
                }
            }
        }
    }
    public void Run(){
        for(int g=0;g<Vertex_Num;g++){
            Start_Vertex = Vertex_Name[g];
            Dijkstra();
        }
    }
    public void Dijkstra(){
        int target_index = -1;
        for(int i=0;i<Vertex_Num;i++){
            if(Objects.equals(Vertex_Name[i], Start_Vertex)){
                target_index = i;
            }
        }
        if(target_index == -1){
            System.out.println("ERROR INPUT OF START VERTEX.PLEASE SELECT IN :"+ Arrays.toString(Vertex_Name));
            return ;
        }
        Dijkstra(target_index);
    }
    public void DisplayPaths(boolean isDetails){
        if(isDetails){
            for(int i=0;i<Vertex_Num;i++){
                for(int j=0;j<Vertex_Num;j++){
                    if(i!=j){
                        Paths[i][j].Display();
                    }
                }
            }
        }
        else{
            for(MinDistPath[] i:Paths){
                for(MinDistPath j:i){
                    System.out.print(j + " ");
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        int n;
        Scanner inp = new Scanner(System.in);
        n = Integer.parseInt(inp.next());
        Graph graph = new Graph(n);
        //graph.Set_Vertex_Name();
        graph.Set_Matrix();
        graph.Run();
        graph.DisplayPaths(false);
    }
}
class MinDistPath {
    private final double MAX_DISTANCE = 114514;
    /* 储存最短路径的类 */
    private String Begin;
    private String End;
    public String Path;
    public double Distance;
    public MinDistPath(String begin,String end){
        this.Begin = begin;
        this.End = end;
        this.Distance = MAX_DISTANCE;
    }
    public MinDistPath(String begin,String end,double distance,String path){
        this.Begin = begin;
        this.End = end;
        this.Distance = distance;
        this.Path = path;
    }
    public String toString(){
        return String.format("%.4f",this.Distance);
    }
    public void Display(){
        System.out.println(this.Begin+" -> "+this.End+" MinDist: "+this.Distance+" Path: "+this.Path);
    }
}
