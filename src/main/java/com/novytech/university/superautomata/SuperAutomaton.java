package com.novytech.university.superautomata;

import com.novytech.university.automata.*;

import java.util.*;
import java.util.stream.Collectors;

public class SuperAutomaton {
    private final List<SuperState> states = new ArrayList<>();
    private final List<SuperTransition> transitions = new ArrayList<>();

    private SuperAutomaton(){}

    public void show() {
        System.out.println("---------- STATES ----------");
        states.forEach(superState -> System.out.print(superState + " "));
        System.out.println();
        System.out.println("---------TRANSITION---------");
        transitions.forEach(System.out::println);
        System.out.println("----------------------------");
        System.out.println();
    }

    public static SuperAutomaton fromAutomaton(Automaton automaton) {
        SuperAutomaton superAutomaton = new SuperAutomaton();

        State initialState = automaton.getInitialState();
        SuperState initialSuperState = SuperState.singularOf(initialState);

        Queue<SuperState> superStates = new LinkedList<>();
        superStates.add(initialSuperState);

        while (!superStates.isEmpty()) {
            SuperState source = superStates.remove();
            boolean shouldAdd = true;
            for (SuperState s: superAutomaton.states) {
                if (s.equals(source)) {
                    shouldAdd = false;
                    break;
                }
            }
            if (shouldAdd) {
                superAutomaton.states.add(source);
            }
            Set<State> sourceStates = source.getSources();
            Map<Symbol, Set<State>> transitionMap = new HashMap<>();

            sourceStates.stream().map(state -> state.getOutbound().values())
                    .flatMap(Collection::stream)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList())
                    .forEach(
                            transition -> {
                                Symbol activator = transition.getActivator();
                                State target = transition.getTarget();
                                Set<State> targets = transitionMap.get(activator);
                                if (targets == null) {
                                    targets = new TreeSet<>();
                                }
                                targets.add(target);

                                transitionMap.put(activator, targets);
                            }
                    );
            transitionMap.forEach(
                    (activator, states) -> {
                            boolean mustBeFinal = false;

                            StringBuilder nameBuilder = new StringBuilder("{");
                            for (State state : states) {
                                nameBuilder.append(state.getName());
                                mustBeFinal = mustBeFinal || (state.getType().equals(StateType.FINAL));
                            }
                            nameBuilder.append("}");

                            String newName = nameBuilder.toString();

                            SuperState superState = new SuperState(states, newName, mustBeFinal ? StateType.FINAL : StateType.COMMON);
                            SuperTransition newSuperTransition = new SuperTransition(source, activator, superState);

                            boolean willAdd = true;
                            for (SuperTransition superTransition : superAutomaton.transitions) {
                                if (superTransition.equals(newSuperTransition)) {
                                    willAdd = false;
                                    break;
                                }
                            }
                            if (willAdd) {
                                source.addOutboundTransition(newSuperTransition);
                                superAutomaton.transitions.add(newSuperTransition);
                                superStates.add(superState);
                            }
                    }
            );
        }

        return superAutomaton;
    }
}
