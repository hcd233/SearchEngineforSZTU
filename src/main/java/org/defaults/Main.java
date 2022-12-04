package org.defaults;

import java.util.Scanner;

public class Main {
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