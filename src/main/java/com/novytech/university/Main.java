package com.novytech.university;

import com.novytech.university.automata.Automaton;

public class Main {
    public static void main(String[] args) {
        Automaton automaton = Automaton.fromSource();
        automaton.show();
        automaton.eliminateEps();
        automaton.show();
    }
}
