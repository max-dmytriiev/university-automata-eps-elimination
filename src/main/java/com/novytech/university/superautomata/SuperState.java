package com.novytech.university.superautomata;

import com.novytech.university.automata.State;
import com.novytech.university.automata.StateType;
import com.novytech.university.automata.Symbol;
import com.novytech.university.automata.Transition;

import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = {"name", "type"})
public class SuperState {
    private final Set<State> sources;
    private final String name;
    private @Setter StateType type;

    private final Map<Symbol, SuperTransition> inbound = new LinkedHashMap<>();
    private final Map<Symbol, SuperTransition> outbound = new LinkedHashMap<>();

    @Override
    public String toString() {
        if (type == StateType.FINAL) {
            return "(" + name + ")";
        }
        return name;
    }

    public static SuperState of(Set<State> states) {
        HashSet<State> sources = new HashSet<>(states);
        String name = "{";
        StateType type = StateType.COMMON;
        for (State s :sources) {
            name = name + s.getName();
            if (type != StateType.FINAL) {
                type = s.getType();
            }
        }
        name += "}";
        return new SuperState(sources, name, type);
    }

    public void addOutboundTransition(SuperTransition t) {
        Symbol activator = t.getActivator();
        if (outbound.keySet().contains(activator)) {
            throw new IllegalStateException("Cannot add: transition: " + t + " violates determinism");
        }
        outbound.put(activator, t);
        t.getSource().addInboundTransition(t);

    }

    private void addInboundTransition(SuperTransition t) {
        Symbol activator = t.getActivator();
        inbound.put(activator, t);
    }
}
