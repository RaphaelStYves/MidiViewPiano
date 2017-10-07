package com;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class  ViewNote extends Group{

    public static final int NOTEWIDTH = 30;
    public static final int NOTEHEIGHT = 20;
    public static final int SCOREWIDTH = 30;
    public static final int BEATHEIGHT = 30;

    ViewNote (Piece piece){

        for (int i = 0; i < piece.notes.size(); i++) {
            piece.notes.get(i).getNote();

            if (piece.notes.get(i).getOn()) {

                Rectangle rect1 = new Rectangle(piece.notes.get(i).getPulse16() * NOTEWIDTH, (77 - piece.notes.get(i).getNote()) * NOTEHEIGHT, NOTEWIDTH * (piece.notes.get(i).getLenght16()), NOTEHEIGHT);

                rect1.setFill(Color.GREEN);
                rect1.setStroke(Color.BLACK);
                rect1.setArcWidth(8);
                rect1.setArcHeight(8);

                Text text = new Text(piece.notes.get(i).getPulse16() * NOTEWIDTH,(77 - piece.notes.get(i).getNote()) * NOTEHEIGHT, Long.toString(piece.notes.get(i).getPulse16()));
                Text text2 = new Text(piece.notes.get(i).getPulse16() * NOTEWIDTH,20+(77 - piece.notes.get(i).getNote()) * NOTEHEIGHT, Long.toString(piece.notes.get(i).getPulse()));


                getChildren().addAll(rect1,text,text2);
            }
            
        }

    }


}
