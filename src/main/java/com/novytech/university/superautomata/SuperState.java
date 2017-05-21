package com.novytech.university.superautomata;

import com.novytech.university.automata.State;
import com.novytech.university.automata.StateType;
import com.novytech.university.automata.Symbol;
import lombok.*;

import java.util.HashSet;
import java.util.LinkedHashMap;
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

    public static SuperState singularOf(State state) {
        Set<State> sources = new HashSet<>();
        sources.add(state);

        return new SuperState(sources, "{" + state.getName() + "}", state.getType());
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
