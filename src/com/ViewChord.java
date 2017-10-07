package com;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.ViewBackGround.CHORDSPLACE;
import static com.ViewNote.NOTEHEIGHT;
import static com.ViewNote.NOTEWIDTH;

public class ViewChord extends Group{


    ViewChord(Piece piece){


            for (int i = 0; i < piece.getPieceLenght16(); i++) {

                RectChord rectChord = new RectChord(i*NOTEWIDTH, 0, NOTEWIDTH , NOTEHEIGHT );

                rectChord.setEchord(EChord.I);
                rectChord.setStroke(Color.GRAY);



                getChildren().add(rectChord);


        }
    }

    class RectChord extends Rectangle{

        EChord echord;
        Color color;

        RectChord(double x, double y, double width, double height){
            super( x,  y,  width,  height);

        }

        public void setEchord(EChord echord) {

            switch (echord) {
                case I:
                    this.color = Color.DODGERBLUE;
                    this.echord = echord;
                    break;
                case i:
                    this.color = Color.CORNFLOWERBLUE;
                    this.echord = echord;
                    break;
                case II:
                    this.color = Color.CORAL;
                    this.echord = echord;
                    break;
                case ii:
                    this.color = Color.DARKSALMON;
                    this.echord = echord;
                    break;
                case III:
                    this.color = Color.HOTPINK;
                    this.echord = echord;
                    break;
                case iii:
                    this.color = Color.VIOLET;
                    this.echord = echord;
                    break;
                case IV:
                    this.color = Color.YELLOWGREEN;
                    this.echord = echord;
                    break;
                case iv:
                    this.color = Color.PALEGREEN;
                    this.echord = echord;
                    break;
                case V:
                    this.color = Color.FIREBRICK;
                    this.echord = echord;
                    break;
                case v:
                    this.color = Color.PALEVIOLETRED;
                    this.echord = echord;
                    break;
                case VI:
                    this.color = Color.AQUA;
                    this.echord = echord;
                    break;
                case vi:
                    this.color = Color.AQUAMARINE;
                    this.echord = echord;
                    break;
                case VII:
                    this.color = Color.TAN;
                    this.echord = echord;
                    break;
                case vii:
                    this.color = Color.BEIGE;
                    this.echord = echord;
                    break;
            }

        }

        public EChord getEchord() {
            return echord;
        }
    }
}
