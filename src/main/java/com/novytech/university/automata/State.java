package com.novytech.university.automata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@EqualsAndHashCode(of = {"name", "type"})
public class State implements Comparable<State>{
    private final String name;
    private @Setter StateType type;

    private Map<Symbol, List<Transition>> inbound = new HashMap<>();
    private Map<Symbol, List<Transition>> outbound = new HashMap<>();

    public State(String name, StateType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public int compareTo(State o) {
        return name.compareTo(o.name);
    }

    @Override
    public String toString() {
        if (type == StateType.FINAL) {
            return "(" + name + ")";
        }
        return name;
    }

    public void addOutboundTransition(Transition toAdd) {
        if (!this.equals(toAdd.getSource())) {
            throw new IllegalArgumentException("Cannot add OUTBOUND transition to state {" + name + " ; " + type + "} : SOURCE not match");
        }

        Symbol activator = toAdd.getActivator();
        List<Transition> existingTransitions = outbound.get(activator);

        if (existingTransitions == null) {
            existingTransitions = new ArrayList<>();
        }

        existingTransitions.add(toAdd);
        outbound.put(activator, existingTransitions);

        toAdd.getTarget().addInboundTransition(toAdd);
    }

    private void addInboundTransition(Transition toAdd) {
        if (!this.equals(toAdd.getTarget())) {
            throw new IllegalArgumentException("Cannot add INBOUND transition to state {" + name + " ; " + type + "} : TARGET not match");
        }

        Symbol activator = toAdd.getActivator();
        List<Transition> existingTransitions = inbound.get(activator);

        if (existingTransitions == null) {
            existingTransitions = new ArrayList<>();
        }

        existingTransitions.add(toAdd);
        inbound.put(activator, existingTransitions);
    }

    public void removeOutboundTransition(Transition toRemove) {
        if (!this.equals(toRemove.getSource())) {
            throw new IllegalArgumentException("Cannot remove OUTBOUND transition from state {" + name + " ; " + type + "} : SOURCE not match");
        }

        Symbol activator = toRemove.getActivator();
        List<Transition> existingTransitions = outbound.get(activator);

        if (existingTransitions == null) {
            throw new IllegalArgumentException("Cannot remove OUTBOUND transition from state {" + name + " ; " + type + "} : NOT EXIST");
        }

        int previousSize = existingTransitions.size();
        existingTransitions.remove(toRemove);
        if (existingTransitions.size() == previousSize) {
            throw new IllegalArgumentException("Could not remove OUTBOUND transition from state {" + name + " ; " + type + "} : NOT EXIST");
        }

        if (existingTransitions.size() == 0) {
            outbound.remove(activator);
        }
        else {
            outbound.put(activator, existingTransitions);
        }
        toRemove.getTarget().removeInboundTransition(toRemove);
    }

    private void removeInboundTransition(Transition toRemove) {
        if (!this.equals(toRemove.getTarget())) {
            throw new IllegalArgumentException("Cannot remove INBOUND transition from state {" + name + " ; " + type + "} : TARGET not match");
        }

        Symbol activator = toRemove.getActivator();
        List<Transition> existingTransitions = inbound.get(activator);

        if (existingTransitions == null) {
            throw new IllegalArgumentException("Cannot remove INBOUND transition from state {" + name + " ; " + type + "} : NOT EXIST");
        }

        int previousSize = existingTransitions.size();
        existingTransitions.remove(toRemove);
        if (existingTransitions.size() == previousSize) {
            throw new IllegalArgumentException("Could not remove INBOUND transition from state {" + name + " ; " + type + "} : NOT EXIST");
        }

        if (existingTransitions.size() == 0) {
            inbound.remove(activator);
        } else {
            inbound.put(activator, existingTransitions);
        }

    }
}
