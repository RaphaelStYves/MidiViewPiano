package com;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Piece implements Serializable{

    //Property
    private int resolution;
    private float divisionType;
    private int cTranspose;
    private long pieceLenght;
    private float bpm = 0;
    List<Note> notes = new ArrayList<>();
    List<EChord> chords = new ArrayList<>();

    private static final int NOTE_ON = 0x90;
    private static final int NOTE_OFF = 0x80;
    private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    public Piece(File file) throws IOException, InvalidMidiDataException, MidiUnavailableException {


        Sequence sequence = MidiSystem.getSequence(file);
        Sequencer seqr = MidiSystem.getSequencer();
        seqr.open();
        seqr.setSequence(sequence);

        bpm = seqr.getTempoInBPM();
        resolution = sequence.getResolution();
        divisionType = sequence.getDivisionType();


        int trackNumber = 0;
        for (Track track : sequence.getTracks()) {
            trackNumber++;

            for (int i = 0; i < track.size(); i++) {
                Note note = new Note();

                note.setTracknumber(trackNumber);

                MidiEvent event = track.get(i);


                note.setPulse(event.getTick());

                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;

                    note.setChannel(sm.getChannel());

                    if (sm.getCommand() == NOTE_ON) {

                        note.setIndex(i);
                        note.setOn(true);
                        note.setNote(sm.getData1());
                        note.setVelocity(sm.getData2());
                        note.setOctave((sm.getData1() / 12) - 1);

                        note.setNotename(NOTE_NAMES[sm.getData1() % 12]);

                        notes.add(note);


                    } else if (sm.getCommand() == NOTE_OFF) {

                        note.setIndex(i);
                        note.setOn(false);
                        note.setNote(sm.getData1());
                        note.setVelocity(sm.getData2());

                        note.setNotename(NOTE_NAMES[sm.getData1() % 12]);

                        notes.add(note);

                    }
                }
            }
        }


        calculateTranspose();
        findPieceLenght();
        calculateLenght();
        ChangeToFalse();

    }

    private void ChangeToFalse() {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getVelocity() == 0) {
                notes.get(i).setOn(false);
            }
        }
    }

    private void calculateLenght() {

        int nnote;
        int nnote2;
        int cchan;
        int cchan2;
        long mtick;
        long mtick2;


        for (int i = 0; i < notes.size(); i++) {

            if (!notes.get(i).getOn() || notes.get(i).getVelocity() == 0) {
                nnote = notes.get(i).getNote();
                cchan = notes.get(i).getChannel();
                mtick = notes.get(i).getPulse();

                for (int j = i - 1; j >= 0; j--) {
                    nnote2 = notes.get(j).getNote();
                    cchan2 = notes.get(j).getChannel();

                    if (nnote == nnote2 && cchan == cchan2) {
                        mtick2 = notes.get(j).getPulse();

                        if (mtick != mtick2) {
                            notes.get(j).setLength(mtick - mtick2);
                        }
                        break;
                    }
                }
            }
        }


    }

    private void calculateTranspose() {

        int noteId;
        Map<Integer, Integer> mapTrans = new HashMap<>();
        //calcul chaque note pour chaque offset
        for (int offSetDT = 0; offSetDT < 11; offSetDT++) {
            int temp = 0;
            for (int i = 0; i < notes.size(); i++) {
                if (notes.get(i).getChannel() != 9) {
                    noteId = (notes.get(i).getNote() + offSetDT) % 12;
                    if (noteId != 1 || noteId != 3 || noteId != 6 || noteId != 8 || noteId != 10) {
                        temp = +1;
                    }
                }
            }
            mapTrans.put(offSetDT, temp);
        }
        //Finding Key associated with max Value in a Java Map
        cTranspose = Collections.max(mapTrans.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private void findPieceLenght() {

        long temp = 0;

        for (Note note : notes) {
            if (note.getPulse() + note.getLenght() > temp) {
                temp = note.getPulse() + note.getLenght();
            }
        }

        pieceLenght = temp;
    }

    public int getResolution() {
        return resolution;
    }

    public float getBpm() {
        return bpm;
    }

    public long getPieceLenght16() {
        return  pieceLenght / (resolution/4);
    }

    public float getDivisionType() {
        return divisionType;
    }

    class Chord implements Serializable{

        private EChord chord;
        private int index;

    }


    class Note implements Serializable{

        private int note;
        private int CNote;
        private boolean on;
        private int velocity;
        private long pulse;
        private int channel;
        private int tracknumber;
        private long lenght;
        private long lenght16;
        private int octave;
        private String notename;
        private int instrument;
        private int index;



        //Property
        public int getLenght16() {

            if (((int)((lenght / ((double)resolution/4))) < 1)){
                return 1;
            }else {
                return (int)((lenght/((double)resolution/4))+.5);
            }
         }

        public int getPulse16() {

            return (int)((pulse/((double)resolution/4))+.5);



        }

        public int getCNote() {
            return note +cTranspose;
        }


        public long getLenght() {
            return lenght;
        }

        int getNote() {
            return note;
        }

        void setNote(int note) {
            this.note = note;
        }

        int getVelocity() {
            return velocity;
        }

        void setVelocity(int velocity) {
            this.velocity = velocity;
        }

        int getChannel() {
            return channel;
        }

        void setChannel(int channel) {
            this.channel = channel;
        }


        void setTracknumber(int tracknumber) {
            this.tracknumber = tracknumber;
        }

        void setOctave(int octave) {
            this.octave = octave;
        }

        void setNotename(String notename) {
            this.notename = notename;
        }

        public long getPulse() {
            return pulse;
        }

        public void setPulse(long pulse) {
            this.pulse = pulse;
        }

        public long getLength() {
            return lenght;
        }

        public void setLength(long length) {
            this.lenght = length;
        }

        public boolean getOn() {
            return on;
        }

        public void setOn(boolean on) {
            this.on = on;
        }

        public int getInstrument() {
            return 0;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}