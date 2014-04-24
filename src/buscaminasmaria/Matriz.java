
package buscaminasmaria;

public class Matriz {
    int filas;
    int columnas;
    String matriz [][];
    boolean open[][];
    int minas=99;
   
    Matriz(int filas, int columnas)
    {
        this.filas=filas;
        this.columnas=columnas;
        matriz=new String [filas][columnas];
        for(int i=0;i<filas;i++)
        {
            for(int j=0;j<columnas;j++)
            {
                matriz[i][j]="0.jpg";
            }
        }
        
        open=new boolean [filas][columnas];
        for(int i=0;i<filas;i++)
        {
            for(int j=0;j<columnas;j++)
            {
                open[i][j]=false;
            }
        }
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    
    public void setMatriz(String[][] matriz) {
        this.matriz = matriz;
    }
    
    // Creo una matriz exactamente igual con booleans, para comprobar si cada casilla está destapada
    public String getMatriz(int f,int c) {
        return matriz[f][c];
    }
    
    public int getMinas(){
        return this.minas;
    }
    
    public void setOpen(int f,int c){
        this.open[f][c]=true;
    }
    
    public void setClose(int f,int c){
        this.open[f][c]=false;
    }
    
    public boolean abierta(int f, int c){
        return open[f][c];
    }
    
    public void resetAll(){
        for(int i=0;i<filas;i++)
        {
            for(int j=0;j<columnas;j++)
            {
                matriz[i][j]="0.jpg";
            }
        }
        
        open=new boolean [filas][columnas];
        for(int i=0;i<filas;i++)
        {
            for(int j=0;j<columnas;j++)
            {
                open[i][j]=false;
            }
        }
    }
    public void abrirAll(){
        for(int i=0;i<filas;i++)
        {
            for(int j=0;j<columnas;j++)
            {
                open[i][j]=true;
            }
        }
    }
    
    public void rellenar(){
        int cont=0;
        // Pone aleatoriamente 99 bombas
        do{
            int i=(int)(Math.random()*15);
            int j=(int)(Math.random()*30);
            if(matriz[i][j].equalsIgnoreCase("0.jpg"))
            {
                matriz[i][j]="9.jpg";
                cont++;
            }
        }
        while(cont<minas);
        
        // rellena el resto de huecos con números
        
        for(int i=0;i<filas;i++)
        {
            for(int j=0;j<columnas;j++)
            {
                cont=0;
                if(!(matriz[i][j].equalsIgnoreCase("9.jpg")))
                {
                    try
                    {
                        if(matriz[i-1][j-1].equalsIgnoreCase("9.jpg"))
                        {
                            cont++;
                        }
                    }
                    catch(Exception e){

                    }
                    try
                    {
                        if(matriz[i-1][j].equalsIgnoreCase("9.jpg"))
                        {
                            cont++;
                        }
                    }
                    catch(Exception e){

                    }
                    try
                    {
                        if(matriz[i-1][j+1].equalsIgnoreCase("9.jpg"))
                        {
                            cont++;
                        }
                    }
                    catch(Exception e){

                    }
                    try
                    {
                        if(matriz[i][j-1].equalsIgnoreCase("9.jpg"))
                        {
                            cont++;
                        }
                    }
                    catch(Exception e){

                    }
                    try
                    {
                        if(matriz[i][j+1].equalsIgnoreCase("9.jpg"))
                        {
                            cont++;
                        }
                    }
                    catch(Exception e){

                    }
                    try
                    {
                        if(matriz[i+1][j-1].equalsIgnoreCase("9.jpg"))
                        {
                            cont++;
                        }
                    }
                    catch(Exception e){

                    }
                    try
                    {
                        if(matriz[i+1][j].equalsIgnoreCase("9.jpg"))
                        {
                            cont++;
                        }
                    }
                    catch(Exception e){

                    }
                    try
                    {
                        if(matriz[i+1][j+1].equalsIgnoreCase("9.jpg"))
                        {
                            cont++;
                        }
                    }
                    catch(Exception e){

                    }

                    matriz[i][j]=String.valueOf(cont)+".jpg";
                }
            }
        }
 
    }
}
