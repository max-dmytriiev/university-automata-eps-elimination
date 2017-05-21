package com.novytech.university;

import com.novytech.university.automata.Automaton;
import com.novytech.university.superautomata.SuperAutomaton;

public class Main {
    public static void main(String[] args) {
        Automaton automaton = Automaton.fromSource();
        automaton.show();
        automaton.eliminateEps();
        automaton.show();

        SuperAutomaton superAutomaton = SuperAutomaton.fromAutomaton(automaton);
        superAutomaton.show();
    }
}
