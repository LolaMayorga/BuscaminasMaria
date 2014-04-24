
package buscaminasmaria;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class BuscaMarco extends javax.swing.JFrame {
    
    Matriz matriz=new Matriz(16,30);
    JButton boton;
    int abiertas=0;
    boolean primerClick=false;
    int minas=0;
    boolean finPartida=false;
    boolean check=false;
    
    public BuscaMarco() {
        initComponents();
        matriz.rellenar();
        cargarBotones();
        numMinas.setText(String.valueOf(matriz.getMinas()));
        ImageIcon icono= new ImageIcon("1up_1.png");
        Icon icon = new ImageIcon(icono.getImage().getScaledInstance
        (start.getWidth(), start.getHeight(), Image.SCALE_DEFAULT));
        start.setIcon(icon);
    }
    
    void cargarBotones()
    {
        //Dimension panel 750,400 o 1050,560
        int cont=0;
        this.panel.setPreferredSize(new Dimension(1050,560)); //Dimensiona el panel
        this.panel.setLayout(new GridLayout(matriz.getFilas(),matriz.getColumnas()));  //setLayout establece la distribucion que tendran los botones en el panel. GridLayout es una plantilla que me permite tener una distribución en filas y columnas

        for(int i=0;i<matriz.getFilas();i++)  // Crea los botones en tiempo de ejecución
        {
            for(int j=0;j<matriz.getColumnas();j++)
            {
                JButton b=new JButton();
                b.setName(String.valueOf(cont));
 //               boton.setText(matriz.getMatriz(i, j));
                cont++;
                panel.add(b);
                
                b.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                    botonMouseClicked(evt);
                    }
                });
           }
        }
    }
    private void botonMouseClicked(java.awt.event.MouseEvent evt) {
        
        boton=(JButton)evt.getSource();
        int f=Integer.parseInt(boton.getName())/matriz.getColumnas();
        int c=Integer.parseInt(boton.getName())%matriz.getColumnas();
        
        //DOBLE CLICK IZQUIERDO PARA ABRIR SI YA TIENE LAS MINAS CORRESPONDIENTES UN NÚMERO
        if(evt.getButton()==MouseEvent.BUTTON1 && evt.getClickCount()==2 && matriz.abierta(f, c)==true && !matriz.getMatriz(f, c).equalsIgnoreCase("9.jpg") && finPartida==false)
        {
            checkMinas(f,c);
            if(check==true)
            {
                this.abrir2(f, c);
                check=false;
                if(abiertas==(matriz.getFilas()*matriz.getColumnas()-matriz.getMinas()))
                        {
                            mostrarWin();
                            JOptionPane.showMessageDialog(this, "ENHORABUENA ¡Eres un crack!");
                            finPartida=true;
                        }
            }
        }
        
        else
        {
            // CLICK IZQUIERDO PARA ABRIR
            if(evt.getButton()==MouseEvent.BUTTON1 && evt.getClickCount()==1)//boton izquierdo del raton 1 vez
            {
                if(matriz.getMatriz(f, c).equalsIgnoreCase("9.jpg") && matriz.abierta(f, c)==false)
                {
                        sonido("bomba.wav");
                        ImageIcon icono= new ImageIcon("11.jpg");
                        Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                        (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                        boton.setIcon(icon);
                        mostrarAll();
                        boton.setIcon(icon);
                        finPartida=true;

                }
                else
                {
                    if(matriz.abierta(f, c)==false)
                    {
                        abrir(f,c);
                        if(abiertas==(matriz.getFilas()*matriz.getColumnas()-matriz.getMinas()))
                        {
                            mostrarWin();
                            JOptionPane.showMessageDialog(this, "ENHORABUENA ¡Eres un crack!");
                            finPartida=true;
                        }
                    }
                }
            }
            
            else
            {
                // CLICK DERECHO PARA PONER O QUITAR UNA MINA
                if(evt.getButton()==MouseEvent.BUTTON3)
                {


                    if(boton.getIcon()==null)
                    {
                        ImageIcon icono= new ImageIcon("10.jpg");
                        Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                        (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                        boton.setIcon(icon); 
                        minas++;
                        numMinas.setText(String.valueOf(matriz.getMinas()-minas));
                    }
                    else
                    {
                        if(matriz.abierta(f, c)==false)
                        {
                            boton.setIcon(null);
                            minas--;
                            numMinas.setText(String.valueOf(matriz.getMinas()-minas));
                        }
                    }

                }
            }  
        }
    }
    
    private void abrir(int f, int c)
    {
        ImageIcon icono= new ImageIcon(matriz.getMatriz(f, c));
        Icon icon = new ImageIcon(icono.getImage().getScaledInstance
        (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
        JButton bt=(JButton)panel.getComponent((f)*matriz.getColumnas()+(c));
        bt.setIcon(icon);
        abiertas++;
        matriz.setOpen(f, c);
       
        if(matriz.getMatriz(f, c).equalsIgnoreCase("0.jpg")){       
                try
                {
                    if(matriz.abierta(f-1, c-1)==false)
                    {
                        abrir(f-1,c-1);
                    }
                }
                catch(Exception e){

                }
            
            
                try
                 {
                     if(matriz.abierta(f-1, c)==false)
                     {
                         abrir(f-1,c);
                     }
                 }
                 catch(Exception e){

                 }
                
                try
                {
                    if(matriz.abierta(f-1, c+1)==false)
                    {
                        abrir(f-1,c+1);
                    }
                }
                catch(Exception e){

                }
            
      
                try
                {
                    if(matriz.abierta(f, c-1)==false)
                    {
                        abrir(f,c-1);
                    }
                }
                catch(Exception e){

                }
            
                try
                {
                    if(matriz.abierta(f, c+1)==false)
                    {
                        abrir(f,c+1);
                    }
                }
                catch(Exception e){

                }
            
 
                try
                {
                    if(matriz.abierta(f+1, c-1)==false)
                    {
                        abrir(f+1, c-1);
                    }
                }
                catch(Exception e){

                }
            
 
                try
                {
                    if(matriz.abierta(f+1, c)==false)
                    {
                        abrir(f+1, c);
                    }
                }
                catch(Exception e){

                }

                try
                {
                    if(matriz.abierta(f+1, c+1)==false)
                    {
                        abrir(f+1, c+1);
                    }
                }
                catch(Exception e){

                }
            
        }
    }
    
    private void mostrarAll(){
        JButton  bot;
        
        int cont=0;
        
        matriz.abrirAll();
      for (int i=0; i<matriz.getFilas(); i++)  
      {
          for(int j=0;j<matriz.getColumnas();j++)
          {
              if(matriz.getMatriz(i, j).equalsIgnoreCase("9.jpg"))
              {
                bot= (JButton)this.panel.getComponent(cont);
                ImageIcon icono= new ImageIcon(matriz.getMatriz(i, j));
                Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                (bot.getWidth(), bot.getHeight(), Image.SCALE_DEFAULT));
                bot.setIcon(icon);
              }
              
              cont++;
          }
      }
    }
    
    private void mostrarWin(){
        
        JButton  bot;
        int cont=0;
        
        matriz.abrirAll();
        for (int i=0; i<matriz.getFilas(); i++)  
      {
          for(int j=0;j<matriz.getColumnas();j++)
          {
              if(matriz.getMatriz(i, j).equalsIgnoreCase("9.jpg"))
              {
                bot= (JButton)this.panel.getComponent(cont);
                ImageIcon icono= new ImageIcon("10.jpg");
                Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                (bot.getWidth(), bot.getHeight(), Image.SCALE_DEFAULT));
                bot.setIcon(icon);
              }
              
              cont++;
          }
      }
    }
    
    private void checkMinas(int f, int c)
    {
        int tocan=0;
        
        try
        {
            boton=(JButton)panel.getComponent((f-1)*matriz.getColumnas()+(c-1));                
            if(matriz.abierta(f-1, c-1)==false && boton.getIcon()!=null)
            {
                tocan++;
                if(!matriz.getMatriz(f-1, c-1).equalsIgnoreCase("9.jpg"))
                {
                    
                    ImageIcon icono= new ImageIcon("12.jpg");
                    Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                    (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                    boton.setIcon(icon);
                    sonido("bomba.wav");
                    mostrarAll();
                    boton.setIcon(icon);
                    finPartida=true;
                    return;
                }
            }
        }
        catch(Exception e){
            
        }
            
        try
        {
            boton=(JButton)panel.getComponent((f-1)*matriz.getColumnas()+(c));                
            if(matriz.abierta(f-1, c)==false && boton.getIcon()!=null)
            {
                tocan++;
                if(!matriz.getMatriz(f-1, c).equalsIgnoreCase("9.jpg"))
                {
                    
                    ImageIcon icono= new ImageIcon("12.jpg");
                    Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                    (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                    boton.setIcon(icon);
                    sonido("bomba.wav");
                    mostrarAll();
                    boton.setIcon(icon);
                    finPartida=true;
                    return;
                }
            }
        }
        catch(Exception e){
            
        }
            
        try
        {
            boton=(JButton)panel.getComponent((f-1)*matriz.getColumnas()+(c+1));                
            if(matriz.abierta(f-1, c+1)==false && boton.getIcon()!=null)
            {
                tocan++;
                if(!matriz.getMatriz(f-1, c+1).equalsIgnoreCase("9.jpg"))
                {
                    ImageIcon icono= new ImageIcon("12.jpg");
                    Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                    (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                    boton.setIcon(icon);
                    sonido("bomba.wav");
                    mostrarAll();
                    boton.setIcon(icon);
                    finPartida=true;
                    return;
                }
            }
        }
        catch(Exception e){
            
        }
            
      
        try
        {
            boton=(JButton)panel.getComponent((f)*matriz.getColumnas()+(c-1));                
            if(matriz.abierta(f, c-1)==false && boton.getIcon()!=null)
            {
                tocan++;
                if(!matriz.getMatriz(f, c-1).equalsIgnoreCase("9.jpg"))
                {
                    ImageIcon icono= new ImageIcon("12.jpg");
                    Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                    (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                    boton.setIcon(icon);
                    sonido("bomba.wav");
                    mostrarAll();
                    boton.setIcon(icon);
                    finPartida=true;
                    return;
                }
            }
        }
        catch(Exception e){
            
        }
            
        try
        {
            boton=(JButton)panel.getComponent((f)*matriz.getColumnas()+(c+1));                
            if(matriz.abierta(f, c+1)==false && boton.getIcon()!=null)
            {
                tocan++;
                if(!matriz.getMatriz(f, c+1).equalsIgnoreCase("9.jpg"))
                {
                    
                    ImageIcon icono= new ImageIcon("12.jpg");
                    Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                    (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                    boton.setIcon(icon);
                    sonido("bomba.wav");
                    mostrarAll();
                    boton.setIcon(icon);
                    finPartida=true;
                    return;
                }
            }
        }
        catch(Exception e){
            
        }
        
        try
        {
            boton=(JButton)panel.getComponent((f+1)*matriz.getColumnas()+(c-1));                
            if(matriz.abierta(f+1, c-1)==false && boton.getIcon()!=null)
            {
                tocan++;
                if(!matriz.getMatriz(f+1, c-1).equalsIgnoreCase("9.jpg"))
                {
                    ImageIcon icono= new ImageIcon("12.jpg");
                    Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                    (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                    boton.setIcon(icon);
                    sonido("bomba.wav");
                    mostrarAll();
                    boton.setIcon(icon);
                    finPartida=true;
                    return;
                }
            }
        }
        catch(Exception e){
        }
            
        try
        {
            boton=(JButton)panel.getComponent((f+1)*matriz.getColumnas()+(c));                
            if(matriz.abierta(f+1, c)==false && boton.getIcon()!=null)
            {
                tocan++;
                if(!matriz.getMatriz(f+1, c).equalsIgnoreCase("9.jpg"))
                {
                    ImageIcon icono= new ImageIcon("12.jpg");
                    Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                    (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                    boton.setIcon(icon);
                    sonido("bomba.wav");
                    mostrarAll();
                    boton.setIcon(icon);
                    finPartida=true;
                    return;
                }
            }
        }
        catch(Exception e){
        }

        try
        {
            boton=(JButton)panel.getComponent((f+1)*matriz.getColumnas()+(c+1));                
            if(matriz.abierta(f+1, c+1)==false && boton.getIcon()!=null)
            {
                tocan++;
                if(!matriz.getMatriz(f+1, c+1).equalsIgnoreCase("9.jpg"))
                {
                    ImageIcon icono= new ImageIcon("12.jpg");
                    Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                    (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                    boton.setIcon(icon);
                    sonido("bomba.wav");
                    mostrarAll();
                    boton.setIcon(icon);
                    finPartida=true;
                    return;
                }
            }
        }
        catch(Exception e){
        }
        
        int prueba=(matriz.getMatriz(f, c).charAt(0)-48); //Pasa el char a int
        
        if(tocan==prueba)
        {
            check=true;
        }
    }
    
    private void abrir2(int f, int c)
    {
        try
                {
                    if(matriz.abierta(f-1, c-1)==false && !matriz.getMatriz(f-1, c-1).equalsIgnoreCase("9.jpg"))
                    {
                        if(matriz.getMatriz(f-1, c-1).equalsIgnoreCase("0.jpg"))
                        {
                            abrir(f-1,c-1);
                        }
                        else
                        {
                            boton=(JButton)panel.getComponent((f-1)*matriz.getColumnas()+(c-1));
                            ImageIcon icono= new ImageIcon(matriz.getMatriz(f-1, c-1));
                            Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                            (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                            
                            boton.setIcon(icon);
                            abiertas++;
                            matriz.setOpen(f-1, c-1);
                        }
                    }
                }
                catch(Exception e){

                }
            
            
                try
                 {
                     if(matriz.abierta(f-1, c)==false && !matriz.getMatriz(f-1, c).equalsIgnoreCase("9.jpg"))
                    {
                        if(matriz.getMatriz(f-1, c).equalsIgnoreCase("0.jpg"))
                        {
                            abrir(f-1,c);
                        }
                        else
                        {
                            boton=(JButton)panel.getComponent((f-1)*matriz.getColumnas()+(c));
                            ImageIcon icono= new ImageIcon(matriz.getMatriz(f-1, c));
                            Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                            (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                            
                            boton.setIcon(icon);
                            abiertas++;
                            matriz.setOpen(f-1, c);
                        }
                    }
                 }
                 catch(Exception e){

                 }
                
                try
                {
                    if(matriz.abierta(f-1, c+1)==false && !matriz.getMatriz(f-1, c+1).equalsIgnoreCase("9.jpg"))
                    {
                        if(matriz.getMatriz(f-1, c+1).equalsIgnoreCase("0.jpg"))
                        {
                            abrir(f-1,c+1);
                        }
                        else
                        {
                            boton=(JButton)panel.getComponent((f-1)*matriz.getColumnas()+(c+1));
                            ImageIcon icono= new ImageIcon(matriz.getMatriz(f-1, c+1));
                            Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                            (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                            
                            boton.setIcon(icon);
                            abiertas++;
                            matriz.setOpen(f-1, c+1);
                        }
                    }
                }
                catch(Exception e){

                }
            
      
                try
                {
                    if(matriz.abierta(f, c-1)==false && !matriz.getMatriz(f, c-1).equalsIgnoreCase("9.jpg"))
                    {
                        if(matriz.getMatriz(f, c-1).equalsIgnoreCase("0.jpg"))
                        {
                            abrir(f,c-1);
                        }
                        else
                        {
                            boton=(JButton)panel.getComponent((f)*matriz.getColumnas()+(c-1));
                            ImageIcon icono= new ImageIcon(matriz.getMatriz(f, c-1));
                            Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                            (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                            
                            boton.setIcon(icon);
                            abiertas++;
                            matriz.setOpen(f, c-1);
                        }
                    }
                }
                catch(Exception e){

                }
            
                try
                {
                    if(matriz.abierta(f, c+1)==false && !matriz.getMatriz(f, c+1).equalsIgnoreCase("9.jpg"))
                    {
                        if(matriz.getMatriz(f, c+1).equalsIgnoreCase("0.jpg"))
                        {
                            abrir(f,c+1);
                        }
                        else
                        {
                            boton=(JButton)panel.getComponent((f)*matriz.getColumnas()+(c+1));
                            ImageIcon icono= new ImageIcon(matriz.getMatriz(f, c+1));
                            Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                            (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                            
                            boton.setIcon(icon);
                            abiertas++;
                            matriz.setOpen(f, c+1);
                        }
                    }
                }
                catch(Exception e){

                }
                try
                {
                    if(matriz.abierta(f+1, c-1)==false && !matriz.getMatriz(f+1, c-1).equalsIgnoreCase("9.jpg"))
                    {
                        if(matriz.getMatriz(f+1, c-1).equalsIgnoreCase("0.jpg"))
                        {
                            abrir(f+1,c-1);
                        }
                        else
                        {
                            boton=(JButton)panel.getComponent((f+1)*matriz.getColumnas()+(c-1));
                            ImageIcon icono= new ImageIcon(matriz.getMatriz(f+1, c-1));
                            Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                            (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                            
                            boton.setIcon(icon);
                            abiertas++;
                            matriz.setOpen(f+1, c-1);
                        }
                    }
                }
                catch(Exception e){

                }
            
 
                try
                {
                    if(matriz.abierta(f+1, c)==false && !matriz.getMatriz(f+1, c).equalsIgnoreCase("9.jpg"))
                    {
                        if(matriz.getMatriz(f+1, c).equalsIgnoreCase("0.jpg"))
                        {
                            abrir(f+1,c);
                        }
                        else
                        {
                            boton=(JButton)panel.getComponent((f+1)*matriz.getColumnas()+(c));
                            ImageIcon icono= new ImageIcon(matriz.getMatriz(f+1, c));
                            Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                            (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                            
                            boton.setIcon(icon);
                            abiertas++;
                            matriz.setOpen(f+1, c);
                        }
                    }
                }
                catch(Exception e){

                }

                try
                {
                    if(matriz.abierta(f+1, c+1)==false && !matriz.getMatriz(f+1, c+1).equalsIgnoreCase("9.jpg"))
                    {
                        if(matriz.getMatriz(f+1, c+1).equalsIgnoreCase("0.jpg"))
                        {
                            abrir(f+1,c+1);
                        }
                        else
                        {
                            boton=(JButton)panel.getComponent((f+1)*matriz.getColumnas()+(c+1));
                            ImageIcon icono= new ImageIcon(matriz.getMatriz(f+1, c+1));
                            Icon icon = new ImageIcon(icono.getImage().getScaledInstance
                            (boton.getWidth(), boton.getHeight(), Image.SCALE_DEFAULT));
                            
                            boton.setIcon(icon);
                            abiertas++;
                            matriz.setOpen(f+1, c+1);
                        }
                    }
                }
                catch(Exception e){

                }
    }
    
    public void sonido(String a)
    {
        
        Clip sonidoActual = null;
        try {
            sonidoActual = AudioSystem.getClip();
            System.out.println("");
        } catch (LineUnavailableException ex) {
            Logger.getLogger(BuscaMarco.class.getName()).log(Level.SEVERE, null, ex);
        }
        File actual = new File(a);

        try {
            AudioInputStream asi = null;
            try {
                    asi = AudioSystem.getAudioInputStream(actual);
                } 
            catch (UnsupportedAudioFileException ex)
            {
                Logger.getLogger(BuscaMarco.class.getName()).log(Level.SEVERE, null, ex);
            }
            sonidoActual.open(asi);
            } 
        catch (LineUnavailableException ex) 
        {
            throw new RuntimeException("ERROR: " + ex);
        }
        catch (IOException ex) 
        {
            throw new RuntimeException("ERROR: " + ex);
        }
        sonidoActual.start();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        numMinas = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        start = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Buscaminas");
        setResizable(false);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 805, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 204, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Te quedan  ");

        numMinas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("minas");

        start.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                startMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                startMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numMinas, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(315, 315, 315)
                        .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(272, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(numMinas, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(362, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void startMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startMouseClicked
       sonido("1up.wav");
       matriz.resetAll();
       matriz.rellenar();
       this.abiertas=0;
        JButton  bot;    
      for (int i=0; i<30*16; i++)  
      {
       bot= (JButton)this.panel.getComponent(i);
       bot.setIcon(null);
      }
       numMinas.setText(String.valueOf(matriz.getMinas()));
       minas=0;
       finPartida=false;
    }//GEN-LAST:event_startMouseClicked

    private void startMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startMousePressed
        ImageIcon icono= new ImageIcon("prueba.png");
        Icon icon = new ImageIcon(icono.getImage().getScaledInstance
        (start.getWidth(), start.getHeight(), Image.SCALE_DEFAULT));
        start.setIcon(icon);
    }//GEN-LAST:event_startMousePressed

    private void startMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startMouseReleased
        ImageIcon icono= new ImageIcon("1up_1.png");
        Icon icon = new ImageIcon(icono.getImage().getScaledInstance
        (start.getWidth(), start.getHeight(), Image.SCALE_DEFAULT));
        start.setIcon(icon);
    }//GEN-LAST:event_startMouseReleased

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuscaMarco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuscaMarco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuscaMarco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuscaMarco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BuscaMarco().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel numMinas;
    private javax.swing.JPanel panel;
    private javax.swing.JButton start;
    // End of variables declaration//GEN-END:variables
}